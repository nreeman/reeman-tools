package fr.reeman.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
public class Stream {

	/**
	 * 
	 * @param input
	 * @param output
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static long copy(final InputStream input, final OutputStream output, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];
		long count = 0;
		int n;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * 
	 * @param input
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(final InputStream input, int bufferSize) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		copy(input, byteArrayOutputStream, bufferSize);
		return byteArrayOutputStream.toByteArray();
	}

}
