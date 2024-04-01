package fr.reeman.tools.bits;

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
public class Base32 {

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
		int size = (base32.length() * 5) / 8 + ((base32.length() * 5) % 8 != 0 ? 1 : 0);
		byte[] bytes = new byte[size];
		int decodeIndex = -1;
		for (int i = 0; i < base32.toUpperCase().length(); i++) {
			char c = base32.charAt(i);
			if (c == '=') {
				break;
			}
			byte bits = charToBase32(c);
			if (DECODE_CURRENT[i%8] != -128) {
				if (DECODE_CURRENT[i%8] >= 0) {
					bytes[decodeIndex] = (byte) (bytes[decodeIndex] ^ (bits << DECODE_CURRENT[i%8]));
				} else {
					bytes[decodeIndex] = (byte) (bytes[decodeIndex] ^ (bits >> Math.abs(DECODE_CURRENT[i%8])));
				}
			}
			
			if (DECODE_NEXT[i%8] != 0) {
				decodeIndex ++;
				bytes[decodeIndex] = (byte) (bits << DECODE_NEXT[i%8]);
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
		StringBuffer decode = new StringBuffer();
		int size = (bytes.length * 8 / 5) + ( bytes.length * 8 % 5 != 0 ? 1 : 0);
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
			
			decode.append(base32ToChar(currentCode));
		}
		
		return decode.toString();
	}

	private static byte charToBase32(char c) {
		switch (c) {
			case 'A': return 0b00000;
			case 'B': return 0b00001;
			case 'C': return 0b00010;
			case 'D': return 0b00011;
			case 'E': return 0b00100;
			case 'F': return 0b00101;
			case 'G': return 0b00110;
			case 'H': return 0b00111;
			case 'I': return 0b01000;
			case 'J': return 0b01001;
			case 'K': return 0b01010;
			case 'L': return 0b01011;
			case 'M': return 0b01100;
			case 'N': return 0b01101;
			case 'O': return 0b01110;
			case 'P': return 0b01111;
			case 'Q': return 0b10000;
			case 'R': return 0b10001;
			case 'S': return 0b10010;
			case 'T': return 0b10011;
			case 'U': return 0b10100;
			case 'V': return 0b10101;
			case 'W': return 0b10110;
			case 'X': return 0b10111;
			case 'Y': return 0b11000;
			case 'Z': return 0b11001;
			case '2': return 0b11010;
			case '3': return 0b11011;
			case '4': return 0b11100;
			case '5': return 0b11101;
			case '6': return 0b11110;
			case '7': return 0b11111; 
			default:
				throw new RuntimeException("Le caractère '" + c + "' n'est pas géré en Base32.");
		}
	}

	private static char base32ToChar(byte b) {
		switch (b) {
			case 0b00000: return 'A';
			case 0b00001: return 'B';
			case 0b00010: return 'C';
			case 0b00011: return 'D';
			case 0b00100: return 'E';
			case 0b00101: return 'F';
			case 0b00110: return 'G';
			case 0b00111: return 'H';
			case 0b01000: return 'I';
			case 0b01001: return 'J';
			case 0b01010: return 'K';
			case 0b01011: return 'L';
			case 0b01100: return 'M';
			case 0b01101: return 'N';
			case 0b01110: return 'O';
			case 0b01111: return 'P';
			case 0b10000: return 'Q';
			case 0b10001: return 'R';
			case 0b10010: return 'S';
			case 0b10011: return 'T';
			case 0b10100: return 'U';
			case 0b10101: return 'V';
			case 0b10110: return 'W';
			case 0b10111: return 'X';
			case 0b11000: return 'Y';
			case 0b11001: return 'Z';
			case 0b11010: return '2';
			case 0b11011: return '3';
			case 0b11100: return '4';
			case 0b11101: return '5';
			case 0b11110: return '6';
			default: return '7'; // 0b11111
		}
	}
}
