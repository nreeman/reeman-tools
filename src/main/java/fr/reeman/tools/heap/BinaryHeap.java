package fr.reeman.tools.heap;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import lombok.NonNull;

public class BinaryHeap<T> {

	public static final int CAPACITY_DEFAULT = computeCapacity(6);

	private T[] elements;
	private int offset;
	private final Comparator<T> comparator;

	public static class BinaryHeapBuilder<T> {
		private Class<T> clazz;
		private Comparator<T> comparator;
		private int capacity = CAPACITY_DEFAULT;
		private Collection<T> init = null;
		
		private BinaryHeapBuilder(@NonNull Class<T> clazz, @NonNull Comparator<T> comparator) {
			this.clazz = clazz;
			this.comparator = comparator;
		}

		public BinaryHeap<T> build() {
			return new BinaryHeap<T>(clazz, comparator, capacity, init);
		}
		
		public BinaryHeapBuilder<T> capacity(int capacity) {
			this.capacity = capacity;
			return this;
		}
		
		public BinaryHeapBuilder<T> init(Collection<T> init) {
			this.init = init;
			return this;
		}
	}
	
	public static <T> BinaryHeapBuilder<T> builder(Class<T> clazz, Comparator<T> comparator) {
		return new BinaryHeapBuilder<T>(clazz, comparator);
	}
	
	@SuppressWarnings("unchecked")
	private BinaryHeap(@NonNull Class<T> clazz, @NonNull Comparator<T> comparator, int capacity, Collection<T> init) {
		this.comparator = comparator;
		capacity = (init != null && init.size() > capacity) ? init.size() : capacity;
		this.elements = (T[]) Array.newInstance(clazz, computeCapacity(elementsToLevel(capacity)));
		this.offset = 0;
		
		if (init != null && init.size() > 0) {
			for (T t : init) {
				elements[this.offset] = t;
				this.offset++;
			}
			for (int i = elements.length - 2; i >= 0; i--) {
				percolateDown(i);
			}
		}
	}
	
	/**
	 * 
	 * @return La capacité du tas. C'est à dire le nombre d'éléments qu'il peut contenir avant de nécessiter un accroissement : <code>capacity() >= size()</code>
	 */
	public int capacity() {
		return elements.length;
	}
	
	/**
	 * 
	 * @return Le nombre d'éléments dans le tas
	 */
	public int size() {
		return offset;
	}
	
	/**
	 * Ajoute un élément dans le tas
	 * 
	 * @param element
	 * @return Le tas
	 */
	public BinaryHeap<T> insert(T element) {
		if (offset == elements.length) {
			elements = Arrays.copyOf(elements, computeCapacity(elementsToLevel(elements.length) + 1));
		}
		
		elements[offset] = element;
		percolateUp(offset);
		offset++;

		return this;
	}
	
	/**
	 * 
	 * @return L'Objet de plus haute priorité, l'enlève du tas et s'assure que son intégrité est conservée suite à la suppression. <null> si le tas est vide
	 */
	public T extract() {
		if (offset == 0) {
			return null;
		}

		T extracted = elements[0];
		elements[0] = elements[offset - 1];
		elements[offset - 1] = null;
		percolateDown(0);
		offset--;

		return extracted;
	}

	/**
	 * 
	 * @param elements Le nombre d'éléments
	 * @return Le nombre de niveaux de l'arbre binaire nécessaire pour stocker le nombre d'éléments passé en paramètre.
	 */
	private static int elementsToLevel(int elements) {
		if (elements == 0) {
			return 0;
		}
		
		double log2 = (StrictMath.log(elements) / StrictMath.log(2));
		double ceil = StrictMath.ceil(log2);
		
		return log2 == ceil ? (int)ceil + 1 : (int)ceil;
	}

	/**
	 * 
	 * @param levels
	 * @return Calcul le nombre d'éléments que peut contenir un arbre binaire ayant le nombre de niveaux passé en paramètre 
	 */
	private static int computeCapacity(int levels) {
		return (int)Math.pow(2, levels) - 1;
	}

	/**
	 * Echange deux éléments dans le tableau
	 * 
	 * @param offset1
	 * @param offset2
	 */
	private void swap(int offset1, int offset2) {
		T t = elements[offset1];
		elements[offset1] = elements[offset2];
		elements[offset2] = t;
	}
	
	/**
	 * Fonction récursive qui échange la place d'un élément avec son père si sa priorisation est supérieure.
	 * Puis recommence jusqu'à ce que l'élément ait un père plus prioritaire ou est au sommet du tas
	 * 
	 * @param currentOffset offset de l'élément à potentiellement déplacer
	 * 
	 */
	private void percolateUp(int currentOffset) {
		if (currentOffset == 0) {
			return;
		}
		
		int fatherOffset = (currentOffset - 1) / 2;

		if (comparator.compare(elements[currentOffset], elements[fatherOffset]) == 1) {
			swap(currentOffset, fatherOffset);
			percolateUp(fatherOffset);
		}
	}

	/**
	 * Fonction récursive qui échange la place d'un élément avec son fils à la plus haute priorisation si celle-ci est supérieure.
	 * Puis recommence jusqu'à ce que l'élément soit plus prioritaire que ses fils ou est en bas du tas
	 * 
	 * @param currentOffset offset de l'élément à potentiellement déplacer
	 * 
	 */
	private void percolateDown(int currentOffset) {
		int firstChildOffset = 2*currentOffset + 1;
		
		if (firstChildOffset >= capacity() || elements[firstChildOffset] == null) {
			return;
		}

		int choosenChildOffset = firstChildOffset;
		int secondChildOffset = 2*currentOffset + 2;
		if (secondChildOffset < capacity() && elements[secondChildOffset] != null) {
			choosenChildOffset = comparator.compare(elements[firstChildOffset], elements[secondChildOffset]) == 1 ? firstChildOffset : secondChildOffset;
		}

		if (comparator.compare(elements[currentOffset], elements[choosenChildOffset]) == -1) {
			swap(currentOffset, choosenChildOffset);
			percolateDown(choosenChildOffset);
		}
	}

	public void print(PrintStream out) {
		print("", 0, out);
	}

	private void print(String prefix, int index, PrintStream out) {
		if (index < elements.length && elements[index] != null) {
			String leaf = "";
			if (index != 0) {
				if (index == elements.length -1|| elements[index + 1] == null) {
					leaf = "\\--";
				} else {
					leaf = (index%2 == 0 ? "\\--" : "|--");
				}
			}
			out.println(prefix + leaf + elements[index]);
			if (index != 0) {
				prefix = prefix + (index%2 == 0 ? "   " : "|  ");
			}
			print(prefix, index * 2 + 1, out);
			print(prefix, index * 2 + 2, out);
		}
	}
}
