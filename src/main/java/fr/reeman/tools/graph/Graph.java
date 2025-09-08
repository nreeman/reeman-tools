package fr.reeman.tools.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleSupplier;

import lombok.Getter;

/**
 * Undirected Graph
 * 
 * @param <V>
 * @param <E>
 */
@Getter
public class Graph<V, E extends DoubleSupplier> implements Cloneable {

	Map<V, Vertex<V, E>> vertices = new HashMap<>();
	List<Edge<V, E>> edges = new ArrayList<>();
	
	public Graph<V, E> addVertex(V v) {
		vertices.putIfAbsent(v, new Vertex<>(v));
		return this;
	}
	
	public Graph<V, E> addEdge(V v1, V v2, E e) {
		// Check if the edge allready exists
		Edge<V, E> edge = getLink(v1, v2);
		if (edge != null) {
			throw new RuntimeException("Vertices " + v1 + " and " + v2 + " are already linked by " + edge.getContent());
		}
		
		Vertex<V, E> tail = vertices.computeIfAbsent(v1, k -> new Vertex<>(v1));
		Vertex<V, E> head = vertices.computeIfAbsent(v2, k -> new Vertex<>(v2));
		edge = new Edge<>(tail, head, e);
		edges.add(edge);
		tail.add(edge);
		head.add(new Edge<>(head, tail, e)); // Because we are undirected
		return this;
	}

	public Vertex<V, E> getVertex(V v) {
		return vertices.get(v);
	}
	

	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return Return the edge if it exists, null otherwise
	 */
	private Edge<V, E> getLink(V v1, V v2) {
		Vertex<V, E> tail = vertices.get(v1);
		if (tail == null) {
			return null;
		}
		for (Edge<V, E> edge : tail.getEdges()) {
			if (edge.getHead().getContent().equals(v2)) {
				return edge;
			}
		}
		return null;
	}

	@Override
    public Object clone() {
		Graph<V, E> clone = new Graph<>();
		
		clone.vertices.forEach( (k, v) -> {
			clone.vertices.put(k, v);
		});
		
    	return clone;
    }
}
