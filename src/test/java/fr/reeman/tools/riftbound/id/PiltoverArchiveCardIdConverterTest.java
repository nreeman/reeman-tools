package fr.reeman.tools.riftbound.id;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.reeman.tools.riftbound.RawCardId;

public class PiltoverArchiveCardIdConverterTest {

	@Test
	public void testUid() {
		PiltoverArchiveCardIdConverter converter = new PiltoverArchiveCardIdConverter();
		assertEquals("OGN-104a", converter.from(new RawCardId(0, 104, 1)));
		assertEquals("OGS-055s", converter.from(new RawCardId(1, 55, 2)));
		assertEquals("SFD-001", converter.from(new RawCardId(3, 1, 0)));
		assertEquals("SFD-001b", converter.from(new RawCardId(3, 1, 3)));
	}

	@Test
	public void testRaw() {
		PiltoverArchiveCardIdConverter converter = new PiltoverArchiveCardIdConverter();
		assertEquals(new RawCardId(0, 104, 1), converter.to("OGN-104a"));
		assertEquals(new RawCardId(1, 55, 2), converter.to("OGS-055s"));
		assertEquals(new RawCardId(3, 1, 0), converter.to("SFD-001"));
		assertEquals(new RawCardId(3, 1, 3), converter.to("SFD-001b"));
	}
}
