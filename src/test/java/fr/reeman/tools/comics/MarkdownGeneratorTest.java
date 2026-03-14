package fr.reeman.tools.comics;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

public class MarkdownGeneratorTest {
	
	@Test
	public void generate() throws ParseException {
		new fr.reeman.tools.comics.MarkdownGenerator().commandes();

	}
}