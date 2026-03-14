package fr.reeman.tools.riftbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
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

	@Test
	public void pretty() throws FileNotFoundException, IOException {
		int p = 4;
		List<String> tokens = List.of("40", "41", "42", "43", "44", "45", "46", "47", "48", "49");
		for (String token : tokens) {
			System.out.println(token);
			String fileName = "/users/nreeman/Downloads/tmp" + token + ".txt";
			int startIndex = "data:image/jpeg;base64,".length();
			String fileNameFormat = "/users/nreeman/Downloads/pretty" + p + "/pretty_" + token + "_%03d.jpg";
			Decoder decoder = Base64.getDecoder();
			try (FileReader fileReader = new FileReader(fileName);
					BufferedReader reader = new BufferedReader(fileReader)) {
				
				String line = reader.readLine();
				int count = 0;
				while (line != null) {
					count++;
					byte[] content = decoder.decode(line.substring(startIndex));
					try (FileOutputStream fileOutputStream = new FileOutputStream(String.format(fileNameFormat, count))) {
						//System.out.println(String.format(fileNameFormat, count));
						fileOutputStream.write(content);
					}
					line = reader.readLine();
				}
			}
		}
	}
}
