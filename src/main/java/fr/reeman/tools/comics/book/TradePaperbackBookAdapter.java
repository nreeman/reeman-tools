package fr.reeman.tools.comics.book;

import fr.reeman.tools.comics.bo.TradePaperback;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TradePaperbackBookAdapter implements BookAdapter {

	private final TradePaperback tradePaperback;
	
	@Override
	public String getMonth() {
		return tradePaperback.getDate().substring(0, tradePaperback.getDate().length() - 3);
	}

	@Override
	public String getDate() {
		return tradePaperback.getDate();
	}

	@Override
	public boolean isOwned() {
		return tradePaperback.isOwned();
	}

//	@Override
//	public String getName() {
//		return tradePaperback.getTpbTitle().getTpbTitleName() + ": " + tradePaperback.getName() + " #" + tradePaperback.getNumber();
//	}
//
//	@Override
//	public String getUrl() {
//		return tradePaperback.getTpbTitle().getUrl() + "_" + tradePaperback.getNumber();
//	}

	@Override
	public TradePaperback getTradePaperback() {
		return null;
	}

	@Override
	public String markdown() {
		return "[" + tradePaperback.getTpbTitle().getTpbTitleName() + "]"
				+"(" + tradePaperback.getTpbTitle().getUrl() + ")"
				+ ": "
				+"[" + tradePaperback.getName() + " #" + tradePaperback.getNumber() + "]"
				+"(" + tradePaperback.getUrl() + ")"
				;
	}
}
