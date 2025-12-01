package fr.reeman.tools.riftbound;

import java.util.Map;

import lombok.Data;

@Data
public class RawDecodedDeck {
	private final Map<RawDecodedCard, Integer> main;
	private final Map<RawDecodedCard, Integer> side;
}
