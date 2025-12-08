package fr.reeman.tools.riftbound;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RawDeck {
	private final Map<RawCardId, Integer> main;
	private final Map<RawCardId, Integer> side;
	
	public static RawDeck noVariantCopy(final RawDeck deck) {
		return new RawDeck(noVariantCopy(deck.main), noVariantCopy(deck.side));
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
	
	public RawDeck noVariantCopy() {
		return noVariantCopy(this);
	}
	
	/**
	 *  
	 */
	//TODO
	public void printTree(PrintStream out) {
		Map<Integer, List<RawCardId>> invertedMap = invertMap(main);
	}
	
	//TODO une méthode générique
	private Map<Integer, List<RawCardId>> invertMap(Map<RawCardId, Integer> map) {
		Map<Integer, List<RawCardId>> invertedMap = new HashMap<>();
		for (Entry<RawCardId, Integer> entry : map.entrySet()) {
			List<RawCardId> rawCardIds = invertedMap.get(entry.getValue());
			if (rawCardIds == null) {
				rawCardIds = new ArrayList<RawCardId>();
				invertedMap.put(entry.getValue(), rawCardIds);
			}
			rawCardIds.add(entry.getKey());
		}
		return invertedMap;
	}
}
