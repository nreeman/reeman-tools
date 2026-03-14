package fr.reeman.tools.comics.bo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TradePaperback implements Comparable<TradePaperback> {
	
	private final TPBTitle tpbTitle;
	private final int number;
	private final String date;
	private final String name;
	private final String url;
	private final boolean owned;
	private List<Issue> issues = new ArrayList<>();

	public TradePaperback add(Issue issue) {
		issues.add(issue);
		return this;
	}

	@Override
	public int compareTo(TradePaperback o) {
		int c = tpbTitle.getTpbTitleName().compareTo(o.tpbTitle.getTpbTitleName());
		
		return c == 0 ? Integer.compare(number, o.number) : c;
	}
}
