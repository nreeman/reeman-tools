package fr.reeman.tools.qtyitm.helper;

import fr.reeman.tools.qtyitm.Item;
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
public class ItemHelper<T extends Comparable<T>> implements Item {
	
	private Comparable<T> comparable;

	public ItemHelper(@NonNull Comparable<T> comparable) {
		this.comparable = comparable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(@NonNull Item item) {
		return this.comparable.compareTo( (T)((ItemHelper<T>)item).comparable );
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		if (object instanceof ItemHelper<?>) {
//			ItemHelper<?> helper = (ItemHelper<?>)object;

//			if (comparable == null && helper.comparable == null) {
//				return true;
//			}
//			
//			if (comparable == null || helper == null) {
//				return false;
//			}
			
			try {
				T t = (T)((ItemHelper<?>)object).comparable;
				return comparable.equals(t);
			} catch (ClassCastException e) {
				return false;
			}
		}
			
		return false;
	}
	
	@Override
	public int hashCode() {
		return comparable.hashCode();
	}
	
	@Override
	public String toString() {
		return comparable.toString();
	}

}
