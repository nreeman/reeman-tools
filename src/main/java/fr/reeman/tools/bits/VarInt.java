package fr.reeman.tools.bits;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public final class VarInt {

	private static final byte[] MASKS  = new byte[] { 0x7F, 0x3F, 0x1F, 0x0F, 0x07, 0x03, 0x01, 0x00 };

    /**
     * Traduit un tableau d'octets en son équivalent au format <code>VarInt</code>.
     *
     * @param bytes Le tableau d'octets à encoder
     * @return Le tableau d'octets encodé.
     */
    public static byte[] encode(byte[] bytes) {
    	if (bytes == null || bytes.length == 0) {
    		return new byte[0]; // On a rien en entrée, on a rien en sortie.
    	}
    	
        int length = bytes.length;
        
        // On vire les octets à 0 qui peuvent exister au début.
        for (byte b : bytes) {
            if (b != 0) {
                break;
            }
            length--;
        }
        length = Math.max(1, length);
        int varIntLength = (length * 8 / 7);
//println("int varIntLength = (bigIntegerTrueLength * 8 / 7) => %d", bigIntegerTrueLength);
        varIntLength += ( bytes[bytes.length - length] >> (8 - (length % 8)) ) == 0 ? 0 : 1;
//println("0b%s >> %d => 0b%s", bin(bigBytes[bigBytes.length - bigIntegerTrueLength]), 8 - (bigIntegerTrueLength % 8), bin((byte) (bigBytes[bigBytes.length - bigIntegerTrueLength] >> (8 - (bigIntegerTrueLength % 8)))));
        byte[] varBytes = new byte[varIntLength];

//println("bigInteger=%d (%s); bigBytes.length=%d; bigIntegerTrueLength=%d, varIntLength=%d", bigInteger, hex(bigInteger.toByteArray()), bigBytes.length, bigIntegerTrueLength, varIntLength);
        int sourceIndex = Math.max(1, bytes.length) - 1;
        for (int targetIndex = 0; targetIndex < varBytes.length; targetIndex++) {
            int mod = targetIndex % 8;
//println("[%d/%d] - %d", targetIndex, sourceIndex, mod);
            varBytes[targetIndex] = sourceIndex < 0 ? 0 : (byte) (bytes[sourceIndex] & MASKS[mod]);
//if (sourceIndex < 0) {
//println(" |- varBytes[%d] = 0", targetIndex);
//} else {
//println(" |- varBytes[%d] = (byte) (0b%s & 0x%02X) => 0b%s", targetIndex, bin(bigBytes[sourceIndex]), mask[mod], bin(varBytes[targetIndex]));
//}
            varBytes[targetIndex] = (byte) (varBytes[targetIndex] << mod);
//println(" |- varBytes[%d] = (byte) (varBytes[%d] << %d) => 0b%s", targetIndex, targetIndex, mod, bin(varBytes[targetIndex]));
            varBytes[targetIndex] |= targetIndex == (varBytes.length - 1) ? 0 : (byte) 0x80;
//println(" |- varBytes[%d] |= targetIndex == (varBytes.length - 1) ? 0 : (byte) 0x80 => 0b%s", targetIndex, bin(varBytes[targetIndex]));
            if (mod != 0) {
            	byte prev = (byte) (bytes[sourceIndex + 1] >> (8 - mod));
//println(" |- prev = (byte) (0b%s >> (8 - %d)) => 0b%s", bin(bigBytes[sourceIndex + 1]), mod, bin(prev));
            	prev = (byte) (prev & MASKS[7 - mod]);
//println(" |- prev = (byte) (prev & 0x%02X) => 0b%s", mask[7 - mod], bin(prev));
            	
                varBytes[targetIndex] |= prev;
//println(" |- varBytes[%d] |= prev => 0b%s", targetIndex, bin(prev), bin(varBytes[targetIndex]));
            }

            sourceIndex = (mod == 7) ? sourceIndex : sourceIndex - 1;
        }

//println(" \\--> %s", hex(varBytes));
        return varBytes;
    }

    /**
     * 
     * @param varInt
     * @return
     */
    public static byte[] decode(byte[] varInt) {
    	if (!isValidVarInt(varInt)) {
    		throw new NumberFormatException("Invalid VarInt");
    	}
    	byte[] result = new byte[] { (byte) (varInt[varInt.length - 1] & 0x7F) };
		for (int i = varInt.length - 2; i >= 0; i--) {
			result = Bits.shiftLeft(result, 7);
			result[result.length - 1] |= (byte) (varInt[i] & 0x7F);
		}
    	
    	return result;
    }

    /**
     * Lit les prochains octets comme un <code>VarInt</code>. ie :
     * Tant que le bit de poid fort est 1 on continue mais on s'arrête dès qu'on a un <code>VarInt</code> complet.
     *
     * @param input Un flux d'octets
     * @return Un <code>VarInt</code> ou <code>null</code> s'il n'a pu être construit.
     */
    public static VarInt read(ByteArrayInputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int i = input.read();
        
        if (i == -1) {
        	return null;
        }

        while (i != -1) {
            byte b = (byte) (255 & i);
            output.write(b);
            if ((b & 0x80) == 0x00) {
            	break;
            }
            
            i = input.read();
        }
        
        byte[] out = output.toByteArray();
        return out.length == 0 ? null : new VarInt(out);
    }

//    /**
//     * Lit les n prochains VarInt dans un flux
//     * @param input
//     * @param n Le nombre de VarInt à lire
//     * @return Un tableau contenant les VarInt lus.
//     */
//    public static VarInt[] read(ByteArrayInputStream input, int n) {
//        VarInt[] varInts = new VarInt[n];
//
//        for (int i = 0; i < n; i++) {
//            varInts[i] = read(input);
//        }
//
//        return varInts;
//    }

    /**
     * Lit un flux d'octets et le converti en tableau de <code>VarInt</code>.
     * 
     * @param input Un flux d'octets
     * @return Un tableau de <code>VarInt</code>
     */
    public static VarInt[] readAll(ByteArrayInputStream input) {
        List<VarInt> varInts = new ArrayList<>();

        VarInt varInt = read(input);
        while (varInt != null) {
            varInts.add(varInt);
            varInt = read(input);
        }

        return varInts.toArray(new VarInt[] {});
    }
    
    /**
     * Decode un flux de <code>VarInt</code> directement en tableau de <code>int</code>. 
     * 
     * @param bytes Un flux de <code>VarInt</code> sous forme de flux d'octets.
     * @return Un tableau de <code>int</code> qui est la traduction du flux.
     */
    public static int[] decodeAsIntArray(byte[] bytes) {
    	VarInt[] varInts =  readAll(new ByteArrayInputStream(bytes));
    	int result[] = new int[varInts.length];
    	for (int i = 0; i < varInts.length; i++) {
    		result[i] = varInts[i].intValue();
    	}
    	return result;
    }

    public static boolean isValidVarInt(byte[] bytes) {
    	if (bytes == null || bytes.length == 0) {
    		return false;
    	}
    	
    	for (int i = 0; i < bytes.length - 1; i++) {
    		if ( (bytes[i] & 0x80) == 0 ) {
    			return false;
    		}
    	}
    	
		return (bytes[bytes.length - 1] & 0x80) == 0;
	}


    private byte[] bytes;

    private VarInt(byte[] bytes) {
    	if (!isValidVarInt(bytes)) {
    		throw new NumberFormatException("Invalid VarInt");
    	}
    	this.bytes = bytes;
    }
    
	public VarInt(byte b) {
        this.bytes = encode(new byte[] { b });
    }

    public VarInt(short s) {
        this.bytes = encode(Bits.toBytes(s));
    }

    public VarInt(int i) {
        this.bytes = encode(Bits.toBytes(i));
    }

    public VarInt(long l) {
    	this.bytes = encode(Bits.toBytes(l));
    }

    @Override
    public String toString() {
    	return "[" + Bits.hex(bytes) + "]";
    }
    
    public byte[] getBytes() {
        return bytes.clone();
    }

    public byte byteValue() {
        return Bits.toByte(decode(bytes));
    }
    
    public short shortValue() {
        return Bits.toByte(decode(bytes));
    }
    
	public int intValue() {
        return Bits.toInt(decode(bytes));
    }

    public long longValue() {
        return Bits.toLong(decode(bytes));
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VarInt other = (VarInt) obj;
		return Arrays.equals(bytes, other.bytes);
	}
}