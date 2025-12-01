package fr.reeman.tools.bits;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Base32Test {
	
	private final org.apache.commons.codec.binary.Base32 apache32 = new org.apache.commons.codec.binary.Base32();

	@Test
	public void test() {
		System.out.println(" ~ decode ~");
		decode("AA======");
		decode("AE======");
		decode("CA======");
		decode("AA");
		decode("AE");
		decode("ca");
		decode("74");
		decode("AaaQEAYEAUDAP7Y");
		System.out.println();
		System.out.println(" ~ encode ~");
		encode(new byte[] { 0x00 });
		encode(new byte[] { 0x01 });
		encode(new byte[] { 0x04 });
		encode(new byte[] { 0x10 });
		encode(new byte[] { (byte)0xFF });
		encode(new byte[] { 0x01, 0x00 });
		encode(new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, (byte)0xFF });
	}

	private void decode(String string) {
		String apache = Bits.hex(apache32.decode(string));
		String base32 = Bits.hex(Base32.decode(string));
		System.out.println(string + " --> Apache:[" + apache + "]; Base32:[" + base32 + "]");
		assertEquals(apache, base32);
	}

	private void encode(byte[] bytes) {
		String apache = new String(apache32.encode(bytes));
		String base32 = Base32.encode(bytes, true);
		System.out.println(Bits.hex(bytes) + " --> Apache:[" + apache + "]; Base32:[" + base32 + "]");
		assertEquals(apache, base32);
	}
	
	@Test
	public void zbase32() {
		zbase32(new byte [] { 0x06, 0x49, 0x79 });
		zbase32(new byte [] { 0x00 });
		zbase32(new byte [] { 0x00, 0x10 });
		zbase32(new byte [] { (byte)0xFF });
	}
	
	private void zbase32(byte[] bytes) {
		String base32 = Base32.zencode(bytes);
		byte[] result = Base32.zdecode(base32);
		System.out.println(Bits.hex(bytes)  + " --> " + base32 + " --> " + Bits.hex(result));
		assertEquals(base32, Base32.zencode(result));
	}
}
