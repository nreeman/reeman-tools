package fr.reeman.tools.comics.bo;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Event {

	private final String name;
	private final int year;
	private Collection<Issue> issues = new ArrayList<Issue>();
	
	public Event add(Issue issue) {
		issues.add(issue);
		return this;
	}
}
