package fr.reeman.tools.riftbound.id;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.reeman.tools.riftbound.RawCardId;

public class OwensCardIdConverter implements ToRawCardId<String>, FromRawCardId<String> {

	private static final List<String> IDENTIFIERS_SET = List.of("ogn", "ogs", "arc", "sfd");
	private static final List<String> UNNECESSARY_NUMBER_OF_CARDS_IN_SET = List.of("298", "024", "ARC", "221");

	private static Pattern PATTERN_CARD_ID = Pattern.compile("(" + String.join("|", IDENTIFIERS_SET) +")-(\\d{3})(a|s|b|-star)?-(" + String.join("|", UNNECESSARY_NUMBER_OF_CARDS_IN_SET) + ")");

	@Override
	public RawCardId to(String id) {
		Matcher matcher = PATTERN_CARD_ID.matcher(id);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("The id '" + id + "' does not match the pattern " + PATTERN_CARD_ID.pattern());
		}
		
		return new RawCardId(IDENTIFIERS_SET.indexOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), 0);
	}

	@Override
	public String from(RawCardId rawCardId) {
		return IDENTIFIERS_SET.get(rawCardId.getSet()) + "-" + String.format("%03d", rawCardId.getId()) + "-" + UNNECESSARY_NUMBER_OF_CARDS_IN_SET.get(rawCardId.getSet());
	}
}
