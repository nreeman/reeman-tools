package fr.reeman.tools.riftbound.id;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.reeman.tools.riftbound.RawCardId;

public class OwenCardIdConverterTest {
	@Test
	public void testUid() {
		OwensCardIdConverter converter = new OwensCardIdConverter();
		assertEquals("ogn-104-298", converter.from(new RawCardId(0, 104, 1)));
		assertEquals("ogs-055-024", converter.from(new RawCardId(1, 55, 2)));
		assertEquals("sfd-001-221", converter.from(new RawCardId(3, 1, 0)));
	}

	@Test
	public void testRaw() {
		OwensCardIdConverter converter = new OwensCardIdConverter();
		assertEquals(new RawCardId(0, 104, 0), converter.to("ogn-104-298"));
		assertEquals(new RawCardId(1, 55, 0), converter.to("ogs-055-024"));
		assertEquals(new RawCardId(3, 1, 0), converter.to("sfd-001-221"));
	}
}
