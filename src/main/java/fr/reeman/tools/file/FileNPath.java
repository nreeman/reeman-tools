package fr.reeman.tools.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class FileNPath {

	/*
	 * FILE
	 */
	public static void write(final String content, final String charset, final String fullPathName) throws IOException {
		write(content.getBytes(charset), fullPathName);
	}
	
	public static void write(final String content, final Charset charset, final String fullPathName) throws IOException {
		write(content.getBytes(charset), fullPathName);
	}

	public static void write(final byte[] content, final String fullPathName) throws IOException {
		try(FileOutputStream fos = new FileOutputStream(fullPathName)) {
			fos.write(content);
		}
	}
	
	public static void write(final String content, final Charset charset, final OutputStream outputStream) throws IOException {
		outputStream.write(content.getBytes(charset));
	}

	public static boolean deleteDirectory(final String dir) {
		return deleteDirectory(new File(dir));
	}
	
	public static boolean deleteDirectory(final File dir) {
		File[] allContents = dir.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		
		return dir.delete();
	}
	
	public static String convert(final String source, final String charset) throws UnsupportedEncodingException {
		return new String(source.getBytes(charset), charset);
	}
	
	public static String readAsString(final String filename, final String charset) throws IOException {
		return new String(Files.readAllBytes(new File(filename).toPath()), Charset.forName(charset));
	}
	
	public static String readAsString(final File file, final Charset charset) throws IOException {
		return new String(Files.readAllBytes(file.toPath()), charset);
	}
	
	public static String readAsString(final Path path, final Charset charset) throws IOException {
		return new String(Files.readAllBytes(path), charset);
	}
	
	public static List<String> readAllLines(final String filename, final String charset) throws IOException {
		return Files.readAllLines(new File(filename).toPath(), Charset.forName(charset));
	}
	
	public static List<String> readAllLines(final String filename, final Charset charset) throws IOException {
		return Files.readAllLines(new File(filename).toPath(), charset);
	}
	
	public static List<String> readAllLines(final Path path, final Charset charset) throws IOException {
		return Files.readAllLines(path, charset);
	}
	
	public static void copyFile(final String src, final String dest) throws IOException {
		Files.copy(new File(src).toPath(), new File(dest).toPath());
	}

	/*
	 * PATH
	 */
	public static String removePathAndExtension(final String path) {
		return path.substring(Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\')) + 1, path.indexOf('.'));
	}

	public static String normalizeDirectoryName(final String directoryName) {
		return directoryName == null || "".equals(directoryName)
				? ""
				: directoryName.replace("\\", "/") + (directoryName.endsWith("/") || directoryName.endsWith("\\") ? "":"/");
	}
	
	public static String normalizeDirectoryNameWindows(final String directoryName) {
		return directoryName == null || "".equals(directoryName)
				? ""
				: directoryName.replace("/", "\\") + (directoryName.endsWith("/") || directoryName.endsWith("\\") ? "":"\\");
	}

	public static String normalizePathWindows(final String path) {
		return path.replace("/", "\\");
	}
}
