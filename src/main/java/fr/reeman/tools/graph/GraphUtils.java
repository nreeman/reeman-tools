package fr.reeman.tools.graph;

import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

public class GraphUtils {

	public static <V, E extends DoubleSupplier> void breadthFirstSearch(Graph<V, E> graph, V root, Consumer<Vertex<V, E>> consume) {
		search(graph, root, consume, deque -> deque.removeFirst());
	}
	
	public static <V, E extends DoubleSupplier> void depthFirstSearch(Graph<V, E> graph, V root, Consumer<Vertex<V, E>> consume) {
		search(graph, root, consume, deque -> deque.removeLast());
	}

	private static <V, E extends DoubleSupplier> void search(Graph<V, E> graph, V root, Consumer<Vertex<V, E>> consume, Function<Deque<Vertex<V, E>>, Vertex<V, E>> removeNext) {
		Map<V, Vertex<V, E>> visited = new HashMap<>();
		Deque<Vertex<V, E>> deque = new LinkedList<>();
		
		Vertex<V, E> rootVertex = graph.getVertex(root);
		if (rootVertex != null) {
			deque.addLast(rootVertex);
			visited.put(root, rootVertex);
		}

		while(deque.size() > 0) {
			Vertex<V, E> currentVertex = removeNext.apply(deque);
			consume.accept(currentVertex);
			
			for (Edge<V, E> edge : currentVertex.getEdges()) {
				if (visited.get(edge.getHead().getContent()) == null) {
					Vertex<V, E> next = edge.getHead();
					deque.addLast(next);
					visited.put(next.getContent(), next);
				}
			}
		}
	}

	public static <V, E extends DoubleSupplier> void dijkstra(Graph<V, E> graph, V origin, Comparator<Edge<V, E>> comparator) {
		//BinaryHeap.builder(null, null)
		
	}

	//public static void dijkstraMin(Collection<Node> graph, Node start, BinaryHeap<? extends Node> heap)
	//public static void dijkstra(Collection<Node> graph, Node start, Node, end, BinaryHeap<? extends Node> heap)
	//public static void dijkstraMin(Collection<Node> graph, Node start, Node, end, BinaryHeap<? extends Node> heap)
}
