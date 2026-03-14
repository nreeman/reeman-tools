package fr.reeman.tools.comics.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TPBTitleName {
	
	ALIAS_RED_BAND_VOL_1_TPB("Alias: Red Band TPB"),
	
	CYCLOPS_VOL_4_TPB("Cyclops TPB"),

	MAGIK_AND_COLOSSUS_VOL_1_TPB("Magik & Colossus TPB"),
	MARVEL_KNIGHTS_PUNISHER_VOL_1_TBP("Marvel Knights: Punisher TBP"),
	
	PHOENIX_VOL_1_TPB("Phoenix TBP"),
	PSYLOCKE_VOL_2_TPB("Psylocke TPB"),

	STORM_EARTH_S_MIGHTIEST_MUTANT_VOL_1_TPB("Storm: Earth's Mightiest Mutant TPB"),

	X_FORCE_VOL_7_TPB("X-Force by Geoffrey Thorne TPB")

	;

	private final String name;

	@Override
	public String toString() {
		return name;
	}
}
