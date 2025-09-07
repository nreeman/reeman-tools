package fr.reeman.tools.qtyitm.builder;

import java.io.FileNotFoundException;

import org.junit.Test;

import fr.reeman.tools.qtyitm.Comparaison;
import fr.reeman.tools.qtyitm.Item;
import fr.reeman.tools.qtyitm.QuantityItemSet;

public class QuantityItemSetBuilderTest {

	@Test
	public void test() throws FileNotFoundException {
		RegexQuantityItemBuilder moxfieldBuilder = new RegexQuantityItemBuilder("(\\d) (.*) \\(.*\\).*", 1, 2);
		RegexQuantityItemBuilder archidektBuilder = new RegexQuantityItemBuilder("(\\d)x (.*) \\(.*\\).*", 1, 2);
		QuantityItemSet set1 = QuantityItemSetBuilder.oneQtyItemByLineFileBuild("Storm 1", "C:\\Users\\nreeman\\Storm1.txt", moxfieldBuilder);
		QuantityItemSet set2 = QuantityItemSetBuilder.oneQtyItemByLineFileBuild("Storm 2", "C:\\Users\\nreeman\\Storm2.txt", archidektBuilder);
		
//		MarkdownDisplay markdownDisplay = MarkdownDisplay.builder().zero("-").consoleColor(true).build();
//		System.out.println(markdownDisplay.generate(Comparaison.compare(set1, set2)));
		
		Comparaison comparaison = Comparaison.compare(set1, set2);
		for (Item item : comparaison.getSortedItem()) {
			Integer[] occurences = comparaison.getItemsOccurences().get(item);
			boolean found = false;
			for (Integer occurence : occurences) {
				if (occurence == null || occurence == 0) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				System.out.println(item.toString());
			}
		}
		
	}
}
