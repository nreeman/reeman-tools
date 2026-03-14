package fr.reeman.tools.comics.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TitleType {

	ANNUAL("Annual"),
	LIMITED("Limited"),
	ONGOING("Ongoing"),
	ONE_SHOT("One-Shot")
	;
	
	private final String label;
}
