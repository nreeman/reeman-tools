package fr.reeman.tools.bits;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class VarIntTest {
	
	@Test
	public void encodeDecode() {
//		System.out.println("Test encodeDecode()");
//		System.out.println("+ Bits.toBytes(Integer.MAX_VALUE)");
		encodeDecode(Bits.toBytes(Integer.MAX_VALUE));

		byte[] bytes = new byte[] { 0x04, (byte)0xAA };
//		System.out.println("+ { 0x04, (byte)0xAA }");
		encodeDecode(bytes);

		bytes = new byte[] { (byte)0xAA, (byte)0xBB, (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF };
//		System.out.println("+ { (byte)0xAA, (byte)0xBB, (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF }");
		encodeDecode(bytes);
	}
	
	private void encodeDecode(byte[] input) {
//		System.out.println(new SuperStringBuffer(" - input  = ").hex(input).toString());
		byte[] encode = VarInt.encode(input);
//		System.out.println(new SuperStringBuffer(" - encode = ").hex(encode).toString());
		byte[] decode = VarInt.decode(encode);
//		System.out.println(new SuperStringBuffer(" - decode = ").hex(decode).toString());
		assertTrue(Arrays.equals(input, decode));
//		System.out.println(" - " + ConsoleColors.OK());
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
