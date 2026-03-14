package fr.reeman.tools.comics.book;

import fr.reeman.tools.comics.bo.Issue;
import fr.reeman.tools.comics.bo.TradePaperback;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueBookAdapter implements BookAdapter {
	
	private final Issue issue;

	@Override
	public String getMonth() {
		return issue.getDate().substring(0, issue.getDate().length() - 3);
	}
	
	@Override
	public String getDate() {
		return issue.getDate();
	}
	
	@Override
	public boolean isOwned() {
		return issue.isOwned();
	}

//	@Override
//	public String getName() {
//		return issue.getTitle().getTitleName() + " #" + issue.getNumber();
//	}
//	
//	@Override
//	public String getUrl() {
//		return issue.getTitle().getUrl() + "_" + issue.getNumber();
//	}

	@Override
	public TradePaperback getTradePaperback() {
		return issue.getTradePaperback();
	}
	
	@Override
	public String markdown() {
		return "[" + issue.getTitle().getTitleName() + " Vol " + issue.getTitle().getVolume() + "]"
				+"(" + issue.getTitle().getUrl() + ")"
				+"[ #" + issue.getNumber() + "]"
				+"(" + issue.getTitle().getUrl() + "_" + issue.getNumber() + ")"
				;
	}
}
