package fr.reeman.tools.qtyitm.section;

import java.util.Objects;

import fr.reeman.tools.qtyitm.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class SectionTest {
	
	@RequiredArgsConstructor
	private enum RiftboundCardSection implements Section {
		LEGEND("Legend"),
		SIGNATURE("Signature"),
		CHAMPION("Champion"),
		UNIT("Unit"),
		SPELL("Spell"),
		GEAR("Gear"),
		BATTLEFIELD("Battlefield"),
		RUNE("Rune"),
		SIDEBOARD("Sideboard")
		;
		
		@Getter
		private final String label;
	}

	@Getter
	@RequiredArgsConstructor
	private class RiftboundCardItem implements ItemWithSection {
		
		private final String code;
		private final String name;
		private final RiftboundCardSection section;

		@Override
		public int hashCode() {
			return  Objects.hash(code, section);
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}
			
			if (object == null || getClass() != object.getClass()) {
				return false;
			}
			
			RiftboundCardItem other = (RiftboundCardItem) object;
			return Objects.equals(code, other.code) && section == other.section;
		}
		
		@Override
		public int compareTo(Item item) {
			if (!(item instanceof RiftboundCardItem rcItem)) {
				throw new IllegalArgumentException("Argument must be of type RiftboundCardItem");
			}
			
			return section.compareTo(rcItem.getSection()) == 0 ? name.compareTo(rcItem.getName()) : section.compareTo(rcItem.getSection());
		}
	}

}
