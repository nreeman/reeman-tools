package fr.reeman.tools.qtyitm;

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
public class QuantityItem {

	private int quantity;
	private Item item;
	
	public QuantityItem(int quantity, Item item) {
		if (quantity <= 0) {
			throw new UnsupportedOperationException("Quantity for a 'QuantityItem' object must be a positive value.");
		}
		this.setQuantity(quantity);
		this.setItem(item);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void increase(int value) {
		if (value < 0) {
			throw new UnsupportedOperationException("Quantity cannot be increase with a negative value.");
		}
		quantity += value;
	}
}
