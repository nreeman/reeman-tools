package fr.reeman.tools.mtg.top8;

import fr.reeman.tools.qtyitm.section.Section;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Top8Section implements Section {

	LANDS("LANDS"),
	CREATURES("CREATURES"),
	OTHER_SPELLS("OTHER SPELLS"),
	SIDEBOARDS("SIDEBOARDS")
	;
	
	@Getter
	private final String label;
}
