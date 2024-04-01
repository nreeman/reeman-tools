package fr.reeman.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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
public class Zip {
	
	public static final long DEFAULT_MAX_ENTRY_SIZE         = 1024l*1024l*1024l; // 1 Go
	public static final long DEFAULT_MAX_ENTRIES            = 10000l;
	public static final long DEFAULT_MAX_COMPRESSION_RATTIO = 10l;

	private final long maxEntrySize;
	private final long maxEntries;
	private final long maxCompressionRatio;
	
	/**
	 * Cré un objet Zip avec les paramétrage par défaut
	 */
	public Zip() {
		this(
			DEFAULT_MAX_ENTRY_SIZE,
			DEFAULT_MAX_ENTRIES,
			DEFAULT_MAX_COMPRESSION_RATTIO
		);
	}
	
	/**
	 * Constructeur qui permet de choisir ses paramètres.
	 * 
	 * @param maxEntrySize
	 * @param maxEntries
	 * @param maxCompressionRatio
	 */
	public Zip(final long maxEntrySize, final long maxEntries, final long maxCompressionRatio) {
		this.maxEntrySize        = maxEntrySize;
		this.maxEntries          = maxEntries;
		this.maxCompressionRatio = maxCompressionRatio;
	}

	/**
	 * Décompresse le contenu d'un fichier zip dans un répertoire.
	 * 
	 * @param fileName Le nom du fichier à décompresser.
	 * @param targetDirectory Le répertoire cible.
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws FileNotFoundException Si le fichier source n'est pas accessible.
	 * @throws IOException En cas de problème lors de l'écriture.
	 */
	public Collection<String> unzip(final String fileName, final String targetDirectory) throws FileNotFoundException, IOException {
		try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
			return unzip(fileInputStream, targetDirectory);
		}
	}

	/**
	 * Décompresse le contenu d'un fichier zip dans un répertoire.
	 * 
	 * @param fileName Le nom du fichier à décompresser.
	 * @param targetDirectory Le répertoire cible.
	 * @param predicate Un filtre sur les fichier à décompresser.
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws FileNotFoundException Si le fichier source n'est pas accessible.
	 * @throws IOException En cas de problème lors de l'écriture.
	 */
	public Collection<String> unzip(final String fileName, final String targetDirectory, final Predicate<String> predicate) throws FileNotFoundException, IOException {
		try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
			return unzip(fileInputStream, targetDirectory, predicate);
		}
	}
	
	/**
	 * Décompresse le contenu d'un fichier zip dans un répertoire.
	 * 
	 * @param fileName Le fichier à décompresser.
	 * @param targetDirectory Le répertoire cible.
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws FileNotFoundException Si le fichier source n'est pas accessible.
	 * @throws IOException En cas de problème lors de l'écriture.
	 */
	public Collection<String> unzip(final File file, final String targetDirectory) throws FileNotFoundException, IOException {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			return unzip(fileInputStream, targetDirectory);
		}
	}
	
	/**
	 * Décompresse le contenu d'un fichier zip dans un répertoire.
	 * 
	 * @param fileName Le fichier à décompresser.
	 * @param targetDirectory Le répertoire cible.
	 * @param predicate Un filtre sur les fichier à décompresser.
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws FileNotFoundException Si le fichier source n'est pas accessible.
	 * @throws IOException En cas de problème lors de l'écriture.
	 */
	public Collection<String> unzip(final File file, final String targetDirectory, final Predicate<String> predicate) throws FileNotFoundException, IOException {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			return unzip(fileInputStream, targetDirectory, predicate);
		}
	}
	
	/**
	 * Décompresse le contenu d'un fichier zip depuis une URL.
	 * 
	 * @param url L'URL du fichier a décompresser
	 * @param targetDirectory Le répertoire cible.
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws FileNotFoundException Si le fichier source n'est pas accessible.
	 * @throws IOException En cas de problème lors de l'écriture.
	 */
	public Collection<String> unzip(final URL url, final String targetDirectory) throws FileNotFoundException, IOException {
		return unzip(url, targetDirectory, s -> true);
	}
	
	/**
	 * Décompresse le contenu d'un fichier zip depuis une URL.
	 * 
	 * @param url L'URL du fichier a décompresser
	 * @param targetDirectory Le répertoire cible.
	 * @param predicate Un filtre sur les fichier à décompresser.
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws FileNotFoundException Si le fichier source n'est pas accessible.
	 * @throws IOException En cas de problème lors de l'écriture.
	 */
	public Collection<String> unzip(final URL url, final String targetDirectory, final Predicate<String> predicate) throws FileNotFoundException, IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(10 * 60 * 1000);
		try (InputStream inputStream = connection.getInputStream()) {
			return unzip(inputStream, targetDirectory, predicate);
		}
	}
	
	/**
	 * Décompresse un flux zip dans le répertoire cible. 
	 * 
	 * @param inputStream
	 * @param targetDirectory
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws IOException
	 */
	public Collection<String> unzip(final InputStream inputStream, final String targetDirectory) throws IOException {
		return unzip(inputStream, targetDirectory, s -> true);
	}
	
	/**
	 * Décompresse un flux zip dans le répertoire cible. 
	 * 
	 * @param inputStream
	 * @param targetDirectory
	 * @param predicate Un filtre sur les fichier à décompresser.
	 * @return Une collection contenant les noms des fichiers décompressés.
	 * @throws IOException
	 */
	public Collection<String> unzip(final InputStream inputStream, final String targetDirectory, final Predicate<String> predicate) throws IOException {
		Collection<String> unzippedFiles = new ArrayList<String>();
		String dir = targetDirectory + (targetDirectory.endsWith("/") || targetDirectory.endsWith("\\") ? "":"/");
		long nbEntries = 0;
		
		try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
			
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			nbEntries++;
			while (zipEntry != null) {
				if (predicate.test(zipEntry.getName())) {
					buildSubDirs(dir + zipEntry.getName());
					try (FileOutputStream fileOutputStream = new FileOutputStream(dir + zipEntry.getName())) {
						long entrySize = copy(zipInputStream, fileOutputStream);
						double ratio = (double)entrySize / zipEntry.getCompressedSize();
						if (ratio > maxCompressionRatio) {
							throw new IOException(String.format("Possibilité de Zip Bomb : Le ratio de compression du fichier '%s' est de %.2f pour un max de %d.", zipEntry.getName(), ratio, maxCompressionRatio));
						}
						
						unzippedFiles.add(dir + zipEntry.getName());
						fileOutputStream.close();
						zipInputStream.closeEntry();
					}
				}
				
				zipEntry = zipInputStream.getNextEntry();
				nbEntries++;
				if (nbEntries > maxEntries) {
					throw new IOException("Possibilité de Zip Bomb : Le nombre d'entrées dans le fichier dépasse les " + maxEntries + ".");
				}
			}
		}
		
		return unzippedFiles;
	}

	private void buildSubDirs(final String fileName) {
		int index = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if (index != -1) {
			new File(fileName.substring(0, index)).mkdirs();
		}
	}

	/**
	 * Copy un flux dans un autre en s'interrompant si la maxEntrySize est dépassé.
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	private long copy(final InputStream input, final OutputStream output) throws IOException {
		byte[] buffer = new byte[1024*1024];
		long count = 0;
		int n;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
			count += n;
			if (count > maxEntrySize) {
				throw new IOException("Possibilité de Zip Bomb : La taille de l'entrée dépasse les " + maxEntrySize + " octets.");
			}
		}
		return count;
	}


	/**
	 * Zip le contenu d'une chaîne de caractère dans un fichier.
	 * 
	 * @param content La chaîne à zipper.
	 * @param charset Le jeu de caractère utilisé dans la chaîne.
	 * @param fullName Le chemin complet du fichier à créer
	 * @throws IOException En cas d'erreur d'écriture du fichier.
	 */
	//TODO Faire mieux
	public static void zip(final String content, final String charset, final String fullName) throws IOException {
		zip(content.getBytes(charset), fullName);
	}

	/**
	 * Zip des octets dans un fichier.
	 * 
	 * @param content Les octets à zippers
	 * @param fullName Le chemi complet du fichier à créer.
	 * @throws IOException En cas d'erreur d'écriture du fichier.
	 */
	//TODO Faire mieux
	public static void zip(final byte[] content, final String fullName) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(fullName);
				ZipOutputStream out = new ZipOutputStream(fileOutputStream)) {
			int index = Math.max(fullName.lastIndexOf('/'), fullName.lastIndexOf('\\'));
			String fileName = (index != -1) ? fullName.substring(index + 1) : fullName;
			ZipEntry zipEntry = new ZipEntry(fileName);
			out.putNextEntry(zipEntry);
			out.write(content, 0, content.length);
		}
	}

	

	public void zip(final String zipFileName, final String[] filesToZip) throws Exception {
		zip(zipFileName, filesToZip, null);
	}

	public void zip(final String zipFileName, final String[] filesToZip, final String[] excludePatterns) throws Exception {
		List<Pattern> excludes = new ArrayList<>();
		if (excludePatterns != null) {
			for (String pattern : excludePatterns) {
				excludes.add(Pattern.compile(pattern));
			}
		}
		
	    try (FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos)) {

	        byte[] buffer = new byte[1024];

	        for (String fileToZip : filesToZip) {
	            File file = new File(fileToZip);
	            if (file.isDirectory()) {
	                addFolderToZip(file, file.getName(), zos, buffer, excludes);
	            } else {
	                addFileToZip(file, zos, buffer, "", excludes);
	            }
	        }
	    }
	}

	private void addFolderToZip(final File folder, final String parentFolderName, final ZipOutputStream zos, final byte[] buffer, final List<Pattern> excludes) throws Exception {
	    for (File file : folder.listFiles()) {
	        if (file.isDirectory()) {
	            addFolderToZip(file, parentFolderName + "/" + file.getName(), zos, buffer, excludes);
	        } else {
	            addFileToZip(file, zos, buffer, parentFolderName, excludes);
	        }
	    }
	}

	private void addFileToZip(final File file, final ZipOutputStream zos, final byte[] buffer, final String parentFolderName, final List<Pattern> excludes) throws Exception {
		String entryPath = parentFolderName.isEmpty() ? file.getName() : parentFolderName + "/" + file.getName();

		if (!isExcluded(entryPath, excludes)) {
		    try (FileInputStream fis = new FileInputStream(file)) {
		        zos.putNextEntry(new ZipEntry(entryPath));

		        int length;
		        while ((length = fis.read(buffer)) > 0) {
		            zos.write(buffer, 0, length);
		        }

		        zos.closeEntry();
		    }
		}
	}

	private boolean isExcluded(final String entryPath, final List<Pattern> excludes) {
		if (excludes == null) {
			return false;
		}
		
		for (Pattern pattern : excludes) {
			if (pattern.matcher(entryPath).matches()) {
				return true;
			}
		}
		
		return false;
	}
}
