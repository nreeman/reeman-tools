package fr.reeman.tools.comics.bo;

import java.util.ArrayList;
import java.util.List;

import fr.reeman.tools.comics.enums.TPBTitleName;
import fr.reeman.tools.comics.enums.TitleStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class  TPBTitle {

	private final TPBTitleName tpbTitleName;
	private final String url;
	private final Title title;
	private final TitleStatus status;
	private List<TradePaperback> tradePaperbacks = new ArrayList<TradePaperback>();

	@Override
	public String toString() {
		return tpbTitleName.getName();
	}
}
