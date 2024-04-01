package fr.reeman.tools.qtyitm.helper;

import java.util.Map;

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
public class HtmlDisplay {

	@Builder.Default private String zero = "0";
	private String tableId;
	private String tableClassName;
	private boolean pretty;

	public String generate(Comparaison comparaison) {
		SuperStringBuffer ssb = new SuperStringBuffer("<table%s%s>%S", tableId == null ? "" : " id='" + tableId + "'", tableClassName == null ? "" : " class='" + tableClassName + "'", pretty ? "\n" : "");
		
		// Header
		ssb.append("<tr class='qtyitm-header'><th></th>");
		String[] names = comparaison.getNames();
		for (int i = 0; i < names.length; i++) {
			ssb.append("<th>%s</th>", names[i]);
		}
		ssb.append("</tr>%s", pretty ? "\n" : "");
		
		// Body
		Map<Item, Integer[]> occurences = comparaison.getItemsOccurences();
		for (Item item : comparaison.getSortedItem()) {
			Integer[] occ = occurences.get(item);
			ssb.append("<tr class='%s'>", rowClassName(occ));
			for (int i = 0; i < occ.length; i++) {
				ssb.append("<td>%s</td>", occ[i] == null ? zero : occ[i].toString());
			}
			ssb.append("</tr>%s", pretty ? "\n" : "");
		}

		return ssb.toString("</table>");
	}

	private String rowClassName(Integer[] occ) {
		String rowClassName = "qtyitm-equals";
		
		if (occ.length > 1 && occ[0] != null) {
			for (int i = 1; i < occ.length; i++) {
				if (occ[i] == null) {
					return "qtyitm-zero";
				}
				if (occ[0] != occ[i]) {
					rowClassName = "qtyitm-diff";
				}
			}
		}
		
		if (occ[0] == null) {
			rowClassName = "qtyitm-zero";
		}

		return rowClassName;
	}
}
