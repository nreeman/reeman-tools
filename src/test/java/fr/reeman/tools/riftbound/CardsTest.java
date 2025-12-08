package fr.reeman.tools.riftbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.reeman.tools.riftbound.id.OwensCardIdConverter;
import fr.reeman.tools.riftbound.owen.Card;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardsTest {
	
	public record NameAndType(String name, String type) {};
	
	private static Map<RawCardId, NameAndType> ALL_CARDS_NO_VARIANT = null;

	public static synchronized  Map<RawCardId, NameAndType> allCardsNoVariant() {
		if (ALL_CARDS_NO_VARIANT == null) {
			try (InputStream inputStream = Test.class.getResourceAsStream("/owen_s_cards_list_202511.json")) {
				Card[] cards = new ObjectMapper().readValue(inputStream, Card[].class);
				OwensCardIdConverter converter = new OwensCardIdConverter();
				ALL_CARDS_NO_VARIANT = new HashMap<>();
				for (Card card : cards) {
					ALL_CARDS_NO_VARIANT.put(converter.to(card.getId()), new NameAndType(card.getName(), card.getCardType().getFirst().getId()));
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return ALL_CARDS_NO_VARIANT;
	}
	
	@Test
	public void test() {
		assertEquals(350, CardsTest.allCardsNoVariant().size());
		assertEquals("Annie, Fiery", CardsTest.allCardsNoVariant().get(new RawCardId(1, 1, 0)).name);
	}
}
