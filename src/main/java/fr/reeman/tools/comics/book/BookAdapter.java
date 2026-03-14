package fr.reeman.tools.comics.book;

import fr.reeman.tools.comics.bo.TradePaperback;

public interface BookAdapter {
	String getMonth();
	String getDate();
	boolean isOwned();
	TradePaperback getTradePaperback();
	String markdown();
}
