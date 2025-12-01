package fr.reeman.tools.riftbound;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class DeckCodeTest {

	@Test
	public void decode() {
		decode("CEAAAAAAAAAQCAAB2YAQAAIBAAAVSAABA4AAAYDK2EA5GAOVAHMADXQBAEDQAACTKVPWS5HJAHXQCAQIAAAABXIB4AA6UAPVAGRAFJICUYBACAAB6YAQ", 0);
		decode("CEAAAAAAAAAQCAAB2YAQAAIBAAAVSAABA4AAAYDK2EA5GAOVAHMADXQBAEDQAACTKVPWS5HJAHXQCAQIAAAABXIB4AA6UAPVAGRAFJICUYBACAAB6YAQ|CEAAAAAAAAAAAAAAAEAQAAPOAEAQCAAA4AAQCAYAABU52APHAE", 8);
		decode("CIAAAAAAAAAQCAAAA4AACAIAABMQAAILAAAAICIMDMOVOX3AM5UHIAIDAAACO6XYAEAQKAAABX3QDGACUABKIAQAAEBQAAAWDBOQCAQAABMHE", 8);
		decode("CIAAAAAAAAAQCAABFIAACAIAAHLACAACAQAAAKZN2UA5QAIIAMACCIRTHCNQDIIBVQA3EAIBAEAAB2IBAIBAAAEZAKUQEBADAAWTFRIB3QAQAAAA", 0);
	}
	
	private void decode(String code, int sideSize) {
		MinimalDeck deck = DeckCode.decode(code);
		deck = MinimalDeck.noVariantCopy(deck);
//		println(deck);
		assertEquals(56, sizeOf(deck.getMain()));
		assertEquals(sideSize, sizeOf(deck.getSide()));
//		System.out.println();
	}
	
	private void println(MinimalDeck deck) {
		System.out.println("Main = " + toString(deck.getMain()) + "; Side = " + toString(deck.getSide()));
		
	}

	private String toString(Map<String, Integer> map) {
		return String.join(", ", map.keySet().stream().map(k -> k + ":" + map.get(k)).toList());
	}

	private int sizeOf(Map<String, Integer> map) {
		return map.values().stream().reduce(0, Integer::sum).intValue();
	}
}
