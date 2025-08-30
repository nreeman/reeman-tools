package fr.reeman.tools.qtyitm.helper;

import java.util.Collection;
import java.util.Map;

import fr.reeman.tools.ConsoleColors;
import fr.reeman.tools.SuperStringBuffer;
import fr.reeman.tools.qtyitm.Comparaison;
import fr.reeman.tools.qtyitm.Item;
import lombok.Builder;

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
@Builder
public class MarkdownDisplay extends Display {
	
	@Builder.Default private String zero = "0";
	private boolean consoleColor;

	@Override
	public String generate(Comparaison comparaison) {
		SuperStringBuffer ssb = new SuperStringBuffer();
		Map<Item, Integer[]> occurences = comparaison.getItemsOccurences();
		String[] names = comparaison.getNames();
		int[] occurenceMaxLength = new int[comparaison.getNames().length];
		int labelMaxLength = maxToStringLength(comparaison.getItemsOccurences().keySet());
		for (int i = 0; i < names.length; i++) {
			occurenceMaxLength[i] = names[i].length();
			for (Integer[] occ : occurences.values()) {
				occurenceMaxLength[i] = Math.max(occurenceMaxLength[i], occ[i] == null ? zero.length() : occ[i].toString().length());
			}
		}
		
		// En-tête du label
		ssb.append("|").repeat(labelMaxLength + 2, " ").append("|");
		// En-tête des quantités
		for (int i = 0; i < names.length; i++) {
			ssb.append(" %-" + occurenceMaxLength[i] + "s |", names[i]);
		}
		// Séparateur
		ssb.append("\n");
		ssb.append("|:").repeat(labelMaxLength + 1, "-").append("|");
		for (int i = 0; i < names.length; i++) {
			ssb.repeat(occurenceMaxLength[i] + 1, "-").append(":|");
		}
		ssb.append("\n");

		// Les labels et quantités
		for (Item item : comparaison.getSortedItem()) {
			ssb.append("| %-" + labelMaxLength + "s |", item);
			Integer[] occ = occurences.get(item);
			for (int i = 0; i < occ.length; i++) {
				
				String cc = consoleColor ? consoleColor(occ).toString() : "";
				String reset = consoleColor ? ConsoleColors.RESET.toString() : "";
				
				if (occ[i] == null) {
					ssb.append(" %s%" + occurenceMaxLength[i] + "s%s |", cc, zero, reset);
				} else {
					ssb.append(" %s%" + occurenceMaxLength[i] + "d%s |", cc, occ[i], reset);
				}
				
				ssb.append(ConsoleColors.RESET);
			}
			ssb.append("\n");
		}

		
		return ssb.toString();
	}
	
	private ConsoleColors consoleColor(Integer[] occ) {
		ConsoleColors consoleColors= ConsoleColors.GREEN;
		
		if (occ.length > 1 && occ[0] != null) {
			for (int i = 1; i < occ.length; i++) {
				if (occ[i] == null) {
					return ConsoleColors.RED;
				}
				if (occ[0] != occ[i]) {
					consoleColors = ConsoleColors.YELLOW;
				}
			}
		}
		
		if (occ[0] == null) {
			consoleColors = ConsoleColors.RED;
		}
		
		return consoleColors;
	}

	private int maxToStringLength(Collection<?> objects) {
		int l = 0;
		
		for (Object object : objects) {
			l = Math.max(l, object.toString().length());
		}
		
		return l;
	}
	
}
