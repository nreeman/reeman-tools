package fr.reeman.tools.bits;

import java.nio.ByteBuffer;

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
     * @param bytes
     * @param n
     * @return
     */
    public static byte[] shiftLeft(byte[] bytes, int n) {
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

	/**
	 * 
	 * Exemple : [0b00110001, 0b11001000] devient : "0x31 0xC8"
	 * 
	 * @param bytes Un tableau d'octets
	 * @return Une representation hexadécimale du tableau d'octets
	 */
	public static String hex(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		
    	if (bytes == null) {
    		return "null";
    	} else if (bytes.length == 0) {
    		return "";
    	}
    	
        for (byte b : bytes) {
            buffer.append(String.format("0x%02X ", b));
        }
        
        String result = buffer.toString();
        return result.substring(0, result.length() -1);
    }
	
    public static String hex(byte b)  { return String.format("0x%02X", b); }
    public static String hex(short s) { return hex(Bits.toBytes(s)); }
    public static String hex(int i)   { return hex(Bits.toBytes(i)); }
    public static String hex(long l)  { return hex(Bits.toBytes(l)); }
}
