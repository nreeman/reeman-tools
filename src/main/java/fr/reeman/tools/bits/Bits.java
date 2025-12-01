package fr.reeman.tools.bits;

import java.nio.ByteBuffer;
import java.util.function.Function;

import lombok.NonNull;

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
public class Bits {

	// byte
	public static byte[] toBytes(byte value) {
		return new byte[] { value };
	}
	public static byte toByte(byte[] bytes) {
    	if (bytes.length > 1) {
    		throw new NumberFormatException(String.format("Conversion de [%s] en byte impossible, la taille est de %d pour %d attendue.", hex(bytes), bytes.length, 1));
    	}
        return bytes[0];
	}

	// short
	public static byte[] toBytes(short value) {
		return ByteBuffer.allocate(2).putShort(value).array();
	}
	public static short toShort(byte[] bytes) {
    	if (bytes.length > 2) {
    		throw new NumberFormatException(String.format("Conversion de [%s] en short impossible, la taille est de %d pour %d attendue.", hex(bytes), bytes.length, 2));
    	}
        return ByteBuffer.wrap(extendSize(bytes, 2)).getShort();
	}

	// int
	public static byte[] toBytes(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}
	public static int toInt(byte[] bytes) {
    	if (bytes.length > 4) {
    		throw new NumberFormatException(String.format("Conversion de [%s] en int impossible, la taille est de %d pour %d attendue.", hex(bytes), bytes.length, 4));
    	}
        return ByteBuffer.wrap(extendSize(bytes, 4)).getInt();
	}

	// long
	public static byte[] toBytes(long value) {
		return ByteBuffer.allocate(8).putLong(value).array();
	}
	public static long toLong(byte[] bytes) {
    	if (bytes.length > 8) {
    		throw new NumberFormatException(String.format("Conversion de [%s] en long impossible, la taille est de %d pour %d attendue.", hex(bytes), bytes.length, 8));
    	}
        return ByteBuffer.wrap(extendSize(bytes, 8)).getLong();
	}

    /**
     * 
     * Déplace les bits de n positions vers la gauche en ignorant et supprimant les octets à zéro non significatifs
     * 
     * shiftLeft({ 0x01 }, 2)       => { 0x04 }
     * shiftLeft({ 0x80 }, 1)       => { 0x01, 0x00 }
     * shiftLeft({ 0x00, 0x01 }, 2) => { 0x04 }
     * shiftLeft({ 0x00 }, 1000) => 0x00
     * 
     * @param bytes
     * @param n
     * @return
     */
    public static byte[] shiftLeft(@NonNull byte[] bytes, int n) {
    	int nMod8 = n % 8;
    	int nbNotSignificantLeading0 = 0;
    	for (int i = 0; i < bytes.length - 1; i++) {
    		if (bytes[i] == 0) {
    			nbNotSignificantLeading0++;
    		} else {
    			break;
    		}
    	}
    	int bytesTrueLength = bytes.length - nbNotSignificantLeading0;
    	
    	if (bytesTrueLength == 1 && bytes[bytes.length - 1] == 0) {
    		// Cas particulier om il n'y aucun bit significatif
    		return new byte[] { 0x00 };
    	}
    	
		byte[] result = new byte[bytesTrueLength + (n / 8) + ( (255 & bytes[nbNotSignificantLeading0]) >>> (8 - nMod8) == 0 ? 0 : 1)];
    	System.arraycopy(bytes, nbNotSignificantLeading0, result, result.length - bytesTrueLength - (n / 8), bytesTrueLength);
    	
    	if (nMod8 != 0) {
    		for (int i = 0; i < result.length; i++) {
    			result[i] = (byte) ( (0xFF & result[i]) << nMod8 | ( (i < result.length - 1) ? (0xFF & result[i + 1]) >>> (8 - nMod8) : 0 ) );
    		}
    	}

    	return result;
    }

    private static byte[] extendSize(byte[] value, int size) {
    	if (value.length < size) {
    		byte[] result = new byte[size];
    		System.arraycopy(value, 0, result, size - value.length, value.length);
    		value = result;
    	}
    	
		return value;
	}

    //           //
    //           //
    // FORMATAGE //
    //           //
    //           //
	public static String dec(final byte[] bytes) {
		return formatBytesArrays(bytes, b -> Byte.toUnsignedInt(b) + "");
    }

	public static String bin(final byte[] bytes) {
		return formatBytesArrays(bytes, b -> bin(b));
	}
    public static String bin(byte b) {
    	String hex = String.format("%02X", b);
    	return hex2bin(hex.charAt(0)) + hex2bin(hex.charAt(1));
    }
    public static String bin(short s) { return bin(Bits.toBytes(s)); }
    public static String bin(int i)   { return bin(Bits.toBytes(i)); }
    public static String bin(long l)  { return bin(Bits.toBytes(l)); }

	/**
	 * 
	 * Exemple : [0b00110001, 0b11001000] devient : "0x31 0xC8"
	 * 
	 * @param bytes Un tableau d'octets
	 * @return Une representation hexadécimale du tableau d'octets
	 */
	public static String hex(final byte[] bytes) {
		return formatBytesArrays(bytes, b -> hex(b));
    }
	public static String hex(byte b)  { return String.format("0x%02X", b); }
    public static String hex(short s) { return hex(Bits.toBytes(s)); }
    public static String hex(int i)   { return hex(Bits.toBytes(i)); }
    public static String hex(long l)  { return hex(Bits.toBytes(l)); }
    
    public static String hex2bin(char c) {
    	return switch (c) {
			case '0' -> "0000";
			case '1' -> "0001";
			case '2' -> "0010";
			case '3' -> "0011";
			case '4' -> "0100";
			case '5' -> "0101";
			case '6' -> "0110";
			case '7' -> "0111";
			case '8' -> "1000";
			case '9' -> "1001";
			case 'A', 'a' -> "1010";
			case 'B', 'b' -> "1011";
			case 'C', 'c' -> "1100";
			case 'D', 'd' -> "1101";
			case 'E', 'e' -> "1110";
			case 'F', 'f' -> "1111";
			default -> throw new IllegalArgumentException("Unexpected value: " + c);
		};
    }

	private static String formatBytesArrays(final byte[] bytes, Function<Byte, String> mapper) {
    	if (bytes == null) {
    		return "null";
    	} else if (bytes.length == 0) {
    		return "";
    	}
    	
		StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(mapper.apply(b)).append(" ");
        }
        
        String result = buffer.toString();
        return result.substring(0, result.length() -1);
    }
}
