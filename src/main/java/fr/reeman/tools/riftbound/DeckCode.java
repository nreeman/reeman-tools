package fr.reeman.tools.riftbound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.reeman.tools.bits.Base32;
import fr.reeman.tools.bits.Bits;
import fr.reeman.tools.bits.VarInt;
import fr.reeman.tools.qtyitm.QuantityItemSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeckCode {
	
	public static final byte[] SUPPORTED_VERSIONS = new byte[] { 0x11, 0x12 };
	public static final byte LAST_VERSION = SUPPORTED_VERSIONS[SUPPORTED_VERSIONS.length - 1];
	
	private static final String[] IDENTIFIERS_SET = new String[] { "OGN", "OGS", "ARC", "SFD" };
	private static final String[] IDENTIFIERS_VARIANT = new String[] { "", "a", "s", "b" };

	public static MinimalDeck decode(String code) {
		byte[] bytes = Base32.decode(code);
//System.out.println("Version : " + Bits.hex(bytes[0]));
		checkVersion(bytes[0]);

		Map<String, Integer> main;
		Map<String, Integer> side;
		if (bytes[0] == 0x11) {
			String[] codes = code.split("\\|");
//System.out.println("decode[0](\"" + codes[0] + "\")");
			main = decode(new ByteArrayInputStream(Base32.decode(codes[0])), 12, true);
			if (codes.length > 1) {
//System.out.println("decode[1](\"" + codes[1] + "\")");
				side = decode(new ByteArrayInputStream(Base32.decode(codes[1])), 12, true);
			} else {
				side = new HashMap<>();
			}
		} else {
//System.out.println("decode(\"" + code + "\")");
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			main = decode(inputStream, 12, true);
			side = decode(inputStream, 3, false);
		}

		return new MinimalDeck(main, side);
	}

	private static Map<String, Integer> decode(ByteArrayInputStream inputStream, int startingQuantityValue, boolean hasVersion) {
		if (hasVersion) {
			int ignoredVersion = VarInt.read(inputStream).intValue();
//System.out.println("Ignored version : " + ignoredVersion);
		}
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int qty = startingQuantityValue; qty >= 1; qty--) {
			int nbOfSetVariantCombinations = VarInt.read(inputStream).intValue();
//System.out.println("qty:" + qty + " -> " + nbOfSetVariantCombinations + " set / variant combination" + (nbOfSetVariantCombinations > 1 ? "s" : ""));
			for (int i = 0; i < nbOfSetVariantCombinations; i++) {
				decodeSetVariantCombination(inputStream, map, qty);
			}
		}

		return map;
	}

	private static void decodeSetVariantCombination(ByteArrayInputStream inputStream, Map<String, Integer> map, int quantity) {
		int nbOfCards = VarInt.read(inputStream).intValue();
		int set = (int)VarInt.read(inputStream).byteValue();
		int variant = (int)VarInt.read(inputStream).byteValue();
//System.out.println(" - " + set + " / " + variant + " : " + nbOfCards);
//List<String> codes = new ArrayList<String>();
		for (int i = 0; i < nbOfCards; i++) {
			String cardCode = IDENTIFIERS_SET[set] + "-" + VarInt.read(inputStream).intValue() + IDENTIFIERS_VARIANT[variant];
			map.put(cardCode, quantity);
//codes.add(cardCode);
		}
//System.out.println(" - codes = " + String.join(", ", codes));
	}

	private static void checkVersion(byte version) {
		for (byte sv : SUPPORTED_VERSIONS) {
			if (sv == version) {
				return;
			}
		}
		
		throw new RuntimeException("Unsupported version : " + Bits.hex(version));
	}

	public static String encode(QuantityItemSet deck) {
		throw new UnsupportedOperationException();
	}
	
}
