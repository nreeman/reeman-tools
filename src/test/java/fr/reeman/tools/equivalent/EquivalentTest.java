package fr.reeman.tools.equivalent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class EquivalentTest {
	
	@Test
	public void contains() {
		Collection<Integer> integers = Arrays.asList(1, 2, 3, 4);
		
		Equivalent<Integer, String> e = (i, s) -> i.toString().equals(s);
		
		assertEquals(Integer.valueOf(1), EquivalentCollections.contains(integers, "1", e));
		assertEquals(Integer.valueOf(2), EquivalentCollections.contains(integers, "2", e));
		assertEquals(Integer.valueOf(3), EquivalentCollections.contains(integers, "3", e));
		assertEquals(Integer.valueOf(4), EquivalentCollections.contains(integers, "4", e));
		
		assertNull(EquivalentCollections.contains(integers, "0", e));
		assertNull(EquivalentCollections.contains(integers, "5", e));
		assertNull(EquivalentCollections.contains(integers, "", e));
		assertNull(EquivalentCollections.contains(integers, null, e));
	}
	
	@Test
	public void remove() {
		Equivalent<Integer, String> e = (i, s) -> i.toString().equals(s);
		
		removeOK(list(1, 2, 3, 4), "1", e);
		removeOK(list(1, 2, 3, 4), "3", e);
		removeOK(list(1, 1, 1, 1), "1", e);
		removeOK(list(1), "1", e);
		
		removeKO(list(1, 2, 3, 4), null, e);
		removeKO(list(1, 2, 3, 4), "", e);
		removeKO(list(1, 2, 3, 4), "5", e);
	}
	
	private void removeOK(Collection<Integer> ints, String toRemove, Equivalent<Integer, String> e) {
		int expectedSize = ints.size() - 1;
		Integer i = EquivalentCollections.remove(ints, toRemove, e);
		assertEquals(Integer.valueOf(toRemove), i);
		assertEquals(expectedSize, ints.size());
		
	}

	private void removeKO(Collection<Integer> ints, String toRemove, Equivalent<Integer, String> e) {
		int expectedSize = ints.size();
		Integer i = EquivalentCollections.remove(ints, toRemove, e);
		assertNull(i);
		assertEquals(expectedSize, ints.size());
	}

	@Test
	public void minus() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Equivalent<Integer, String> e = (i, s) -> i.toString().equals(s);
		
		assertEquals(list(1, 2, 3, 4), EquivalentCollections.minus(list(1, 2, 3, 4), null, e, new ArrayList<>()));
		assertEquals(list(1, 2, 3, 4), EquivalentCollections.minus(list(1, 2, 3, 4), list(), e, new ArrayList<>()));
		assertEquals(list(1, 2, 3, 4), EquivalentCollections.minus(list(1, 2, 3, 4), list("", "5"), e, new ArrayList<>()));
		assertEquals(list(1, 2, 4), EquivalentCollections.minus(list(1, 2, 3, 4), list("", "5", "3"), e, new ArrayList<>()));
		assertEquals(list(1, 2), EquivalentCollections.minus(list(1, 2, 3, 4), list("4", "", "5", "3"), e, new ArrayList<>()));
		assertNull(EquivalentCollections.minus(null, list(), e, new ArrayList<>()));
	}
	
	@Test
	public void minusAsList() {
		Equivalent<Integer, String> e = (i, s) -> i.toString().equals(s);
		
		assertEquals(list(1, 2, 3, 4), EquivalentCollections.minusAsList(list(1, 2, 3, 4), list(), e));
		assertEquals(list(2, 3, 4), EquivalentCollections.minusAsList(list(1, 2, 3, 4), list("1"), e));
		assertEquals(list(2, 4), EquivalentCollections.minusAsList(list(1, 2, 3, 4), list("1", "3"), e));
		assertEquals(list(2, 4), EquivalentCollections.minusAsList(list(1, 2, 3, 4), list("1", "3", "5"), e));
		assertEquals(list(1, 2, 3, 4), EquivalentCollections.minusAsList(list(1, 2, 3, 4), null, e));
		assertNull(EquivalentCollections.minusAsList(null, list(), e));
	}
	
	@Test
	public void minusAsSet() {
		Equivalent<Integer, String> e = (i, s) -> i.toString().equals(s);
		
		assertEquals(set(1, 2, 3, 4), EquivalentCollections.minusAsSet(list(1, 2, 3, 4), set(), e));
		assertEquals(set(2, 3, 4), EquivalentCollections.minusAsSet(list(1, 2, 3, 4), set("1"), e));
		assertEquals(set(2, 4), EquivalentCollections.minusAsSet(list(1, 2, 3, 4), set("1", "3"), e));
		assertEquals(set(2, 4), EquivalentCollections.minusAsSet(list(1, 2, 3, 4), set("1", "3", "5"), e));
		assertEquals(set(1, 2, 3, 4), EquivalentCollections.minusAsSet(set(1, 2, 3, 4), null, e));
		assertNull(EquivalentCollections.minusAsList(null, set(), e));

		assertEquals(set(1, 2, 3, 4), EquivalentCollections.minusAsSet(list(1, 1, 2, 3, 3, 4), set(), e));
		assertEquals(set(2, 3, 4), EquivalentCollections.minusAsSet(list(1, 2, 3, 4, 4), set("1"), e));
		assertEquals(set(2, 4), EquivalentCollections.minusAsSet(list(1, 2, 3, 4), set("1", "3", "3"), e));
		assertEquals(set(2, 4), EquivalentCollections.minusAsSet(list(1, 2, 3, 4), set("1", "3", "5", "1"), e));
		assertEquals(set(1, 2, 3, 4), EquivalentCollections.minusAsSet(set(1, 2, 3, 4), null, e));
		assertNull(EquivalentCollections.minusAsList(null, set(), e));
	}
	
	public void areEquivalent() {
		Equivalent<Integer, String> e = (i, s) -> i.toString().equals(s);
		
		assertTrue(EquivalentCollections.areEquivalent(list(1, 2, 3), list("1", "2", "3"), e));
		assertTrue(EquivalentCollections.areEquivalent(list(1, 2, 3), list("3", "1", "2"), e));
		assertTrue(EquivalentCollections.areEquivalent(list(), list(), e));
		assertTrue(EquivalentCollections.areEquivalent(list(1, 2, 1), list("1", "1", "2"), e));
		assertTrue(EquivalentCollections.areEquivalent(null, null, e));

		assertFalse(EquivalentCollections.areEquivalent(list(1, 2, 3), list("3", "1"), e));
		assertFalse(EquivalentCollections.areEquivalent(list(1, 2, 3), list("3", "1", "1"), e));
		assertFalse(EquivalentCollections.areEquivalent(null, list("3", "1", "2"), e));
		assertFalse(EquivalentCollections.areEquivalent(list(1, 2, 3), null, e));
	}

	@SafeVarargs
	private <T> List<T> list(T... ts) {
		return new ArrayList<>(Arrays.asList(ts));
	}

	@SafeVarargs
	private <T> Set<T> set(T... ts) {
		return new HashSet<>(Arrays.asList(ts));
	}
}
