package fr.reeman.tools.filters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import fr.reeman.tools.ImmutablePair;
import fr.reeman.tools.Shortcuts;

public class CollectionFilterTest {

	@Test
	public void filterTest() throws IOException {
		List<String> mots = Files.readAllLines(new File("D:\\Nicolas\\dico_motus.txt").toPath());

		StringCollectionFilter wordsFilter = StringCollectionFilter
				.builder()
//				.regex("s....[^s]") // 6
//				.regex("s......r") // 8
//				.regex("s...tre").exclude(List.of('a', 'u')).exactly(1, 't') // 9
				.regex("a.o.....[^s]") // 9
				.atLeast(1, 'e')
				.atLeast(1, 's')
				.atLeast(1, 'a')
				.atLeast(1, 'i')
				
//				.regex("a[^s]s......")
//				.exclude(List.of('i', 'i', 'l', 'm', 'r'))
////				.exactly(1, 's')
//				.atLeast(1, 'a')
////				.exactly(1, 'a')
////				.exactly(1, 'i')
////				.exactly(1, 'r')
////				.exactly(2, 'l')
				
				.build();
		
		StringCollectionFilter motusFilter = StringCollectionFilter
				.motusBuilder()
				.motus("evincement", "#.o..ooo..")
//				.motus("materiel", "o..#o.o.")
//				.motus("etalon", "#oo..o")
//				.motus("s", "#.......")
				.build();
//		motusFilter.add(s -> Pattern.compile("f\\w{5}").matcher(s).matches());

		System.out.println(wordsFilter);
		List<String> result = wordsFilter.filter(mots);
		System.out.println("Nb de résultat" + (result.size() > 1 ? "s" : "") + " : " + result.size());
		if (result.size() != 0) {
//			System.out.println(String.join(", ", result.size() <= 100 ? result : sortedRandom(result, 10)));
		}

		System.out.println("----");
		
		System.out.println(motusFilter);
		result = motusFilter.filter(mots);
		System.out.println("Nb de résultat" + (result.size() > 1 ? "s" : "") + " : " + result.size());
		if (result.size() != 0) {
//			System.out.println(String.join(", ", result.size() <= 100 ? result : sortedRandom(result, 10)));
		}
	}
	
	private HashSet<String> sortedRandom(List<String> list, int n) {
		HashSet<String> set = new HashSet<String>();
		while (set.size() < n && set.size() < list.size()) {
//			set.add(list.get(new Random().nextInt(list.size())));
		}

		return set;
	}
	
//	@Test
	public void initial() throws IOException {
		List<String> mots = Files.readAllLines(new File("D:\\Nicolas\\dico_motus.txt").toPath());
		
		for (char c = 'a'; c <= 'z'; c++) {
			StringCollectionFilter wordsFilter = StringCollectionFilter
					.builder()
					.regex(c + "....r")
					.atLeast(1, 'e')
					.atLeast(1, 'a')
					.atLeast(1, 's')
//					.atLeast(1, 'i')
					.build();

			System.out.println(c + " -> " + wordsFilter.filter(mots));	
		}
	}
	
//	@Test
	public void buildDico() throws IOException {
		Pattern hasCapital = Pattern.compile("[A-Z].*");
		Pattern hasDash = Pattern.compile(".*-.*");
		List<String> raw = Files.readAllLines(new File("D:\\Nicolas\\liste.de.mots.francais.frgut.txt").toPath());
		Set<String> mots = new TreeSet<>(raw
											.stream()
											.map(Shortcuts::removeAccents)
											.filter(w -> !hasCapital.matcher(w).matches())
											.filter(w -> !hasDash.matcher(w).matches())
											.toList(
										));
		
		try (FileOutputStream output = new FileOutputStream("D:\\Nicolas\\dico_motus.txt")) {
			for (String mot : mots) {
				output.write((mot + "\n").getBytes(StandardCharsets.UTF_8));
			}
			output.flush();
		}
	}
}