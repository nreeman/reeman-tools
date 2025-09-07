package fr.reeman.tools.qtyitm.builder;

import fr.reeman.tools.qtyitm.QuantityItem;

@FunctionalInterface
public interface QuantityItemBuilder {

	public QuantityItem build(Object object);
	
}
