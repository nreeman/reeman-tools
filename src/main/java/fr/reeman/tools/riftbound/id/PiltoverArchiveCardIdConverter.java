package fr.reeman.tools.riftbound.id;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.reeman.tools.riftbound.RawCardId;

public class PiltoverArchiveCardIdConverter implements ToRawCardId<String>, FromRawCardId<String> {

	private static final List<String> IDENTIFIERS_SET = List.of("OGN", "OGS", "ARC", "SFD");
	private static final List<String> IDENTIFIERS_VARIANT = List.of("", "a", "s", "b");
	
	private static Pattern PATTERN_CARD_ID = Pattern.compile("(" + String.join("|", IDENTIFIERS_SET) +")-(\\d{3})(" + String.join("|", IDENTIFIERS_VARIANT.subList(1, IDENTIFIERS_VARIANT.size())) + "?)");

	@Override
	public RawCardId to(String id) {
		Matcher matcher = PATTERN_CARD_ID.matcher(id);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("The id '" + id + "' does not match the pattern " + PATTERN_CARD_ID.pattern());
		}
		
		return new RawCardId(IDENTIFIERS_SET.indexOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), matcher.groupCount() == 2 ? 0 : IDENTIFIERS_VARIANT.indexOf(matcher.group(3)));
	}

	@Override
	public String from(RawCardId rawCardId) {
		return IDENTIFIERS_SET.get(rawCardId.getSet()) + "-" + String.format("%03d", rawCardId.getId()) + IDENTIFIERS_VARIANT.get(rawCardId.getVariant());
	}

}
