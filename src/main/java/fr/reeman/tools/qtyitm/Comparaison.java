package fr.reeman.tools.qtyitm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Getter
	private Map<Item, Integer[]> itemsOccurences;
	
	@Getter
	private String[] names;
	
	@Getter
	private int size;
	
	public static Comparaison compare(@NonNull QuantityItemSet... sets) {
		return new Comparaison(sets.length).doIt(Arrays.asList(sets));
	}
	
	public static Comparaison compare(@NonNull List<QuantityItemSet> sets) {
		return new Comparaison(sets.size()).doIt(sets);
	}
	
	private Comparaison(int size) {
		this.itemsOccurences = new HashMap<Item, Integer[]>();
		this.names = new String[size];
		this.size = size;
	}

	private Comparaison doIt(List<QuantityItemSet> sets) {
		for (int i = 0; i < sets.size(); i++) {
			add(sets.get(i), i);
		}
		
		return this;
	}

	private void add(QuantityItemSet quantityItemSet, int rank) {
		names[rank] = (quantityItemSet.getName() == null || "".equals(quantityItemSet.getName())) ? "List " + rank : quantityItemSet.getName();
		for (QuantityItem quantityItem : quantityItemSet) {
			Integer[] numbers = itemsOccurences.get(quantityItem.getItem());
			if (numbers == null) {
				numbers = new Integer[size];
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
