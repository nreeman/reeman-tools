package fr.reeman.tools.heap;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import fr.reeman.tools.heap.BinaryHeap;

public class BinaryHeapTest {
	
	@Test
	public void testCapacity() {
		assertEquals(0, BinaryHeap.builder(String.class, (s1, s2) -> s1.compareTo(s2)).capacity(0).build().capacity());
		assertEquals(1, BinaryHeap.builder(String.class, (s1, s2) -> s1.compareTo(s2)).capacity(1).build().capacity());
		assertEquals(3, BinaryHeap.builder(String.class, (s1, s2) -> s1.compareTo(s2)).capacity(2).build().capacity());
		assertEquals(3, BinaryHeap.builder(String.class, (s1, s2) -> s1.compareTo(s2)).capacity(3).build().capacity());
		assertEquals(7, BinaryHeap.builder(String.class, (s1, s2) -> s1.compareTo(s2)).capacity(4).build().capacity());
		assertEquals(7, BinaryHeap.builder(String.class, (s1, s2) -> s1.compareTo(s2)).capacity(7).build().capacity());
	}

	@Test
	public void testInsertAndExtract() {
		BinaryHeap<Integer> heap = BinaryHeap
									.builder(Integer.class, Comparator.comparingInt(Integer::intValue))
									.capacity(0)
									.build();
		assertEquals(0, heap.size());
		assertEquals(0, heap.capacity());
		
		heap.insert(3);
		assertEquals(1, heap.size());
		assertEquals(1, heap.capacity());

		heap.insert(2);
		assertEquals(2, heap.size());
		assertEquals(3, heap.capacity());

		heap.insert(5);
		assertEquals(3, heap.size());
		assertEquals(3, heap.capacity());

		heap.insert(10);
		assertEquals(4, heap.size());
		assertEquals(7, heap.capacity());

		heap.insert(6).insert(1).insert(7);
		assertEquals(7, heap.size());
		assertEquals(7, heap.capacity());

		heap.insert(4);
		assertEquals(8, heap.size());
		assertEquals(15, heap.capacity());

		heap.insert(6).insert(1).insert(7);

		assertEquals(Integer.valueOf(10), heap.extract());
		assertEquals(Integer.valueOf(7), heap.extract());
		assertEquals(Integer.valueOf(7), heap.extract());
		assertEquals(Integer.valueOf(6), heap.extract());
		assertEquals(Integer.valueOf(6), heap.extract());
		assertEquals(Integer.valueOf(5), heap.extract());
		assertEquals(Integer.valueOf(4), heap.extract());
		assertEquals(Integer.valueOf(3), heap.extract());
		assertEquals(Integer.valueOf(2), heap.extract());
		assertEquals(Integer.valueOf(1), heap.extract());
		assertEquals(Integer.valueOf(1), heap.extract());
	}

	@Test
	public void testInit() {
		BinaryHeap<Integer> heap = BinaryHeap
									.builder(Integer.class, Comparator.comparingInt(Integer::intValue))
									.capacity(0)
									.init(List.of(1, 2, 3, 4, 5))
									.build();

		assertEquals(7, heap.capacity());
		assertEquals(Integer.valueOf(5), heap.extract());
		assertEquals(Integer.valueOf(4), heap.extract());
		assertEquals(Integer.valueOf(3), heap.extract());
		assertEquals(Integer.valueOf(2), heap.extract());
		assertEquals(Integer.valueOf(1), heap.extract());
	}
	
	@Test
	public void print() {
		BinaryHeap<Integer> heap = BinaryHeap
				.builder(Integer.class, (e1, e2) -> e2.compareTo(e1))
				.capacity(0)
				.init(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17))
				.build();
		heap.print(System.out);

		System.out.println();
		
		heap = BinaryHeap
				.builder(Integer.class, (e1, e2) -> e2.compareTo(e1))
				.capacity(127)
				.build();
		for (int i = 1; i < 128; i++) {
			heap.insert(i);
		}
		//heap.print(System.out);
	}
}
