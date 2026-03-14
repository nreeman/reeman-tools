package fr.reeman.tools.comics;

import static fr.reeman.tools.ImmutablePair.pair;
import static fr.reeman.tools.comics.enums.TPBTitleName.ALIAS_RED_BAND_VOL_1_TPB;
import static fr.reeman.tools.comics.enums.TPBTitleName.CYCLOPS_VOL_4_TPB;
import static fr.reeman.tools.comics.enums.TPBTitleName.MAGIK_AND_COLOSSUS_VOL_1_TPB;
import static fr.reeman.tools.comics.enums.TPBTitleName.MARVEL_KNIGHTS_PUNISHER_VOL_1_TBP;
import static fr.reeman.tools.comics.enums.TPBTitleName.PHOENIX_VOL_1_TPB;
import static fr.reeman.tools.comics.enums.TPBTitleName.PSYLOCKE_VOL_2_TPB;
import static fr.reeman.tools.comics.enums.TPBTitleName.STORM_EARTH_S_MIGHTIEST_MUTANT_VOL_1_TPB;
import static fr.reeman.tools.comics.enums.TPBTitleName.X_FORCE_VOL_7_TPB;
import static fr.reeman.tools.comics.enums.TitleName.ALIAS_RED_BAND;
import static fr.reeman.tools.comics.enums.TitleName.ASM;
import static fr.reeman.tools.comics.enums.TitleName.ASM_VENOM_DEATH_SPIRAL;
import static fr.reeman.tools.comics.enums.TitleName.ASM_VENOM_DEATH_SPIRAL_DEATH_SPIRAL;
import static fr.reeman.tools.comics.enums.TitleName.AVENGERS;
import static fr.reeman.tools.comics.enums.TitleName.CYCLOPS;
import static fr.reeman.tools.comics.enums.TitleName.EMMA_FROST_THE_WHITE_QUEEN;
import static fr.reeman.tools.comics.enums.TitleName.EXCEPTIONAL_X_MEN;
import static fr.reeman.tools.comics.enums.TitleName.IMPERIAL;
import static fr.reeman.tools.comics.enums.TitleName.IMPERIAL_WAR_BLACK_PANTHER;
import static fr.reeman.tools.comics.enums.TitleName.IMPERIAL_WAR_EXILES;
import static fr.reeman.tools.comics.enums.TitleName.IMPERIAL_WAR_IMPERIAL_GUARDIANS;
import static fr.reeman.tools.comics.enums.TitleName.IMPERIAL_WAR_NOVA_CENTURION;
import static fr.reeman.tools.comics.enums.TitleName.IMPERIAL_WAR_PLANET_SHE_HULK;
import static fr.reeman.tools.comics.enums.TitleName.LONGSHOTS;
import static fr.reeman.tools.comics.enums.TitleName.MAGIK;
import static fr.reeman.tools.comics.enums.TitleName.MAGIK_AND_COLOSSUS;
import static fr.reeman.tools.comics.enums.TitleName.MARVEL_KNIGHTS_PUNISHER;
import static fr.reeman.tools.comics.enums.TitleName.MARVEL_KNIGHTS_THE_WORLD_TO_COME;
import static fr.reeman.tools.comics.enums.TitleName.NYX;
import static fr.reeman.tools.comics.enums.TitleName.PHOENIX;
import static fr.reeman.tools.comics.enums.TitleName.PSYLOCKE;
import static fr.reeman.tools.comics.enums.TitleName.ROGUE;
import static fr.reeman.tools.comics.enums.TitleName.ROGUE_STORM;
import static fr.reeman.tools.comics.enums.TitleName.STORM;
import static fr.reeman.tools.comics.enums.TitleName.STORM_EARTH_S_MIGHTIEST_MUTANT;
import static fr.reeman.tools.comics.enums.TitleName.STORM_LIFEDREAM;
import static fr.reeman.tools.comics.enums.TitleName.UNCANNY_X_MEN;
import static fr.reeman.tools.comics.enums.TitleName.UNCANNY_X_MEN_ANNUAL;
import static fr.reeman.tools.comics.enums.TitleName.X_FACTOR;
import static fr.reeman.tools.comics.enums.TitleName.X_FORCE;
import static fr.reeman.tools.comics.enums.TitleName.X_MANHUNT_OMEGA;
import static fr.reeman.tools.comics.enums.TitleName.X_MEN;
import static fr.reeman.tools.comics.enums.TitleName.X_MEN_AGE_OF_REVELATION;
import static fr.reeman.tools.comics.enums.TitleName.X_MEN_AGE_OF_REVELATION_FINALE;
import static fr.reeman.tools.comics.enums.TitleName.X_MEN_AGE_OF_REVELATION_OVERTURE;
import static fr.reeman.tools.comics.enums.TitleName.X_MEN_ANNUAL;
import static fr.reeman.tools.comics.enums.TitleName.X_MEN_HELLFIRE_VIGIL;
import static fr.reeman.tools.comics.enums.TitleName.X_MEN_UNITED;
import static fr.reeman.tools.comics.enums.TitleStatus.ACTIVE;
import static fr.reeman.tools.comics.enums.TitleStatus.FINISHED;
import static fr.reeman.tools.comics.enums.TitleType.ANNUAL;
import static fr.reeman.tools.comics.enums.TitleType.LIMITED;
import static fr.reeman.tools.comics.enums.TitleType.ONE_SHOT;
import static fr.reeman.tools.comics.enums.TitleType.ONGOING;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import fr.reeman.tools.ImmutablePair;
import fr.reeman.tools.comics.bo.Issue;
import fr.reeman.tools.comics.bo.TPBTitle;
import fr.reeman.tools.comics.bo.Title;
import fr.reeman.tools.comics.bo.TradePaperback;
import fr.reeman.tools.comics.enums.TPBTitleName;
import fr.reeman.tools.comics.enums.TitleName;


/**
 *
 * https://yuml.me/diagram/scruffy/class/draw
 * 
[Title|TitleName name;int volume;TitleType type;int year; TitleStatus status;String url]
[Issue|String name;int number;String date, boolean owned]
[TPBTitle|TPBTitleName name;String url]
[TradePaperback|int number;String name;String date; boolean owned]
[Event|String name;int year]

[Title]1-0..n[Issue]
[TPBTitle]0..1-1[Title]
[TPBTitle]0..1-1..n[TradePaperback]
[TradePaperback]0..1-1..n[Issue]
[Event]0..n-0..n[Issue]

 */
public class Collec {
	
	private static Collec collec = null;
	
	private List<Title> titles = new ArrayList<>();
	private List<Issue> issues = new ArrayList<>();
	private List<TPBTitle> tpbTitles = new ArrayList<>();
	private List<TradePaperback> tradePaperbacks = new ArrayList<>();
	private List<Issue> watchlist = null;
	

	public static synchronized Collec getInstance() {
		if (collec == null) {
			collec = new Collec();
		}
		return collec;
	}
	
	private Collec() {
		buildActiveTitles();
		buildFinishedTitles();

		buildActiveIssues();
		buildFinishedIssues();

		buildTPBTitles();
		buildFinishedTPBTitles();
		
		buildTradePaperbacks();
		buildFinishedTradePaperbacks();
		
		watchlist = getIssues(i -> {
			return false;
		});
	}
	
	private void buildActiveTitles() {
		titles.add(new Title(ALIAS_RED_BAND, 1, LIMITED, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Alias:_Red_Band_Vol_1"));

		titles.add(new Title(ASM, 7, ONGOING, 2025, ACTIVE, "https://marvel.fandom.com/wiki/Amazing_Spider-Man_Vol_7"));
		titles.add(new Title(ASM_VENOM_DEATH_SPIRAL, 1, ONE_SHOT, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Amazing_Spider-Man/Venom:_Death_Spiral_Vol_1"));
		titles.add(new Title(ASM_VENOM_DEATH_SPIRAL_DEATH_SPIRAL, 1, ONE_SHOT, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Amazing_Spider-Man/Venom:_Death_Spiral_Body_Count_Vol_1"));
		titles.add(new Title(AVENGERS, 9, ONGOING, 2023, ACTIVE, "https://marvel.fandom.com/wiki/Avengers_Vol_9"));
		
		titles.add(new Title(CYCLOPS, 4, LIMITED, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Cyclops_Vol_4"));

		titles.add(new Title(MAGIK_AND_COLOSSUS, 1, LIMITED, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Magik_%26_Colossus_Vol_1"));
		titles.add(new Title(MARVEL_KNIGHTS_PUNISHER, 1, LIMITED, 2025, ACTIVE, "https://marvel.fandom.com/wiki/Marvel_Knights:_Punisher_Vol_1"));
		titles.add(new Title(MARVEL_KNIGHTS_THE_WORLD_TO_COME, 1, LIMITED, 2025, ACTIVE, "https://marvel.fandom.com/wiki/Marvel_Knights:_The_World_To_Come_Vol_1"));

		titles.add(new Title(ROGUE, 4, LIMITED, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Rogue_Vol_4"));
		
		titles.add(new Title(STORM_EARTH_S_MIGHTIEST_MUTANT, 1, LIMITED, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Storm:_Earth%27s_Mightiest_Mutant_Vol_1"));

		titles.add(new Title(UNCANNY_X_MEN, 6, ONGOING, 2024, ACTIVE, "https://marvel.fandom.com/wiki/Uncanny_X-Men_Vol_6"));
		titles.add(new Title(UNCANNY_X_MEN_ANNUAL, 6, ANNUAL, 2026, ACTIVE, "https://marvel.fandom.com/wiki/Uncanny_X-Men_Annual_Vol_6"));
		
		titles.add(new Title(TitleName.VENOM, 6, ONGOING, 2025, ACTIVE, "https://marvel.fandom.com/wiki/Venom_Vol_6"));
		
		titles.add(new Title(X_MEN, 7, ONGOING, 2024, ACTIVE, "https://marvel.fandom.com/wiki/X-Men_Vol_7"));
		titles.add(new Title(X_MEN_ANNUAL, 6, ANNUAL, 2026, ACTIVE, "https://marvel.fandom.com/wiki/X-Men_Annual_Vol_6"));
		titles.add(new Title(X_MEN_UNITED, 1, ONGOING, 2026, ACTIVE, "https://marvel.fandom.com/wiki/X-Men_United_Vol_1"));
	}

	private void buildActiveIssues() {
		link(ALIAS_RED_BAND, 1, List.of(
				pair("2026-03-11", false), pair("2026-04-15", false), pair("2026-05-20", false)
			)
		);
		link(ASM, 7, List.of(
				pair("2025-04-09", true), pair("2025-04-23", true), pair("2025-05-07", true), pair("2025-05-21", true), pair("2025-06-04", true), //  1- 5
				pair("2025-06-18", true), pair("2025-07-02", true), pair("2025-07-23", true), pair("2025-08-06", true), pair("2025-08-20", true), //  6-10
				pair("2025-09-03", true), pair("2025-09-17", true), pair("2025-10-01", true), pair("2025-10-15", true), pair("2025-11-05", true), // 11-15
				pair("2025-11-19", true), pair("2025-12-10", true), pair("2025-12-24", true), pair("2026-01-07", true), pair("2026-01-21", true), // 16-20
				pair("2026-02-04", true), pair("2026-02-18", true), pair("2026-03-04", true), pair("2026-03-18", false), pair("2026-03-25", false), // 21-25
				pair("2026-04-08", false), pair("2026-04-22", false),pair("2026-05-06", false), pair("2026-05-20", false)
			)
		);
		link(ASM_VENOM_DEATH_SPIRAL, 1, List.of(pair("2026-02-25", true)) );
		link(ASM_VENOM_DEATH_SPIRAL_DEATH_SPIRAL, 1, List.of(pair("2026-05-13", false)) );

		link(CYCLOPS, 4, List.of(
				pair("2026-02-11", false), pair("2026-03-18", false), pair("2026-04-22", false), pair("2026-05-06", false)
			)
		);

		link(MAGIK_AND_COLOSSUS, 1, List.of(
				pair("2026-02-04", false), pair("2026-03-11", false), pair("2026-04-15", false), pair("2026-05-13", false)
			)
		);
		link(MARVEL_KNIGHTS_PUNISHER, 1, List.of(
				pair("2025-10-08", false), pair("2025-11-26", false), pair("2025-12-10", false), pair("2026-03-25", false)
			)
		);
		link(MARVEL_KNIGHTS_THE_WORLD_TO_COME, 1, List.of(
				pair("2025-06-04", true), pair("2025-07-16", true), pair("2025-10-08", true), pair("2025-11-12", true), pair("2025-12-31", true),
				pair("2026-03-25", false)
			)
		);

		link(ROGUE, 4, List.of(
				pair("2026-01-21", true), pair("2026-02-25", true), pair("2026-03-25", false), pair("2026-04-22", false), pair("2026-05-20", false)
			)
		);
		link(STORM_EARTH_S_MIGHTIEST_MUTANT, 1, List.of(
				pair("2026-02-04", false), pair("2026-03-11", false), pair("2026-04-15", false), pair("2026-05-06", false)
			)
		);
		
		link(UNCANNY_X_MEN, 6, List.of(
				pair("2024-08-07", true), pair("2024-09-11", true), pair("2024-09-25", true), pair("2024-10-16", true), pair("2024-11-13", true),
				pair("2024-11-27", true), pair("2024-12-11", true), pair("2025-01-08", true), pair("2025-01-22", true), pair("2025-02-19", true),
				pair("2025-03-05", true), pair("2025-03-26", true), pair("2025-04-09", true), pair("2025-05-14", true), pair("2025-05-28", true),
				pair("2025-06-25", true), pair("2025-07-09", true), pair("2025-07-23", true), pair("2025-08-06", true), pair("2025-09-03", true),
				pair("2025-09-17", true), pair("2026-01-14", true), pair("2026-02-04", true), pair("2026-02-18", true), pair("2026-03-25", false),
				pair("2026-04-08", false), pair("2026-04-29", false), pair("2026-05-13", false)
			)
		);
		link(UNCANNY_X_MEN_ANNUAL, 6, List.of(pair("2026-04-01", false)));
		
		link(TitleName.VENOM, 6, List.of(
				pair("2026-03-11", true), pair("2026-04-01", false), pair("2026-04-15", false)
			), 254
		);
		link(X_MEN, 7, List.of(
				pair("2024-07-10", true), pair("2024-08-14", true), pair("2024-08-28", true), pair("2024-09-18", true), pair("2024-10-02", true),
				pair("2024-10-23", true), pair("2024-11-06", true), pair("2024-12-04", true), pair("2024-12-25", true), pair("2025-01-29", true),
				pair("2025-02-12", true), pair("2025-02-26", true), pair("2025-03-12", true), pair("2025-04-02", true), pair("2025-04-23", true),
				pair("2025-05-07", true), pair("2025-06-04", true), pair("2025-06-18", true), pair("2025-07-02", true), pair("2025-08-13", true),
				pair("2025-08-27", true), pair("2025-01-21", true), pair("2026-01-07", true), pair("2026-01-21", true), pair("2026-02-18", true),
				pair("2026-03-04", true), pair("2026-03-18", false), pair("2026-04-01", false), pair("2026-05-06", false), pair("2026-05-27", false)
			)
		);
		link(X_MEN_ANNUAL, 6, List.of(pair("2026-03-04", true)));
		link(X_MEN_UNITED, 1, List.of(
				pair("2026-03-11", true), pair("2026-04-15", false), pair("2026-05-20", false)
			)
		);
	}
	
	private void buildTPBTitles() {
		tpbTitles.add(new TPBTitle(ALIAS_RED_BAND_VOL_1_TPB, null, getTitle(ALIAS_RED_BAND, 1), ACTIVE));
		
		tpbTitles.add(new TPBTitle(CYCLOPS_VOL_4_TPB, null, getTitle(CYCLOPS, 4), ACTIVE));
		
		tpbTitles.add(new TPBTitle(MAGIK_AND_COLOSSUS_VOL_1_TPB, null, getTitle(MAGIK_AND_COLOSSUS, 1), ACTIVE));
		tpbTitles.add(new TPBTitle(MARVEL_KNIGHTS_PUNISHER_VOL_1_TBP, null, getTitle(MARVEL_KNIGHTS_PUNISHER, 1), ACTIVE));

		tpbTitles.add(new TPBTitle(STORM_EARTH_S_MIGHTIEST_MUTANT_VOL_1_TPB, null, getTitle(STORM_EARTH_S_MIGHTIEST_MUTANT, 1), ACTIVE));
	}
	
	private void buildTradePaperbacks() {
		tpb(getTpbTitle(ALIAS_RED_BAND_VOL_1_TPB), 1, null, "Unknown", "unknown url", false, 1, 3);
		tpb(getTpbTitle(CYCLOPS_VOL_4_TPB), 1, null, "Unknown", "unknown url", false, 1, 3);

		tpb(getTpbTitle(MAGIK_AND_COLOSSUS_VOL_1_TPB), 1, null, "Unknown", "unknown url", false, 1, 4);
		tpb(getTpbTitle(MARVEL_KNIGHTS_PUNISHER_VOL_1_TBP), 1, null, "Unknown", "unknown url", false, 1, 4);
		tpb(getTpbTitle(STORM_EARTH_S_MIGHTIEST_MUTANT_VOL_1_TPB), 1, null, "Unknown", "unknown url", false, 1, 4);
	}

	private void link(TitleName titleName, int volume, List<ImmutablePair<String, Boolean>> pairs) {
		link(titleName, volume, pairs, 0);
	}
	
	private void link(TitleName titleName, int volume, List<ImmutablePair<String, Boolean>> pairs, int offset) {
		Title title = getTitle(titleName, volume);
		for (int index = 0; index < pairs.size(); index++) {
			Issue issue = new Issue(title, index + 1 + offset, pairs.get(index).getLeft(), pairs.get(index).getRight());
			title.add(issue);
			issues.add(issue);
		}
	}

	private void tpb(TPBTitle tpbTitle, int number, String name, String date, String url, boolean owned, int begin, int end) {
		TradePaperback tradePaperback = new TradePaperback(tpbTitle, number, name, date, url, owned);
		getIssues(i -> i.getTitle().equals(tpbTitle.getTitle()) && i.getNumber() >= begin && i.getNumber() <= end).forEach(i -> {
			tradePaperback.add(i);
			i.setTradePaperback(tradePaperback);
		});
		tradePaperbacks.add(tradePaperback);
	}

	public List<TPBTitle> getTpbTitle(Predicate<TPBTitle> predicate) {
		return tpbTitles
				.stream()
				.filter(predicate)
				.toList()
			;
	}
	
	public TPBTitle getTpbTitle(TPBTitleName tpbTitleName) {
		return tpbTitles
				.stream()
				.filter(t -> t.getTpbTitleName() == tpbTitleName)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Impossible de trouver un TPBTitle '" + tpbTitleName.getName() + "'"))
			;
	}
	
	public Title getTitle(TitleName titleName, int volume) {
		return titles
				.stream()
				.filter(t -> t.getTitleName() == titleName && t.getVolume() == volume)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Impossible de trouver un Title '" + titleName.getName() + " Vol " + volume + "'"))
			;
	}
	
	public List<Issue> getIssues(Predicate<Issue> predicate) {
		return issues
				.stream()
				.filter(predicate)
				.sorted()
				.toList();
	}

	public List<Issue> getIssuesFromDate(String from) {
		return getIssues(i -> i.getDate().compareTo(from) >= 0);
	}


	public List<TradePaperback> getTradePaperbacks(Predicate<TradePaperback> predicate) {
		return tradePaperbacks
				.stream()
				.filter(predicate)
				.sorted()
				.toList();
	}

	public List<TradePaperback> getTradePaperbacksFromDate(String from) {
		return getTradePaperbacks(i -> i.getDate().compareTo(from) >= 0);
	}

	private void buildFinishedTitles() {
		titles.add(new Title(ASM, 6, ONGOING, 2022, FINISHED, "https://marvel.fandom.com/wiki/Amazing_Spider-Man_Vol_6"));

		titles.add(new Title(EMMA_FROST_THE_WHITE_QUEEN, 1, LIMITED, 2025, FINISHED, "https://marvel.fandom.com/wiki/Emma_Frost:_The_White_Queen_Vol_1"));
		titles.add(new Title(EXCEPTIONAL_X_MEN, 1, ONGOING, 2024, FINISHED, "https://marvel.fandom.com/wiki/Exceptional_X-Men_Vol_1"));

		titles.add(new Title(IMPERIAL, 1, LIMITED, 2025, FINISHED, "https://marvel.fandom.com/wiki/Imperial_Vol_1"));
		titles.add(new Title(IMPERIAL_WAR_BLACK_PANTHER, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/Imperial_War:_Black_Panther_Vol_1"));
		titles.add(new Title(IMPERIAL_WAR_EXILES, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/Imperial_War:_Exiles_Vol_1"));
		titles.add(new Title(IMPERIAL_WAR_IMPERIAL_GUARDIANS, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/Imperial_War:_Imperial_Guardians_Vol_1"));
		titles.add(new Title(IMPERIAL_WAR_NOVA_CENTURION, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/Imperial_War:_Nova_-_Centurion_Vol_1"));
		titles.add(new Title(IMPERIAL_WAR_PLANET_SHE_HULK, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/Imperial_War:_Planet_She-Hulk_Vol_1"));

		titles.add(new Title(LONGSHOTS, 1, LIMITED, 2025, FINISHED, "https://marvel.fandom.com/wiki/Longshots_Vol_1"));
		
		titles.add(new Title(MAGIK, 2, ONGOING, 2025, FINISHED, "https://marvel.fandom.com/wiki/Magik_Vol_2"));

		titles.add(new Title(NYX, 2, ONGOING, 2024, FINISHED, "https://marvel.fandom.com/wiki/NYX_Vol_2"));

		titles.add(new Title(PHOENIX, 1, ONGOING, 2024, FINISHED, "https://marvel.fandom.com/wiki/Phoenix_Vol_1"));
		titles.add(new Title(PSYLOCKE, 2, ONGOING, 2025, FINISHED, "https://marvel.fandom.com/wiki/Psylocke_Vol_2"));
		
		titles.add(new Title(ROGUE_STORM, 1, LIMITED, 2025, FINISHED, "https://marvel.fandom.com/wiki/Rogue_Storm_Vol_1"));

		titles.add(new Title(STORM, 5, ONGOING, 2024, FINISHED, "https://marvel.fandom.com/wiki/Storm_Vol_5"));
		titles.add(new Title(STORM_LIFEDREAM, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/Storm:_Lifedream_Vol_1"));

		titles.add(new Title(X_FACTOR, 5, ONGOING, 2024, FINISHED, "https://marvel.fandom.com/wiki/X-Factor_Vol_5"));
		titles.add(new Title(X_FORCE, 7, ONGOING, 2024, FINISHED, "https://marvel.fandom.com/wiki/X-Force_Vol_7"));
		titles.add(new Title(X_MANHUNT_OMEGA, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/X-Manhunt_Omega_Vol_1"));
		titles.add(new Title(X_MEN_AGE_OF_REVELATION, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/X-Men:_Age_of_Revelation_Vol_1"));
		titles.add(new Title(X_MEN_AGE_OF_REVELATION_FINALE, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/X-Men:_Age_of_Revelation_Finale_Vol_1"));
		titles.add(new Title(X_MEN_AGE_OF_REVELATION_OVERTURE, 1, ONE_SHOT, 2026, FINISHED, "https://marvel.fandom.com/wiki/X-Men:_Age_of_Revelation_Overture_Vol_1"));
		titles.add(new Title(X_MEN_HELLFIRE_VIGIL, 1, ONE_SHOT, 2025, FINISHED, "https://marvel.fandom.com/wiki/X-Men:_Hellfire_Vigil_Vol_1"));
	}

	private void buildFinishedIssues() {
		link(ASM, 6, List.of(
				pair("2024-11-13", true), pair("2024-11-27", true), pair("2024-12-11", true), pair("2024-12-25", true), pair("2025-01-08", true),
				pair("2025-01-22", true), pair("2025-02-12", true), pair("2025-02-19", true), pair("2025-03-12", true), pair("2025-03-26", true)
			), 60
		);
		link(AVENGERS, 9, List.of(
				pair("2024-08-07", true), pair("2024-09-18", true), pair("2024-10-23", true), pair("2025-11-06", true), pair("2025-12-04", true),
				pair("2025-01-01", true), pair("2025-02-05", true), pair("2025-03-19", true), pair("2025-04-23", true), pair("2025-05-28", true),
				pair("2025-06-18", true), pair("2025-07-02", true), pair("2025-08-27", true), pair("2025-09-03", true), pair("2025-10-08", true),
				pair("2025-11-05", true), pair("2025-12-17", true), pair("2026-01-14", true), pair("2026-02-04", true), pair("2026-03-04", true)
			), 16
		);

		link(EMMA_FROST_THE_WHITE_QUEEN, 1, List.of(
				pair("2025-06-18", true), pair("2025-07-23", true), pair("2025-08-27", true), pair("2025-09-24", true), pair("2025-10-29", true)
			)
		);
		link(EXCEPTIONAL_X_MEN, 1, List.of(
				pair("2024-09-04", true), pair("2024-10-09", true), pair("2024-11-20", true), pair("2024-12-25", true), pair("2025-01-15", true),
				pair("2025-02-19", true), pair("2025-03-19", true), pair("2025-04-16", true), pair("2025-05-21", true), pair("2025-06-11", true),
				pair("2025-07-16", true), pair("2025-08-20", true), pair("2025-09-10", true)
			)
		);

		link(IMPERIAL, 1, List.of(
				pair("2025-06-04", true), pair("2025-07-16", true), pair("2025-08-20", true), pair("2025-10-29", true)
			)
		);
		link(IMPERIAL_WAR_BLACK_PANTHER, 1, List.of( pair("2025-08-27", true) ));
		link(IMPERIAL_WAR_EXILES, 1, List.of( pair("2025-09-03", true) ));
		link(IMPERIAL_WAR_IMPERIAL_GUARDIANS, 1, List.of( pair("2025-10-08", true) ));
		link(IMPERIAL_WAR_NOVA_CENTURION, 1, List.of( pair("2025-09-10", true) ));
		link(IMPERIAL_WAR_PLANET_SHE_HULK, 1, List.of( pair("2025-08-27", true) ));

		link(LONGSHOTS, 1, List.of(
				pair("2025-10-08", true), pair("2025-11-05", true), pair("2025-12-10", true)
			)
		);

		link(MAGIK, 2, List.of(
				pair("2025-01-08", true), pair("2025-02-12", true), pair("2025-03-19", true), pair("2025-04-23", true), pair("2025-05-28", true),
				pair("2025-06-11", true), pair("2025-07-09", true), pair("2025-08-13", true), pair("2025-09-03", true), pair("2025-09-17", true)
			)
		);

		link(NYX, 2, List.of( pair("2025-03-05", true) ), 8);

		link(PHOENIX, 1, List.of(
				pair("2024-07-17", false), pair("2024-08-21", false), pair("2024-09-25", false), pair("2024-10-09", false), pair("2024-11-20", false),
				pair("2024-12-11", false), pair("2025-01-22", false), pair("2025-02-05", false), pair("2025-03-12", false), pair("2025-04-09", false),
				pair("2025-05-14", false), pair("2025-06-11", false), pair("2025-07-23", false), pair("2025-08-27", false), pair("2025-09-17", false)
			)
		);
		link(PSYLOCKE, 2, List.of(
				pair("2024-11-13", false), pair("2024-12-18", false), pair("2025-01-29", false), pair("2025-02-12", false), pair("2025-03-19", false),
				pair("2025-04-02", false), pair("2025-05-07", false), pair("2025-06-18", false), pair("2025-07-23", false), pair("2025-08-20", false)
			)
		);

		link(ROGUE_STORM, 1, List.of(
				pair("2025-10-15", true), pair("2025-11-12", true), pair("2025-12-17", true)
			)
		);

		link(STORM, 5, List.of(
				pair("2024-10-02", true), pair("2024-11-20", true), pair("2024-12-11", true), pair("2025-01-15", true), pair("2025-02-19", true),
				pair("2025-03-05", true), pair("2025-04-09", true), pair("2025-05-07", true), pair("2025-06-04", true), pair("2025-07-23", true),
				pair("2025-08-06", true), pair("2025-09-24", true)
			)
		);

		link(X_FACTOR, 5, List.of( pair("2025-03-12", true) ), 7);
		link(X_FORCE, 7, List.of(
				pair("2024-07-31", false), pair("2024-08-28", false), pair("2024-09-25", false), pair("2024-10-09", false), pair("2024-11-06", false),
				pair("2024-12-04", true), pair("2025-01-01", true), pair("2025-02-19", true), pair("2025-03-19", true), pair("2025-04-23", true)
			)
		);
		link(X_MANHUNT_OMEGA, 1, List.of( pair("2025-03-26", true)) );
		link(X_MEN_AGE_OF_REVELATION, 1, List.of( pair("2025-07-16", true)), -1 );
		link(X_MEN_AGE_OF_REVELATION_FINALE, 1, List.of( pair("2025-12-31", true)) );
		link(X_MEN_AGE_OF_REVELATION_OVERTURE, 1, List.of( pair("2025-10-01", true)) );
		link(X_MEN_HELLFIRE_VIGIL, 1, List.of( pair("2025-07-02", true)) );
	}

	private void buildFinishedTPBTitles() {
		tpbTitles.add(new TPBTitle(PHOENIX_VOL_1_TPB, "https://marvel.fandom.com/wiki/Phoenix_TPB_Vol_1", getTitle(PHOENIX, 1), FINISHED));
		tpbTitles.add(new TPBTitle(PSYLOCKE_VOL_2_TPB, "https://marvel.fandom.com/wiki/Psylocke_TPB_Vol_1", getTitle(PSYLOCKE, 2), FINISHED));

		tpbTitles.add(new TPBTitle(X_FORCE_VOL_7_TPB, "https://marvel.fandom.com/wiki/X-Force_by_Geoffrey_Thorne_TPB_Vol_1", getTitle(X_FORCE, 7), FINISHED));
	}
	

	private void buildFinishedTradePaperbacks() {
		tpb(getTpbTitle(PHOENIX_VOL_1_TPB), 1, "2025-03-18", "Orbital Resonance", "https://marvel.fandom.com/wiki/Phoenix_TPB_Vol_1_1:_Orbital_Resonance", true, 1, 5);
		tpb(getTpbTitle(PHOENIX_VOL_1_TPB), 2, "2025-08-12", "Cosmic Ascent", "https://marvel.fandom.com/wiki/Phoenix_TPB_Vol_1_2:_Cosmic_Ascent", true, 6, 10);
		tpb(getTpbTitle(PHOENIX_VOL_1_TPB), 3, "2026-01-27", "Astral Bond", "https://marvel.fandom.com/wiki/Phoenix_TPB_Vol_1_3:_Astral_Bond", true, 11, 15);

		tpb(getTpbTitle(PSYLOCKE_VOL_2_TPB), 1, "2025-07-08", "Guardian", "https://marvel.fandom.com/wiki/Psylocke_TPB_Vol_1_1:_Guardian", true, 1, 5);
		tpb(getTpbTitle(PSYLOCKE_VOL_2_TPB), 2, "2025-12-16", "Nightmares of the Past", "https://marvel.fandom.com/wiki/Psylocke_TPB_Vol_1_2:_Nightmares_of_the_Past", true, 6, 10);
		
		tpb(getTpbTitle(X_FORCE_VOL_7_TPB), 1, "2025-03-25", "Fractures", "https://marvel.fandom.com/wiki/X-Force_by_Geoffrey_Thorne_TPB_Vol_1_1:_Fractures", true, 1, 5);
	}
}
