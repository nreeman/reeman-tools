package fr.reeman.tools.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class CollectionFilter<T> {

	private List<Predicate<T>> filters = new ArrayList<>();

	public List<T> filter(Collection<T> ts) {
		return ts
				.stream()
				.filter(this::check)
				.toList();
	}

	public boolean check(T t) {
		for (Predicate<T> filter : filters) {
			if (!filter.test(t)) {
				return false;
			}
		}
		
		return true;
	}

	public CollectionFilter<T> add(Predicate<T> filter) {
		filters.add(filter);
		return this;
	}
	
	@Override
	public String toString() {
		return "CollectionFilter:[" + String.join("; ", filters.stream().map(f -> f.toString()).toList()) + "]";
	}
}
