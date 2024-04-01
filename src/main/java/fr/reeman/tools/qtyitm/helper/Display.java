package fr.reeman.tools.qtyitm.helper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import fr.reeman.tools.qtyitm.Comparaison;

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
public abstract class Display {

	abstract String generate(Comparaison comparaison);
		
	public void generate(Comparaison comparaison, OutputStream outputStream) throws IOException {
		generate(comparaison, outputStream, StandardCharsets.UTF_8);
	}

	public void generate(Comparaison comparaison, OutputStream outputStream, String charset) throws IOException {
		generate(comparaison, outputStream, Charset.forName(charset));
	}

	public void generate(Comparaison comparaison, OutputStream outputStream, Charset charset) throws IOException {
		outputStream.write(generate(comparaison).getBytes(charset));
	}

}
