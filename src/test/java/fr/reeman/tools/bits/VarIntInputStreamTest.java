package fr.reeman.tools.bits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class VarIntInputStreamTest {

	
	@Test
	public void readNull() throws IOException {
		try (VarIntInputStream inputStream = new VarIntInputStream(new byte[] {})) {
			assertNull(inputStream.read());
		}
	}
	
	@Test
	public void read() throws IOException {
		read(127);
		read(127, 23456);
		read(127, 23456, Integer.MAX_VALUE);
		read(127, 23456, Integer.MAX_VALUE, Integer.MIN_VALUE);
	}

	private void read(Integer... values) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (Integer value : values) {
			outputStream.write(new VarInt(value).getBytes());
		}
		try (VarIntInputStream inputStream = new VarIntInputStream(outputStream.toByteArray())) {
			for (int i = 0; i < values.length; i++) {
				assertEquals(values[i].intValue(), inputStream.read().intValue());
			}
			assertNull(inputStream.read());
		}
	}
	
	@Test
	public void readAll() throws IOException {
		long[] longs = new long[] { 127, 23456, Long.MAX_VALUE, Long.MIN_VALUE };
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (long l : longs) {
			outputStream.write(new VarInt(l).getBytes());
		}
		try (VarIntInputStream inputStream = new VarIntInputStream(outputStream.toByteArray())) {
			VarInt[] varInts = inputStream.readAll();
			for (int i = 0; i < longs.length; i++) {
				assertEquals(longs[i], varInts[i].longValue());
			}
			assertNull(inputStream.read());
		}
	}
}
