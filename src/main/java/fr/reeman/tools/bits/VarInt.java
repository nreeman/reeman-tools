package fr.reeman.tools.bits;

import java.util.Arrays;

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
	
	private static final byte[] MASKS = new byte[] { 0x7F, 0x3F, 0x1F, 0x0F, 0x07, 0x03, 0x01, 0x00 };

	public static final VarInt ZERO = new VarInt(new byte[] { 0x00 });

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
        varIntLength += ( bytes[bytes.length - length] >> (8 - (length % 8)) ) == 0 ? 0 : 1;
        byte[] varBytes = new byte[varIntLength];

        int sourceIndex = Math.max(1, bytes.length) - 1;
        for (int targetIndex = 0; targetIndex < varBytes.length; targetIndex++) {
            int mod = targetIndex % 8;
            varBytes[targetIndex] = sourceIndex < 0 ? 0 : (byte) (bytes[sourceIndex] & MASKS[mod]);
            varBytes[targetIndex] = (byte) (varBytes[targetIndex] << mod);
            varBytes[targetIndex] |= targetIndex == (varBytes.length - 1) ? 0 : (byte) 0x80;
            if (mod != 0) {
            	byte prev = (byte) (bytes[sourceIndex + 1] >> (8 - mod));
            	prev = (byte) (prev & MASKS[7 - mod]);
            	
                varBytes[targetIndex] |= prev;
            }
            sourceIndex = (mod == 7) ? sourceIndex : sourceIndex - 1;
        }

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

    public VarInt(byte[] bytes) {
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