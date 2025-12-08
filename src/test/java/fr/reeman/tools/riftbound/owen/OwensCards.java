package fr.reeman.tools.riftbound.owen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OwensCards {

	@Test
	public void read() throws IOException {
		try (InputStream inputStream = Test.class.getResourceAsStream("/owen_s_cards_list_202511.json")) {
			Card[] cards = new ObjectMapper().readValue(inputStream, Card[].class);
			assertEquals(394, cards.length);
		}
	}
}
