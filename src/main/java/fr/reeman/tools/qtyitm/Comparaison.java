package fr.reeman.tools.qtyitm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.reeman.tools.Shortcuts;
import lombok.Getter;
import lombok.NonNull;

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
public class Comparaison {

	private QuantityItemSet[] data;
	
	@Getter
	private Map<Item, Integer[]> itemsOccurences;
	
	@Getter
	private String[] names;
	
	@Getter
	private Map<Item, Integer> intersection;
	
	@Getter
	private Map<Item, Integer> union;
	
	public static Comparaison compare(@NonNull QuantityItemSet... sets) {
		return new Comparaison(sets).doIt(Arrays.asList(sets));
	}
	
	public static Comparaison compare(@NonNull List<QuantityItemSet> sets) {
		return new Comparaison(sets.toArray(QuantityItemSet[]::new)).doIt(sets);
	}
	
	private Comparaison(QuantityItemSet[] data) {
		this.data = data;
		this.itemsOccurences = new HashMap<Item, Integer[]>();
		this.names = new String[data.length];
		this.intersection = new HashMap<Item, Integer>();
		this.union = new HashMap<Item, Integer>();
	}

	private Comparaison doIt(List<QuantityItemSet> sets) {
		for (int i = 0; i < sets.size(); i++) {
			add(sets.get(i), i);
		}
		
		itemsOccurences.forEach((k, v) -> intersection.put(k, Shortcuts.minNullIsZero(v)));
		itemsOccurences.forEach((k, v) -> union.put(k, Shortcuts.maxNullIsZero(v)));
		
		return this;
	}

	private void add(QuantityItemSet quantityItemSet, int rank) {
		names[rank] = (quantityItemSet.getName() == null || "".equals(quantityItemSet.getName())) ? "List " + rank : quantityItemSet.getName();
		for (QuantityItem quantityItem : quantityItemSet) {
			Integer[] numbers = itemsOccurences.get(quantityItem.getItem());
			if (numbers == null) {
				numbers = new Integer[data.length];
				itemsOccurences.put(quantityItem.getItem(), numbers);
			}
			numbers[rank] = quantityItem.getQuantity();
		}
	}

	public List<Item> getSortedItem() {
		List<Item> items = new ArrayList<Item>(itemsOccurences.keySet());
		Collections.sort(items);
		return items;
	}
}
