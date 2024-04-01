package fr.reeman.tools.qtyitm.helper;

import org.junit.Test;

import fr.reeman.tools.qtyitm.Comparaison;
import fr.reeman.tools.qtyitm.QuantityItemSet;

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
public class DisplayTest {

	private Comparaison comparaison() {
		QuantityItemSet set1 = new QuantityItemSet("set1");
		set1.add(1, new ItemHelper<>("Item 1"));
		set1.add(2, new ItemHelper<>("Item 2"));
		set1.add(4, new ItemHelper<>("Item 4"));
		
		QuantityItemSet set2 = new QuantityItemSet("set2");
		set2.add(1, new ItemHelper<>("Item 1"));
		set2.add(4, new ItemHelper<>("Item 2"));
		set2.add(2, new ItemHelper<>("Item 3"));
		
		return Comparaison.compare(set1, set2);
	}
	
	@Test
	public void markdown() {
		MarkdownDisplay markdownDisplay = MarkdownDisplay.builder().zero("-").consoleColor(true).build();
		System.out.println(markdownDisplay.generate(comparaison()));
	}

	@Test
	public void html() {
		HtmlDisplay htmlDisplay = HtmlDisplay.builder().tableClassName("CLASS").tableId("ID").multilines(true).zero("-").build();
		System.out.println(htmlDisplay.generate(comparaison()));
	}
}
