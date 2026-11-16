package fr.reeman.tools.riftbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
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
		String p = "18";
		String root = "/users/nreeman/Downloads/";
		String target = root + "pretty" + p + "/";
		int startIndex = "data:image/jpeg;base64,".length();

		new File(target).mkdirs();
		for (int i = 0; i < 8; i++) {
			String current = p+i;
			System.out.println(current);
			String fileName = root + "tmp" + current + ".txt";
			String fileNameFormat = target + current + "_%03d.jpg";
			Decoder decoder = Base64.getDecoder();
			try (FileReader fileReader = new FileReader(fileName);
					BufferedReader reader = new BufferedReader(fileReader)) {
				
				String line = reader.readLine();
				int count = 0;
				while (line != null) {
					count++;
//if (i == 2) {
//	System.out.println(count + " - " + line);
//}
					byte[] content = decoder.decode(line.substring(startIndex));
					try (FileOutputStream fileOutputStream = new FileOutputStream(String.format(fileNameFormat, count))) {
						//System.out.println(String.format(fileNameFormat, count));
						fileOutputStream.write(content);
					}
					line = reader.readLine();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				throw e;
			}
		}
	}
}
