package fr.reeman.tools.qtyitm.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import fr.reeman.tools.qtyitm.Comparaison;
import fr.reeman.tools.qtyitm.Operations;
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

	private Pattern RIFTMANA_PATTERN = Pattern.compile("^(\\d+) (.+)$");
	
	private String DARTILL = """
			1 Viktor, Herald of the Arcane
			
			1 Viktor, Leader
			
			2 Seal of Unity
			3 Stupefy
			1 Blastcone Fae
			3 Hidden Blade
			3 Soaring Scout
			3 Cull the Weak
			3 Watchful Sentry
			2 Lecturing Yordle
			3 Faithful Manufactor
			3 Vanguard Captain
			1 Consult the Past
			3 Sprite Mother
			1 Falling Comet
			1 Machine Evangel
			2 Grand Strategem
			3 Singularity
			2 Thousand-Tailed Watcher
			
			-1 Trifarian War Camp
			-1 Obelisk of Power
			-1 The Arena's Greatest
			
			-7 Order Rune
			-5 Mind Rune
			
			Sideboard:
			-2 Salvage
			-2 Shen, Kinkou
			-2 Leona, Determined
			-1 Thousand-Tailed Watcher
			-1 Dr. Mundo, Expert""";
	
	private String JINX_PRECON = """
			1 Jinx Loose Cannon

			1 Vi Destructive
			1 Jinx Demolitionist
			3 Chemtech Enforcer
			3 Traveling Merchant
			3 Flame Chompers
			2 Cemetery Attendant
			3 Raging Soul
			2 Blazing Scorcher
			2 Undercover Agent
			3 Brazen Buccaneer
			1 Magma Wurm
			1 Rhasa the Sunderer
			3 Gust
			3 Fight or Flight
			2 Get Excited!
			2 Void Seeker
			2 Fading Memories
			3 Scrapheap
			
			1 Targon's Peak
			1 Zaun Warrens
			1 Reaver's Row
			
			6 Fury Rune
			6 Chaos Rune""";

	private String LEE_SIN_PRECON = """
			1 Lee Sin Blind Monk

			1 Lee Sin Centered
			1 Udyr Wildman
			3 Stalwart Poro
			3 Pit Rookie
			3 Wielder of Water
			3 First Mate
			2 Pakaa Cub
			3 Wildclaw Shaman
			3 Wizened Elder
			2 Bilgewater Bully
			2 Stormclaw Ursine
			2 Mountain Drake
			2 Charm
			3 Challenge
			3 Discipline
			2 Stand United
			1 Mask of Foresight
			1 Mistfall
			
			1 Monastery of Hirana
			1 Targon's Peak
			1 Grove of the God-Willow
			
			6 Calm Rune
			6 Body Rune""";

	private String VIKTOR_PRECON = """
			1 Viktor Herald of the Arcane

			1 Heimerdinger Inventor
			1 Viktor Innovator
			3 Soaring Scout
			3 Ravenbloom Student
			3 Eager Apprentice
			3 Noxian Drummer
			3 Cruel Patron
			2 Jeweled Colossus
			1 Wraith of Echoes
			2 Stupefy
			3 Cull the Weak
			2 Hidden Blade
			2 Smoke Screen
			2 Back to Back
			2 Sprite Call
			2 Consult the Past
			1 Grand Strategem
			2 Orb of Regret
			2 Mushroom Pouch

			1 Trifarian War Camp
			1 The Grand Plaza
			1 Altar to Unity

			6 Mind Rune
			6 Order Rune""";

	private String VIKTOR_META = """
			1 Viktor Herald of the Arcane
			
			1 Viktor Leader
			3 Ravenbloom Student
			3 Daring Poro
			3 Faithful Manufactor
			3 Vanguard Captain
			3 Cruel Patron
			3 Sprite Mother
			2 Thousand-Tailed Watcher
			3 Stupefy
			2 Retreat
			2 Convergent Mutation
			3 Hidden Blade
			3 Smoke Screen
			2 Cull the Weak
			2 Grand Strategem
			2 Singularity
			
			1 Trifarian War Camp
			1 Obelisk of Power
			1 The Candlelit Sanctum
			
			6 Order Rune
			6 Mind Rune""";

	private String V1 = """
			1 Viktor, Herald of the Arcane
			
			1 Viktor, Leader
			
			1 Retreat
			1 Blastcone Fae
			3 Hidden Blade
			3 Daring Poro
			3 Soaring Scout
			1 Facebreaker
			3 Cull the Weak
			2 Lecturing Yordle
			3 Faithful Manufactor
			2 Vanguard Captain
			2 Cruel Patron
			1 Consult the Past
			2 Sprite Mother
			1 Falling Comet
			2 Imperial Decree
			1 Machine Evangel
			2 Grand Strategem
			1 Riptide Rex
			3 Singularity
			2 Thousand-Tailed Watcher
			
			-1 Trifarian War Camp
			-1 Vilemaw's Lair
			-1 The Arena's Greatest
			
			-7 Order Rune
			-5 Mind Rune
			
			Sideboard:
			-1 Shen, Kinkou
			-2 Leona, Determined
			-2 Vengeance
			-1 Sprite Mother
			-2 Machine Evangel""";
	

	private String V2 = """
			1 Viktor, Herald of the Arcane
			
			1 Viktor, Leader
			
			1 Seal of Unity
			2 Stupefy
			1 Salvage
			3 Hidden Blade
			3 Soaring Scout
			3 Cull the Weak
			3 Watchful Sentry
			3 Noxian Drummer
			3 Faithful Manufactor
			2 Consult the Past
			3 Sprite Mother
			2 Falling Comet
			1 Imperial Decree
			2 Machine Evangel
			2 Grand Strategem
			2 Singularity
			2 Thousand-Tailed Watcher
			1 Harnessed Dragon
			
			-1 The Grand Plaza
			-1 Trifarian War Camp
			-1 The Arena's Greatest
			
			-7 Order Rune
			-5 Mind Rune
			
			Sideboard:
			-2 Salvage
			-3 Leona, Determined
			-1 Imperial Decree
			-1 Commander Ledros
			-1 Singularity""";
	
	private String V3 = """
			1 Viktor, Herald of the Arcane
			
			1 Viktor, Leader
			
			3 Stupefy
			3 Ravenbloom Student
			3 Hidden Blade
			3 Smoke Screen
			3 Daring Poro
			3 Soaring Scout
			3 Cull the Weak
			3 Lecturing Yordle
			3 Cruel Patron
			3 Sprite Mother
			2 Falling Comet
			2 Grand Strategem
			2 Riptide Rex
			2 Singularity
			1 Thousand-Tailed Watcher
			
			-1 Vilemaw's Lair
			-1 Obelisk of Power
			-1 The Candlelit Sanctum
			
			-6 Order Rune
			-6 Mind Rune
			
			Sideboard:
			-2 Salvage
			-2 Shen, Kinkou
			-2 Leona, Determined
			-1 Imperial Decree
			-1 Singularity""";
	
	private String SMILOU = """
			1 Viktor, Herald of the Arcane

			1 Viktor, Leader
			
			3 Stupefy
			3 Ravenbloom Student
			3 Hidden Blade
			2 Daring Poro
			2 Siphon Power
			3 Soaring Scout
			3 Cull the Weak
			2 Watchful Sentry
			3 Noxian Drummer
			3 Faithful Manufactor
			3 Vanguard Captain
			1 Machine Evangel
			3 Grand Strategem
			2 Singularity
			2 Recruit the Vanguard
			1 Thousand-Tailed Watcher
			
			1 Zaun Warrens
			1 The Grand Plaza
			1 Trifarian War Camp
			
			7 Order Rune
			5 Mind Rune
			
			-Sideboard:
			-2 Salvage
			-2 Imperial Decree
			-2 Machine Evangel
			-2 Dr. Mundo, Expert""";
	
	private final String SMILOU_2 = """
			1 Viktor, Herald of the Arcane

			1 Viktor, Leader
			
			3 Seal of Unity
			3 Stupefy
			2 Hidden Blade
			3 Daring Poro
			3 Soaring Scout
			3 Cull the Weak
			3 Watchful Sentry
			3 Shen, Kinkou
			3 Faithful Manufactor
			1 Spectral Matron
			3 Cruel Patron
			3 Consult the Past
			3 Vengeance
			3 Harnessed Dragon
			
			-1 Targon's Peak
			-1 Trifarian War Camp
			-1 Vilemaw's Lair
			
			-7 Order Rune
			-5 Mind Rune
			
			-Sideboard:
			-3 Salvage
			-3 Commander Ledros
			-2 Singularity""";

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
	
	@Test
	public void riftbound() {
		List<QuantityItemSet> sets = new ArrayList<>();
//		sets.add(riftmanaFormatToQuantityItemSet("Viktor precon", VIKTOR_PRECON));
//		sets.add(riftmanaFormatToQuantityItemSet("Viktor Meta", VIKTOR_META));
		sets.add(riftmanaFormatToQuantityItemSet("29th National", V1));
		sets.add(riftmanaFormatToQuantityItemSet("1st National Open", V2));
		//sets.add(riftmanaFormatToQuantityItemSet("Control 6-0", V3));
		//sets.add(riftmanaFormatToQuantityItemSet("Smilou", SMILOU));
		sets.add(riftmanaFormatToQuantityItemSet("Dartill", DARTILL));
//		sets.add(riftmanaFormatToQuantityItemSet("Smilou Control", SMILOU_2));

		MarkdownDisplay markdownDisplay = MarkdownDisplay
				.builder()
				.zero("-")
				.consoleColor(true)
				.union(false)
				.build();
		System.out.println(markdownDisplay.generate(Comparaison.compare(sets)));
	}

	private QuantityItemSet riftmanaFormatToQuantityItemSet(String name, String... riftmanaFormats) {
		QuantityItemSet quantityItemSet = new QuantityItemSet(name);
		
		for (String riftmanaFormat : riftmanaFormats) {
			String[] lines = riftmanaFormat.split("\n");
			for (String line : lines) {
				if (line.length() != 0) {
					Matcher matcher = RIFTMANA_PATTERN.matcher(line);
					if (matcher.matches()) {
						quantityItemSet.add(Integer.valueOf(matcher.group(1)), new ItemHelper<>(matcher.group(2)));
						
					} else {
//						System.err.println("Does not match ! : " + line);
					}
				}
			}
		}
		
		return quantityItemSet;
	}
	
	@Test
	public void intersection() {
		QuantityItemSet intersection = Operations.intersection(
				//riftmanaFormatToQuantityItemSet("Viktor precon", VIKTOR_PRECON),
				//riftmanaFormatToQuantityItemSet("Viktor Meta", VIKTOR_META)
				riftmanaFormatToQuantityItemSet("29th National", V1),
				riftmanaFormatToQuantityItemSet("1st National Open", V2)
		);
		
		intersection.forEach(qi -> {
			System.out.println(qi.getQuantity() + " " + qi.getItem());
		});
		intersection = Operations.intersection(
				//riftmanaFormatToQuantityItemSet("Viktor precon", VIKTOR_PRECON),
				//riftmanaFormatToQuantityItemSet("Viktor Meta", VIKTOR_META)
				riftmanaFormatToQuantityItemSet("29th National", V1),
				riftmanaFormatToQuantityItemSet("1st National Open", V2)
				//, riftmanaFormatToQuantityItemSet("Smilou", SMILOU)
		);
		
		intersection.forEach(qi -> {
			System.out.println(qi.getQuantity() + " " + qi.getItem());
		});
	}
	
}
