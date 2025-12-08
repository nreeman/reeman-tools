package fr.reeman.tools.qtyitm.builder;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import fr.reeman.tools.qtyitm.Comparaison;
import fr.reeman.tools.qtyitm.QuantityItemSet;
import fr.reeman.tools.qtyitm.helper.MarkdownDisplay;

public class QuantityItemSetBuilderTest {

	@Test
	public void test() throws IOException {
		RegexQuantityItemBuilder moxfieldBuilder = new RegexQuantityItemBuilder("(\\d) (.*) \\(.*\\).*", 1, 2);
		RegexQuantityItemBuilder archidektBuilder = new RegexQuantityItemBuilder("(\\d)x (.*) \\(.*\\).*", 1, 2);
		QuantityItemSet set1 = QuantityItemSetBuilder.oneQtyItemByLineFileBuild("Storm 1", "C:\\Users\\nreeman\\Storm1.txt", moxfieldBuilder);
		QuantityItemSet set2 = QuantityItemSetBuilder.oneQtyItemByLineFileBuild("Storm 2", "C:\\Users\\nreeman\\Storm2.txt", archidektBuilder);
		
		MarkdownDisplay markdownDisplay = MarkdownDisplay.builder().zero("-").consoleColor(true).build();
		System.out.println(markdownDisplay.generate(Comparaison.compare(set1, set2)));
		
	}
}
