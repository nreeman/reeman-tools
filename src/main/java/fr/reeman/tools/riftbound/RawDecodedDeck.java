package fr.reeman.tools.riftbound;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RawDecodedDeck {
	private final Map<RawCardId, Integer> main;
	private final Map<RawCardId, Integer> side;
	
	public static RawDecodedDeck noVariantCopy(final RawDecodedDeck deck) {
		return new RawDecodedDeck(noVariantCopy(deck.main), noVariantCopy(deck.side));
	}


	private static Map<RawCardId, Integer> noVariantCopy(final Map<RawCardId, Integer> map) {
		Map<RawCardId, Integer> copy = new HashMap<>();
		for (Entry<RawCardId, Integer> entry : map.entrySet()) {
			RawCardId newCard = new RawCardId(entry.getKey().getSet(), entry.getKey().getId(), 0);
			Integer newQty = copy.computeIfAbsent(newCard, c -> 0) + entry.getValue();
			copy.put(newCard, newQty);
		}
		return copy;
	}
	
	public RawDecodedDeck noVariantCopy() {
		return noVariantCopy(this);
	}
}
