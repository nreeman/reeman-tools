package fr.reeman.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class WordsFilter {

	private List<Predicate<String>> filters = new ArrayList<>();
	
	public List<String> filter(List<String> words) {
		return words
				.stream()
				.filter(this::check)
				.toList();
	}
	
	public boolean check(String word) {
		for (Predicate<String> filter : filters) {
			if (!filter.test(word)) {
				return false;
			}
		}
		
		return true;
	}

	public WordsFilter add(Predicate<String> filter) {
		filters.add(filter);
		return this;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Builder {
		
		private WordsFilter wordsFilter = new WordsFilter();
		
		public WordsFilter build() {
			return wordsFilter;
		}
		
		public Builder regex(String regex) {
			wordsFilter.add(new RegexPredicate(regex));
			return this;
		}

		public Builder exactly(int n, char c) {
			wordsFilter.add(new ExactlyPredicate(n, c));
			return this;
		}

		public Builder atLeast(int n, char c) {
			wordsFilter.add(new AtLeastPredicate(n, c));
			return this;
		}

		public Builder exclude(Collection<Character> chars) {
			wordsFilter.add(new ExcludePredicate(chars));
			return this;
		}
	}
	
	@RequiredArgsConstructor
	private static class ExcludePredicate implements Predicate<String> {
		private final Collection<Character> chars;
	
		public boolean test(String s) {
			for (int i = 0; i < s.length(); i++) {
				if (chars.contains(s.charAt(i))) {
					return false;
				}
			}
			return true;
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
		
	}
}
