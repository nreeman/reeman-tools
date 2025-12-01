package fr.reeman.tools.bits;

import java.util.List;
import java.util.function.Function;

//Copyright (C) 2024 Reeman Nicolas
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

// https://en.wikipedia.org/wiki/Base32

public class Base32 {
	
//	public class Base32Parameters {
//		
//		public enum PaddingPolicy {
//			NONE,
//			OPTIONAL,
//			REQUIRED
//		}
//		
//		private final List<Character> alphabet;
//		private final PaddingPolicy paddingPolicy;
//		private final char padding;
//
//		public Base32Parameters(List<Character> alphabet) {
//			this(alphabet, PaddingPolicy.OPTIONAL, '=');
//		}
//		
//		public Base32Parameters(List<Character> alphabet, PaddingPolicy paddingPolicy, char padding) {
//			this.alphabet = alphabet;
//			this.paddingPolicy = paddingPolicy;
//			this.padding = padding;
//		}
//	}
	
	private static final List<Character> ALPHABET_BASE32 = List.of(
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '7'
		);

	private static final List<Character> ALPHABET_Z_BASE32 = List.of(
			'y', 'b', 'n', 'd', 'r', 'f', 'g', '8',
			'e', 'j', 'k', 'm', 'c', 'p', 'q', 'x',
			'o', 't', '1', 'u', 'w', 'i', 's', 'z',
			'a', '3', '4', '5', 'h', '7', '6', '9'
		);

	/*
	 * 0 :                                          next = (decode << 3)
	 * 1 : current = (mask 0000 0111 decode >> 2) + next = (decode << 6)
	 * 2 : current = (mask 0011 1110 decode << 1)
	 * 3 : current = (mask 0000 0001 decode >> 4) + next = (decode << 4)
	 * 4 : current = (mask 0000 1111 decode >> 1) + next = (decode << 7)
	 * 5 : current = (mask 1000 0000 decode << 2)
	 * 6 : current = (mask 0000 0011 decode >> 3) + next = (decode << 5)
	 * 7 : current = (mask 0001 1111 decode >> 0)
	 */
	//                                                     0   1  2   3   4  5   6  7
	private static byte[] DECODE_CURRENT = new byte[] { -128, -2, 1, -4, -1, 2, -3, 0 };
	private static byte[] DECODE_NEXT    = new byte[] {    3,  6, 0,  4,  7, 0,  5, 0 };
	
	/**
	 * Decode une chaine de caractère en Base32 en un tableau d'octets
	 * 
	 * @param base32 Une chaine de caractère au format Base32
	 * @return La donnée une fois décodée.
	 * @throws RuntimeException Si la chaine en entrée n'est pas au format Base32
	 */
	public static byte[] decode(final String base32) {
		return decode(base32, c -> (byte)ALPHABET_BASE32.indexOf(Character.toUpperCase(c)));
	}

	public static byte[] zdecode(final String base32) {
		return decode(base32, c -> (byte)ALPHABET_Z_BASE32.indexOf(Character.toLowerCase(c)));
	}

	private static byte[] decode(final String base32, Function<Character, Byte> char2byte) {
		String local32 = base32;
		while (local32.length() > 0 && local32.charAt(local32.length() -1) == '=') {
			local32 = local32.substring(0, local32.length() - 1);
		}
		int size = ((local32.length() * 5) / 8);
		byte[] bytes = new byte[size];
		int decodeIndex = -1;
		for (int i = 0; i < local32.length(); i++) {
			byte bits = char2byte.apply(local32.charAt(i));
			if (DECODE_CURRENT[i%8] != -128) {
				if (DECODE_CURRENT[i%8] >= 0) {
					bytes[decodeIndex] = (byte) (bytes[decodeIndex] ^ (bits << DECODE_CURRENT[i%8]));
				} else {
					bytes[decodeIndex] = (byte) (bytes[decodeIndex] ^ (bits >> Math.abs(DECODE_CURRENT[i%8])));
				}
			}
			
			if (DECODE_NEXT[i%8] != 0 && decodeIndex < size - 1) {
				bytes[++decodeIndex] = (byte) (bits << DECODE_NEXT[i%8]);
			}
		}
		
		return bytes;
	}
	
	/*
	 * 0 :                                next >> 3              1111 1000
	 * 1 : ( current << 2 & 0001 1100 ) | next >> 6  0000 0111 + 1100 0000
	 * 2 :   current >> 1 & 0001 1111                0011 1110
	 * 3 : ( current << 4 & 0001 0000 ) | next >> 4  0000 0001 + 1111 0000
	 * 4 : ( current << 1 & 0001 1110 ) | next >> 7  0000 1111 + 1000 0000
	 * 5 :   current >> 2 & 0001 1111                0111 1100
	 * 6 : ( current << 3 & 0001 1000 ) | next >> 5  0000 0011 + 1110 0000
	 * 7 :   current      & 0001 1111                0001 1111
	 */
	//                                                                   0        1           2        3        4           5        6           7
	private static byte[] ENCODE_CURRENT         = new byte[] {       -128,       2,         -1,       4,       1,         -2,       3,          0 };
	private static byte[] ENCODE_NEXT            = new byte[] {          3,       6,       -128,       4,       7,       -128,       5,       -128 };
	private static byte[] ENCODE_CURRENT_MASK    = new byte[] { (byte)0xFF, 0b11100,    0b11111, 0b10000, 0b11110,    0b11111, 0b11000,    0b11111 };
	private static byte[] ENCODE_NEXT_MASK       = new byte[] {    0b11111,    0b11, (byte)0xFF,  0b1111,     0b1, (byte)0xFF,   0b111, (byte)0xFF };

	/**
	 * Encode un tableau d'octets en chaine de caractères Base32
	 * 
	 * @param bytes Le tableau d'octets à convertir
	 * @return La chaine de carctère Base32
	 */
	public static String encode(final byte[] bytes) {
		return encode(bytes, b -> ALPHABET_BASE32.get(b), false);
	}
	
	public static String encode(final byte[] bytes, boolean padding) {
		return encode(bytes, b -> ALPHABET_BASE32.get(b), padding);
	}
	
	public static String zencode(final byte[] bytes) {
		return encode(bytes, b -> ALPHABET_Z_BASE32.get(b), false);
	}
	
	private static String encode(final byte[] bytes, Function<Byte, Character> byte2char, boolean padding) {
		StringBuffer decode = new StringBuffer();
		int size = (bytes.length * 8 / 5) + (bytes.length * 8 % 5 != 0 ? 1 : 0);
		int codeIndex = -1;
		for (int i = 0; i < size; i++) {
			byte currentCode = 0;
			if (ENCODE_CURRENT[i%8] != -128) {
				if (ENCODE_CURRENT[i%8] < 0) {
					currentCode = (byte)( ENCODE_CURRENT_MASK[i%8] & (bytes[codeIndex] >> Math.abs(ENCODE_CURRENT[i%8])) );
				} else {
					currentCode = (byte)( ENCODE_CURRENT_MASK[i%8] & (bytes[codeIndex] << ENCODE_CURRENT[i%8]) );
				}
			}
			
			if (ENCODE_NEXT[i%8] != -128 && codeIndex < bytes.length - 1) {
				codeIndex++;
				currentCode = (byte)( currentCode | ( ENCODE_NEXT_MASK[i%8] & (bytes[codeIndex] >> ENCODE_NEXT[i%8]) ) );
			}
			
			decode.append(byte2char.apply(currentCode));
		}
		
		if (padding) {
			while (decode.length() % 8 != 0) {
				decode.append("=");
			}
		}
		
		return decode.toString();
	}

}
