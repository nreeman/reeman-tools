package fr.reeman.tools.riftbound;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lombok.Data;

@Data
public class MinimalDeck {
	private static final Pattern VARIANT_PATTERN = Pattern.compile(".+(a|s|b)");

	private final Map<String, Integer> main;
	private final Map<String, Integer> side;
	
	public static MinimalDeck noVariantCopy(MinimalDeck minimalDeck) {
		return new MinimalDeck(noVariantCopy(minimalDeck.main), noVariantCopy(minimalDeck.side));
	}

	private static Map<String, Integer> noVariantCopy(Map<String, Integer> map) {
		Map<String, Integer> result = new HashMap<>(map);
		for (String code : map.keySet()) {
			if (VARIANT_PATTERN.matcher(code).matches()) {
				Integer qty = result.get(code);
				if (qty != null) {
					String newCode = code.substring(0, code.length() - 1);
					int newQty = result.computeIfAbsent(newCode, c -> 0) + qty;
					result.put(newCode, newQty);
					result.remove(code);
				}
			}
		}
		return result;
	}
}
