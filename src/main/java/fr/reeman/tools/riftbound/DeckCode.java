package fr.reeman.tools.riftbound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.reeman.tools.bits.Base32;
import fr.reeman.tools.bits.Bits;
import fr.reeman.tools.bits.VarInt;
import fr.reeman.tools.bits.VarIntInputStream;
import fr.reeman.tools.qtyitm.QuantityItemSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeckCode {
	
	public static final int[] SUPPORTED_VERSIONS = new int[] { 0x11, 0x12 };
	public static final int LAST_VERSION = SUPPORTED_VERSIONS[SUPPORTED_VERSIONS.length - 1];
	private static final byte[] LAST_VERSION_AS_VARINT_BYTES_ARRAY = new VarInt(LAST_VERSION).getBytes();

	private record SetVariant(int set, int variant) implements Comparable<SetVariant>{
		@Override
		public int compareTo(SetVariant other) {
			int c = Integer.compare(this.set, other.set);
			return c != 0 ? c : Integer.compare(this.variant, other.variant);
		}
	};
	
	public static RawDecodedDeck decode(String code) {
		try (VarIntInputStream inputStream = new VarIntInputStream(Base32.decode(code))) {
			int version = inputStream.read().intValue();
			checkVersion(version);

//System.out.println(Bits.hex((byte)version) + " ");
			Map<RawCardId, Integer> main;
			Map<RawCardId, Integer> side;
			if (version == 0x11) {
				String[] codes = code.split("\\|");
				main = decode(inputStream, 12);
				if (codes.length > 1) {
//System.out.println("side:");
					try (VarIntInputStream sideInputStream = new VarIntInputStream(Base32.decode(codes[1]))) {
						sideInputStream.read(); // useless version
//System.out.print(sideInputStream.read().intValue() + " ");
						side = decode(sideInputStream, 12);
					}
				} else {
					side = new HashMap<>();
				}
			} else {
				main = decode(inputStream, 12);
//System.out.println("side:");
				side = decode(inputStream, 3);
			}

//System.out.println();
			return new RawDecodedDeck(main, side);
		} catch (IOException e) {
			// Not supposed to happen since close() does nothing
			e.printStackTrace();
			return null;
		}
	}

	private static Map<RawCardId, Integer> decode(VarIntInputStream inputStream, int startingQuantityValue) {
		Map<RawCardId, Integer> map = new HashMap<>();
		for (int qty = startingQuantityValue; qty >= 1; qty--) {
			VarInt varInt = inputStream.read();
			if (varInt == null) {
				break;
			}
			int nbOfSetVariantCombinations = varInt.intValue();
//System.out.println(" |- {" + (qty >= 10 ? "" : "0") + qty + "}:" + nbOfSetVariantCombinations);
			for (int i = 0; i < nbOfSetVariantCombinations; i++) {
				decodeSetVariantCombination(inputStream, map, qty);
			}
		}

		return map;
	}

	private static void decodeSetVariantCombination(VarIntInputStream inputStream, Map<RawCardId, Integer> map, int quantity) {
		int nbOfCards = inputStream.read().intValue();
		int set = inputStream.read().intValue();
		int variant = inputStream.read().intValue();
//System.out.print(" |  |- [" + nbOfCards + " "  + set + " " + variant + "]=>( ");
		for (int i = 0; i < nbOfCards; i++) {
//int id = inputStream.read().intValue();
//System.out.print(id + " ");
//map.put(new RawCardId(set, id, variant), quantity);
			map.put(new RawCardId(set, inputStream.read().intValue(), variant), quantity);
		}
//System.out.println(")");
	}

	private static void checkVersion(int version) {
		for (int supportedVersion : SUPPORTED_VERSIONS) {
			if (supportedVersion == version) {
				return;
			}
		}
		throw new RuntimeException("Unsupported version : " + Bits.hex(version));
	}

	public static String encode(RawDecodedDeck rawDecodedDeck) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			outputStream.write(LAST_VERSION_AS_VARINT_BYTES_ARRAY);
			encode(rawDecodedDeck.getMain(), 12, outputStream);
			encode(rawDecodedDeck.getSide(), 3, outputStream);
			
			return Base32.encode(outputStream.toByteArray());
		} catch (IOException e) {
			// Not supposed to happen since close() does nothing
			e.printStackTrace();
			return null;
		}
	}

	private static void encode(Map<RawCardId, Integer> map, int startingPoint, ByteArrayOutputStream outputStream) throws IOException {
		Map<Integer, List<RawCardId>> invertedMap = new HashMap<>();
		for (Entry<RawCardId, Integer> entry : map.entrySet()) {
			List<RawCardId> rawCardIds = invertedMap.get(entry.getValue());
			if (rawCardIds == null) {
				rawCardIds = new ArrayList<RawCardId>();
				invertedMap.put(entry.getValue(), rawCardIds);
			}
			rawCardIds.add(entry.getKey());
		}
		
		for (int i = startingPoint; i > 0; i--) {
			List<RawCardId> rawCardIds = invertedMap.get(i);
			if (rawCardIds == null) {
				outputStream.write(VarInt.ZERO.getBytes());
				continue;
			}
			
			Map<SetVariant, List<RawCardId>> groupByMap = rawCardIds.stream().collect(Collectors.groupingBy(id -> new SetVariant(id.getSet(), id.getVariant())));
			List<SetVariant> setVariants = new ArrayList<>(groupByMap.keySet());
			Collections.sort(setVariants);
//System.out.println("{" + i + "} : " + setVariants.size());
			outputStream.write(new VarInt(setVariants.size()).getBytes());
			for (SetVariant setVariant : setVariants) {
				List<Integer> ids = groupByMap.get(setVariant).stream().map(raw -> raw.getId()).sorted().toList();
//System.out.print(" |- [" + ids.size() + " " + setVariant.set + " " + setVariant.variant + "]--> ");
//ids.forEach(id -> System.out.print(id + " "));
				outputStream.write(new VarInt(ids.size()).getBytes());
				outputStream.write(new VarInt(setVariant.set).getBytes());
				outputStream.write(new VarInt(setVariant.variant).getBytes());
				for (int id : ids) {
					outputStream.write(new VarInt(id).getBytes());
				}
//System.out.println();
			}
		}
	}
	
}
