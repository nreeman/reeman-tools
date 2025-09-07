package fr.reeman.tools.qtyitm.builder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import fr.reeman.tools.qtyitm.QuantityItemSet;

public class QuantityItemSetBuilder {
	
	public static QuantityItemSet oneQtyItemByLineFileBuild(String name, String path, QuantityItemBuilder quantityItemBuilder) throws FileNotFoundException {
		return oneQtyItemByLineFileBuild(name, new BufferedReader(new FileReader(path)), quantityItemBuilder);
	}

	public static QuantityItemSet oneQtyItemByLineFileBuild(String name, BufferedReader bufferedReader, QuantityItemBuilder quantityItemBuilder) {
		QuantityItemSet set = new QuantityItemSet(name);
		bufferedReader.lines().forEach(
			line -> set.add(quantityItemBuilder.build(line))
		);
		return set;
	}

}
