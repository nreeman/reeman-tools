package fr.reeman.tools.comics.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TitleName {

	ALIAS_RED_BAND("Alias: Red Band"),
	ASM("Amazing Spider-Man"),
	ASM_VENOM_DEATH_SPIRAL("Amazing Spider-Man/Venom: Death Spiral"),
	ASM_VENOM_DEATH_SPIRAL_DEATH_SPIRAL("Amazing Spider-Man/Venom: Death Spiral Body Count"),
	AVENGERS("Avengers"),
	
	CYCLOPS("Cyclops"),

	EMMA_FROST_THE_WHITE_QUEEN("Emma Frost: The White Queen"),
	EXCEPTIONAL_X_MEN("Exceptional X-Men"),

	IMPERIAL("Imperial"),
	IMPERIAL_WAR_BLACK_PANTHER("Imperial War: Black Panther"),
	IMPERIAL_WAR_EXILES("Imperial War: Exiles"),
	IMPERIAL_WAR_IMPERIAL_GUARDIANS("Imperial War: Imperial Guardians"),
	IMPERIAL_WAR_NOVA_CENTURION("Imperial War: Nova - Centurion"),
	IMPERIAL_WAR_PLANET_SHE_HULK("Imperial War: Planet She-Hulk"),

	LONGSHOTS("Longshots"),
	
	MAGIK("Magik"),
	MAGIK_AND_COLOSSUS("Magik & Colossus"),
	MARVEL_KNIGHTS_PUNISHER("Marvel Knights: Punisher"),
	MARVEL_KNIGHTS_THE_WORLD_TO_COME("Marvel Knights: The World To Come"),

	NYX("NYX"),
	
	PHOENIX("Phoenix"),
	PSYLOCKE("Psylocke"),
	
	ROGUE("Rogue"),
	ROGUE_STORM("Rogue Storm"),
	
	STORM("Storm"),
	STORM_EARTH_S_MIGHTIEST_MUTANT("Storm: Earth's Mightiest Mutant"),
	STORM_LIFEDREAM("Storm: Lifedream"),

	VENOM("Venom"),
	
	UNCANNY_X_MEN("Uncanny X-Men"),
	UNCANNY_X_MEN_ANNUAL("Uncanny X-Men Annual"),

	X_FACTOR("X-Factor"),
	X_FORCE("X-Force"),
	X_MANHUNT_OMEGA("X-Manhunt Omega"),
	X_MEN("X-Men"),
	X_MEN_AGE_OF_REVELATION("X-Men: Age of Revelation"),
	X_MEN_AGE_OF_REVELATION_FINALE("X-Men: Age of Revelation Finale"),
	X_MEN_AGE_OF_REVELATION_OVERTURE("X-Men: Age of Revelation Overture"),
	X_MEN_ANNUAL("X-Men Annual"),
	X_MEN_HELLFIRE_VIGIL("X-Men: Hellfire Vigil"),
	X_MEN_UNITED("X-Men United")

	;
	
	private final String name;
	
	@Override
	public String toString() {
		return name;
	}
	
}
