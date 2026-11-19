package fr.reeman.tools.mtg.top8;

import fr.reeman.tools.qtyitm.Item;
import fr.reeman.tools.qtyitm.section.ItemWithSection;
import fr.reeman.tools.qtyitm.section.Section;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Top8CardItem implements ItemWithSection {

	private final String name;
	private final Top8Section section;
	
	@Override
	public int compareTo(Item item) {
		if (!(item instanceof Top8CardItem top8CardItem)) {
			throw new IllegalArgumentException("Argument must be of type Top8CardItem");
		}
		
		return section.compareTo((Top8Section)top8CardItem.getSection()) == 0 ? name.compareTo(top8CardItem.getName()) : section.compareTo((Top8Section)top8CardItem.getSection());
	}

	@Override
	public Section getSection() {
		return section;
	}

}
