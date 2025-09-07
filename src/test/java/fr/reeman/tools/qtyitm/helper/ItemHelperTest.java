package fr.reeman.tools.qtyitm.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ItemHelperTest {

	@Test
	public void equals() {
		// Given
		ItemHelper<String> helper1a = new ItemHelper<>("helper1");
		ItemHelper<String> helper1b = new ItemHelper<>("helper1");
		ItemHelper<String> helper2 = new ItemHelper<>("helper2");
//		ItemHelper<String> helperNull = new ItemHelper<>(null);
		ItemHelper<Integer> helperInteger = new ItemHelper<>(1);
		
		// Then
		assertTrue(helper1a.equals(helper1b));
		assertTrue(helper1b.equals(helper1a));
		assertFalse(helper1a.equals(helper2));
		
//		assertFalse(helper1a.equals(helperNull));
//		assertFalse(helperNull.equals(helper2));
//		assertTrue(helperNull.equals(helperNull));
		
		assertFalse(helper1a.equals(helperInteger));
		
		assertFalse(helper1a.equals("toto"));
	}
	
//	public interface Section extends Comparable<Section> {
//		String getLabel();
//	}
//
//	@RequiredArgsConstructor
//	@Getter
//	public class CardTypeSection implements Section {
//
//		private final String label;
//		private final int order;
//
//		@Override
//		public int compareTo(Section o) {
//			return this.order == ((CardTypeSection)o).order ? 0 : (this.order > ((CardTypeSection)o).order ? 1 : -1);
//		}
//	}
//	
////	public static final ItemHelperTest.CardTypeSection SECTION_DEFAULT = new ItemHelperTest.CardTypeSection(null, 0);
//	
//	public void test(Enum e) {
//		
//	}
}
