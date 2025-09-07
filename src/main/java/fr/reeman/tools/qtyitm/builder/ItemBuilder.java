package fr.reeman.tools.qtyitm.builder;

import fr.reeman.tools.qtyitm.Item;
import fr.reeman.tools.qtyitm.helper.ItemHelper;

@FunctionalInterface
public interface ItemBuilder {

	public ItemBuilder SIMPLE_STRING_ITEM_BUILDER = new ItemBuilder() {
		public Item build(Object object) {
			return object == null ? null : new ItemHelper<String>(object.toString());
		}
	};


	Item build(Object object);
	
}
