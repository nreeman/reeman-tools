package fr.reeman.tools.comics.bo;

import java.util.ArrayList;
import java.util.List;

import fr.reeman.tools.comics.enums.TitleName;
import fr.reeman.tools.comics.enums.TitleStatus;
import fr.reeman.tools.comics.enums.TitleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Title {
	
	private final TitleName titleName;
	private final int volume;
	private final TitleType type;
	private final int year;
	private final TitleStatus status;
	private final String url;
	@Setter private List<Issue> issues = new ArrayList<Issue>();
	@Setter private TPBTitle tpbTitle = null;

	public Title add(Issue issue) {
		issues.add(issue);
		return this;
	}
	
	public String getFullName() {
		return titleName + " Vol " + volume;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Title)) {
			return false;
		}
		
		Title t = (Title)object;
		
		return t.titleName == titleName && t.volume == volume;
	}
}
