package fr.reeman.tools.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BinaryHeapTest {
	
	@Test
	public void testCapacity() {
		assertEquals(0, new BinaryHeap<String>((s1, s2) -> s1.compareTo(s2), 0).capacity());
		assertEquals(1, new BinaryHeap<String>((s1, s2) -> s1.compareTo(s2), 1).capacity());
		assertEquals(3, new BinaryHeap<String>((s1, s2) -> s1.compareTo(s2), 2).capacity());
		assertEquals(3, new BinaryHeap<String>((s1, s2) -> s1.compareTo(s2), 3).capacity());
		assertEquals(7, new BinaryHeap<String>((s1, s2) -> s1.compareTo(s2), 4).capacity());
		assertEquals(7, new BinaryHeap<String>((s1, s2) -> s1.compareTo(s2), 7).capacity());
	}

	@Test
	public void testInsertAndExtract() {
		BinaryHeap<Integer> heap = new BinaryHeap<>(Comparator.comparingInt(Integer::intValue), 0);
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
		BinaryHeap<Integer> heap = new BinaryHeap<>(Comparator.comparingInt(Integer::intValue), 0, List.of(1, 2, 3, 4, 5));

		assertEquals(7, heap.capacity());
		assertEquals(Integer.valueOf(5), heap.extract());
		assertEquals(Integer.valueOf(4), heap.extract());
		assertEquals(Integer.valueOf(3), heap.extract());
		assertEquals(Integer.valueOf(2), heap.extract());
		assertEquals(Integer.valueOf(1), heap.extract());
	}
	
	@Test
	public void print() {
		BinaryHeap<Integer> heap = new BinaryHeap<>((i1, i2) -> i2.compareTo(i1), 0, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17));
		heap.print(System.out);

		System.out.println();
		
		heap = new BinaryHeap<>((i1, i2) -> i2.compareTo(i1), 127);
		for (int i = 1; i < 128; i++) {
			heap.insert(i);
		}
		//heap.print(System.out);
		
//		System.out.println(heap);
	}
}
