package fr.reeman.tools.comics.bo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Issue implements Comparable<Issue> {
	private final Title title;
	private final int number;
	private final String date;
	private final boolean owned;
	@Setter private TradePaperback tradePaperback = null;
	@Setter private Event event = null;
	
	@Override
	public int compareTo(Issue o) {
		int c = title.getTitleName().compareTo(o.getTitle().getTitleName());
		
		if (c == 0) {
			c = Integer.compare(title.getVolume(), o.getTitle().getVolume());
			
			c = c == 0 ? Integer.compare(number, o.number) : c;
		}

		return c;
	}
}
