package fr.reeman.tools.qtyitm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

//Copyright (C) 2024 Reeman Nicolas
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
public class QuantityItemSet implements Set<QuantityItem> {
	
	private String name;
	private Map<Item, QuantityItem> items;

	private class QuantityItemIterator implements Iterator<QuantityItem> {
		private Map<Item, QuantityItem> map;
		private List<Item> sortedKeys;
		private int index = 0;
		
		private QuantityItemIterator(Map<Item, QuantityItem> map, List<Item> sortedKeys) {
			this.map = new HashMap<Item, QuantityItem>(map);
			this.sortedKeys  = sortedKeys;
		}

		@Override
		public boolean hasNext() {
			return index < sortedKeys.size();
		}

		@Override
		public QuantityItem next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			index++;
			return map.get(sortedKeys.get(index - 1));
		}
	}
	
	public QuantityItemSet(String name) {
		this.setName(name);
		this.items = new HashMap<>();
	}
	
	public QuantityItemSet() {
		this(null);
	}

	public boolean add(Item item) {
		return add(1, item);
	}
	
	public boolean add(int quantity, Item item) {
		if (quantity < 0) {
			throw new UnsupportedOperationException("Quantity cannot be increased with a negative value.");
		}

		QuantityItem quantityItem = items.get(item);
		
		if (quantityItem == null) {
			items.put(item, new QuantityItem(quantity, item));
		} else {
			items.put(item, new QuantityItem(quantity + quantityItem.getQuantity(), item));
		}
		
		return quantityItem == null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof Item) {
			return items.keySet().contains(o);
		}
		return false;
	}

	@Override
	public Iterator<QuantityItem> iterator() {
		return new QuantityItemIterator(items, sortedKeys());
	}

	@Override
	public Object[] toArray() {
		QuantityItem[] array = new QuantityItem[items.size()];
		List<Item> sortedKeys = sortedKeys(); 
		for (int i = 0; i < sortedKeys.size(); i++) {
			array[i] = items.get(sortedKeys.get(i));
		}
		
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		QuantityItem[] array = new QuantityItem[items.size()];
		List<Item> sortedKeys = sortedKeys();
		for (int i = 0; i < sortedKeys.size(); i++) {
			QuantityItem qtyItem = items.get(sortedKeys.get(i));
			if (a.length < i) {
				a[i] = (T)qtyItem;
			}
			
			array[i] = qtyItem;
		}
		
		if (a.length > sortedKeys.size()) {
			a[sortedKeys.size()] = null;
		}
		
		return (T[]) array;
	}

	@Override
	public boolean add(QuantityItem e) {
		return add(e.getQuantity(), e.getItem());
	}

	public boolean remove(int quantityToRemove, Item item) {
		if (quantityToRemove < 0) {
			throw new UnsupportedOperationException("Quantity removed cannot be a negative value.");
		}

		QuantityItem quantityItem = items.get(item);
		if (quantityItem != null) {
			int oldQty = quantityItem.getQuantity();
			int newQty = oldQty - quantityToRemove;

			if (newQty <= 0) {
				items.remove(item);
			} else {
				items.put(item, new QuantityItem(newQty, item));
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean remove(Item item) {
		return items.remove(item) != null;
	}
	
	public boolean remove(QuantityItem quantityItem) {
		return remove(quantityItem.getQuantity(), quantityItem.getItem());
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof QuantityItem) {
			return remove((QuantityItem)o);
		} else if (o instanceof Item) {
			return remove((Item)o);
		}
		
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends QuantityItem> c) {
		boolean updated = false;
		
		for (QuantityItem quantityItem : c) {
			updated = updated || add(quantityItem);
		}
		
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean updated = false;
		
		for (Object object : c) {
			updated = updated || remove(object);
		}
		
		return updated;
	}

	@Override
	public void clear() {
		items.clear();
	}

	private List<Item> sortedKeys() {
		List<Item> list = new ArrayList<Item>(items.keySet());
		Collections.sort(list);
		return list;

	}
}
