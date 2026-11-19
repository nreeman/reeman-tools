package fr.reeman.tools.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import fr.reeman.tools.ImmutablePair;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class StringCollectionFilter extends CollectionFilter<String> {

	public static Builder builder() {
		return new Builder();
	}
	
	public static MotusBuilder motusBuilder() {
		return new MotusBuilder();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Builder {
		
		private StringCollectionFilter stringCollectionFilter = new StringCollectionFilter();
		
		public StringCollectionFilter build() {
			return stringCollectionFilter;
		}
		
		public Builder add(@NonNull Predicate<String> predicate) {
			stringCollectionFilter.add(predicate);
			return this;
		}
		
		public Builder regex(@NonNull String regex) {
			stringCollectionFilter.add(new RegexPredicate(regex));
			return this;
		}

		public Builder exactly(int n, char c) {
			stringCollectionFilter.add(new ExactlyPredicate(n, c));
			return this;
		}

		public Builder atLeast(int n, char c) {
			stringCollectionFilter.add(new AtLeastPredicate(n, c));
			return this;
		}

		public Builder exclude(Collection<Character> chars) {
			if (chars != null && !chars.isEmpty()) {
				stringCollectionFilter.add(new ExcludePredicate(chars));
	       }
			return this;
		}
		
//		public Build hangman(String text, Collection<String> exclude) {
//			Map<Character, Integer> found = 
//		}
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class MotusBuilder {
		
		int motusLength = -1;
		Map<Integer, Character> ok = new HashMap<>();
		Map<Integer, Set<Character>> ko = new HashMap<>();
		Map<Character, Integer> atLeast = new HashMap<>();
		Map<Character, Integer> exactly = new HashMap<>();
		Set<Character> exclude = new HashSet<Character>();

		public StringCollectionFilter build() {
			StringCollectionFilter stringCollectionFilter = new StringCollectionFilter();
			
			if (motusLength != -1) {
				exactly.forEach((c, i) -> stringCollectionFilter.add(new ExactlyPredicate(i, c)));
				stringCollectionFilter.add(new ExcludePredicate(exclude));
				atLeast.forEach((c, i) -> stringCollectionFilter.add(new AtLeastPredicate(i, c)));
				stringCollectionFilter.add(new RegexPredicate(buildRegex()));
			}
			
			return stringCollectionFilter;
		}
		
		private String buildRegex() {
			String regex = "";
			
			for (int i = 0; i < motusLength; i++) {
				Character c = ok.get(i);
				if (c != null) {
					regex += c;
				} else if (ko.get(i) != null && !ko.get(i).isEmpty()) {
					regex += "[^" + String.join("", ko.get(i).stream().map(x -> x.toString()).toList()) + "]";
				} else {
					regex += ".";
				}
			}
			return regex;
		}

		public MotusBuilder motus(@NonNull String word, @NonNull String mask) {
			if (word.length() != mask.length()) {
				throw new RuntimeException("The word and the mask should have the same length : " + word + " / " + mask);
			}
			
			if (motusLength == -1) {
				motusLength = mask.length();
			} else if (motusLength != mask.length()) {
				throw new RuntimeException("Bad motus length");
			}
			
			Map<Character, Integer> currentAtLeast = new HashMap<>();
			
			for (char m : List.of('#', 'o', '.')) {
				for (int i = 0; i < mask.length(); i++) {
					if (mask.charAt(i) == m) {
						switch (mask.charAt(i)) {
							case '#'-> { addOK(i, word.charAt(i)); addAtLeast(currentAtLeast, word.charAt(i)); }
							case 'o'-> { addKO(i, word.charAt(i)); addAtLeast(currentAtLeast, word.charAt(i)); }
							case '.'-> { nothing(currentAtLeast, i, word.charAt(i)); }
							default -> throw new RuntimeException("Unsupported character '" + mask.charAt(i) + "' in the mask");
						}
					}
				}
			}
			
			mergeAtLeast(currentAtLeast);
			
			return this;
		}

		private void mergeAtLeast(Map<Character, Integer> currentAtLeast) {
			atLeast.forEach((c, i) -> {
				Integer currentI = currentAtLeast.get(c);
				if (currentI != null) {
					atLeast.put(c, Math.max(i, currentI));
				}
			});
			
			currentAtLeast.forEach((c, i) -> atLeast.computeIfAbsent(c, x -> i));
		}

		private void nothing(Map<Character, Integer> currentAtLeast, int position, char c) {
			Integer i = atLeast.get(c);
			Integer currentI = currentAtLeast.get(c);
			if (i != null || currentI != null) {
				exactly.putIfAbsent(c, i == null ? currentI : i);
				addKO(position, c);
				atLeast.remove(c);
				currentAtLeast.remove(c);
			} else if (exactly.get(c) == null) {
				exclude.add(c);
			}
		}

		private void addKO(int position, char c) {
			Set<Character> set = ko.computeIfAbsent(position, x -> new HashSet<>());
			set.add(c);
		}

		private void addOK(int position, char c) {
			ok.put(position, c);
		}

		private void addAtLeast(Map<Character, Integer> currentAtLeast, char c) {
			if (exactly.get(c) == null) {
				Integer i = currentAtLeast.computeIfAbsent(c, x -> 0);
				currentAtLeast.put(c, i + 1);
			}
		}
	}

	private static class ExcludePredicate implements Predicate<String> {
		private final Set<Character> chars;
		
		public ExcludePredicate(Collection<Character> chars) {
			this.chars = new HashSet<>(chars);
		}
	
		public boolean test(String s) {
			for (int i = 0; i < s.length(); i++) {
				if (chars.contains(s.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		
		@Override
		public String toString() {
			return "exclude(" + String.join(", ", chars.stream().map(c -> c.toString()).sorted().toList()) + ")";
		}
	}
	
	@RequiredArgsConstructor
	private static class ExactlyPredicate implements Predicate<String> {

		private final int n;
		private final char c;
		
		@Override
		public boolean test(String s) {
			int found = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == c) {
					found++;
				}
			}
			return found == n;
		}

		@Override
		public String toString() {
			return "exactly(" + n + ", '" + c + "')";
		}
	}
	
	@RequiredArgsConstructor
	private static class AtLeastPredicate implements Predicate<String> {

		private final int n;
		private final char c;
		
		@Override
		public boolean test(String s) {
			int found = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == c) {
					found++;
					if (found >= n) {
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public String toString() {
			return "atLeast(" + n + ", '" + c + "')";
		}
	}
	
	private static class RegexPredicate implements Predicate<String> {

		private Pattern pattern;
		
		private RegexPredicate(String regex) {
			pattern = Pattern.compile(regex);
		}
		
		@Override
		public boolean test(String s) {
			return pattern.matcher(s).matches();
		}

		@Override
		public String toString() {
			return "regex(\"" + pattern.toString() + "\")";
		}
	}
}
