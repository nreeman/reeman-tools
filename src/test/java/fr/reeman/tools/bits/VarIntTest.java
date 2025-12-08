package fr.reeman.tools.bits;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class VarIntTest {
	
	@Test
	public void encodeDecode() {
		encodeDecode(Bits.toBytes(Integer.MAX_VALUE));
		encodeDecode(new byte[] { 0x00 });
		encodeDecode(new byte[] { 0x04, (byte)0xAA });
		encodeDecode(new byte[] { (byte)0xAA, (byte)0xBB, (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF });
	}
	
	private void encodeDecode(byte[] input) {
		byte[] encode = VarInt.encode(input);
		byte[] decode = VarInt.decode(encode);
		assertTrue(Arrays.equals(input, decode));
	}
	
	@Test
	public void isValidVarInt() {
		assertTrue(VarInt.isValidVarInt(new byte [] { 0x00 }));
		assertTrue(VarInt.isValidVarInt(new byte [] { (byte)0x80, 0x78 }));
		assertTrue(VarInt.isValidVarInt(new byte [] { (byte)0xA5, (byte)0xCC, 0x00 }));
		
		assertFalse(VarInt.isValidVarInt(null));
		assertFalse(VarInt.isValidVarInt(new byte [] {}));
		assertFalse(VarInt.isValidVarInt(new byte [] { (byte)0x80 }));
		assertFalse(VarInt.isValidVarInt(new byte [] { (byte)0xA5, (byte)0x4C, 0x00 }));
	}
}
