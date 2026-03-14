package fr.reeman.tools;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImmutablePair<L, R> {
	
	private final L left;
	private final R right;

	public static final <L, R> ImmutablePair<L, R> pair(L l, R r) {
		return new ImmutablePair<>(l, r);
	}
	
}
