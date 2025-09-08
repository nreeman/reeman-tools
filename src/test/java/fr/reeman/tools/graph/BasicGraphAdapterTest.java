package fr.reeman.tools.graph;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.Test;

import fr.reeman.tools.graph.BasicGraphAdapter.NumberToDoubleSupplier;

public class BasicGraphAdapterTest {

	public enum COLOR {
		BLUE,
		BLACK,
		GREEN,
		ORANGE,
		PINK,
		RED,
		WHITE,
		YELLOW;
	}
	
	@Test
	public void test() {
		BasicGraphAdapter<Integer> g = new BasicGraphAdapter<>();

		g.addEdge("Edinburgh", "London", 4);
		g.addEdge("London", "Amsterdam", 2);
		g.addEdge("London", "Dieppes", 2);
		g.addEdge("Dieppes", "Brest", 2);
		g.addEdge("Bruxelles", "Dieppes", 2);
		g.addEdge("Brest", "Paris", 3);
		g.addEdge("Paris", "Dieppes", 1);
		g.addEdge("Paris", "Bruxelles", 2);
		g.addEdge("Brest", "Pamplona", 4);
		g.addEdge("Pamplona", "Madrid", 3);
		g.addEdge("Madrid", "Lisboa", 3);
		g.addEdge("Lisboa", "Cadiz", 2);
		g.addEdge("Cadiz", "Madrid", 3);
		g.addEdge("Madrid", "Barcelona", 2);
		g.addEdge("Barcelona", "Pamplona", 2);
		g.addEdge("Barcelona", "Marseille", 4);
		g.addEdge("Pamplona", "Marseille", 4);
		g.addEdge("Pamplona", "Paris", 4);
		g.addEdge("Paris", "Marseille", 4);
		g.addEdge("Paris", "Zurich", 3);
		g.addEdge("Marseille", "Zurich", 2);
		g.addEdge("Marseille", "Roma", 4);
		g.addEdge("Paris", "Frankfurt", 3);
		assertEquals(16, g.getVertices().size());
		assertEquals(23, g.getEdges().size());
		
		g.addEdge("Palermo", "Roma", 4);
		g.addEdge("Palermo", "Brindisi", 3);
		g.addEdge("Roma", "Brindisi", 2);
		g.addEdge("Roma", "Venezia", 2);
		g.addEdge("Zurich", "Venezia", 2);
		g.addEdge("Zurich", "Frankfurt", 2);
		g.addEdge("Amsterdam", "Frankfurt", 2);
		g.addEdge("Bruxelles", "Frankfurt", 2);
		assertEquals(19, g.getVertices().size());
		assertEquals(31, g.getEdges().size());

		System.out.println("graph G {");
		Map<String, String> map = new HashMap<>();
		GraphUtils.breadthFirstSearch(g.getGraph(), "Edinburgh", v -> {
			for (Edge edge : v.getEdges()) {
				if (map.get(edge.getHead().getContent()) == null) {
					System.out.println("\t" + edge.getTail().getContent() + "--" + edge.getHead().getContent());
				}
			}
			map.put(v.getContent(), v.getContent());
		});
		System.out.println("}");
		
		System.out.println("\nParcours en largeur\n-------------------");
		AtomicInteger count = new AtomicInteger(0);
		GraphUtils.breadthFirstSearch(g.getGraph(), "Edinburgh", v -> System.out.println(String.format("%2d %s", count.incrementAndGet(), v.getContent())) );
		System.out.println();
		
		System.out.println("\nParcours en profondeur\n----------------------");
		count.set(0);
		GraphUtils.depthFirstSearch(g.getGraph(), "Edinburgh", v -> System.out.println(String.format("%2d %s", count.incrementAndGet(), v.getContent())) );
		System.out.println();
	}
}
