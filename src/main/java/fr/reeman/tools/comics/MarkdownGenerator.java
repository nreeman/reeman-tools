package fr.reeman.tools.comics;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.reeman.tools.comics.book.BookAdapter;
import fr.reeman.tools.comics.book.IssueBookAdapter;
import fr.reeman.tools.comics.book.TradePaperbackBookAdapter;
import lombok.NonNull;

public class MarkdownGenerator {

	public final static String HEADER = """
			# Comics
			
			""";

	public final static String FOOTER = """
			## Events
			
			- [2026 Event Debuts](https://marvel.fandom.com/wiki/Category:2026_Event_Debuts)
				- [Shadows of Tomorrow](https://marvel.fandom.com/wiki/Shadows_of_Tomorrow)
			- [2025 Event Debuts](https://marvel.fandom.com/wiki/Category:2025_Event_Debuts)
				- [Age of Revelation](https://marvel.fandom.com/wiki/Age_of_Revelation)
				- [Imperial](https://marvel.fandom.com/wiki/Imperial)
				- [X-Manhunt](https://marvel.fandom.com/wiki/X-Manhunt)

				https://marvel.fandom.com/wiki/Category:2024_Story_Arc_Debuts
				
				https://marvel.fandom.com/wiki/Armageddon_(Event)
			""";

	public void commandes() {
		commandes(System.out);
	}
	
	public void commandes(@NonNull PrintStream out) {
		out.println(HEADER);
		
		generateWatchList(out);
		generateCalendar(out);
		
		out.println(FOOTER);
	}

	private void generateCalendar(@NonNull PrintStream out) {
		Map<String, List<BookAdapter>> byMonth = getBooksFromDateByMonth();
		byMonth.keySet().stream().sorted().forEach(m -> month(m, byMonth.get(m), out));
	}

	private void month(String month, List<BookAdapter> books, @NonNull PrintStream out) {
		out.println("## " + month);
		out.println();

		Map<String, List<BookAdapter>> byDate = books.stream().collect(Collectors.groupingBy(b -> b.getDate()));
		byDate.keySet().stream().sorted().forEach(k -> date(k, byDate.get(k), out));
		out.println();
	}

	private void date(String date, List<BookAdapter> books, PrintStream out) {
		Map<Boolean, List<BookAdapter>> byOwned = books.stream().collect(Collectors.groupingBy(b -> b.isOwned() || (b.getTradePaperback() != null && b.getTradePaperback().isOwned()) ));
		
		List<BookAdapter> list = byOwned.get(Boolean.TRUE);
		if (list != null && !list.isEmpty()) {
			out.println("- [X] " + date + " - " + String.join(", ", list.stream().map(b -> b.markdown() + (b.isOwned() ? "" : "+")).toList()));
		}
		list = byOwned.get(Boolean.FALSE);
		if (list != null && !list.isEmpty()) {
			out.println("- [ ] " + date + " - " + String.join(", ", list.stream().map(b -> b.markdown()).toList()));
		}
	}

	private Map<String, List<BookAdapter>> getBooksFromDateByMonth() {
		Map<String, List<BookAdapter>> books = Collec
				.getInstance()
				.getIssues(i -> !i.isOwned() && i.getTradePaperback() == null)
				.stream()
				.map(i -> new IssueBookAdapter(i))
				.collect(Collectors.groupingBy(a -> a.getMonth()));

		Map<String, List<BookAdapter>> tpbs = Collec
				.getInstance()
				.getTradePaperbacks(t -> !t.isOwned() && t.getDate() != null)
				.stream()
				.map(t -> new TradePaperbackBookAdapter(t))
				.collect(Collectors.groupingBy(a -> a.getMonth()));

		tpbs.forEach((k, v) -> books.computeIfAbsent(k, x -> new ArrayList<>()).addAll(v));
		
		return books;
	}

	private void generateWatchList(@NonNull PrintStream out) {
		out.println("## Watch list");
		out.println();
		Collec.getInstance()
			.getTradePaperbacks(t -> t.getDate() == null)
			.stream()
			.sorted((t1, t2) -> t1.getIssues().getFirst().getDate().compareTo(t2.getIssues().getFirst().getDate()))
			.forEach(t -> out.println("- [" + t.getTpbTitle().getTpbTitleName() + "](" + t.getTpbTitle().getTitle().getUrl() + ")"));
		out.println();
	}
}
