package fr.reeman.tools.graph;

import java.util.function.DoubleSupplier;

/**
 * TODO Draft
 * 
 * @param <V>
 * @param <E>
 */
public class Digraph<V, E extends DoubleSupplier> extends Graph<V, E> {

	@Override
	public Graph<V, E> addEdge(V v1, V v2, E e) {
		Vertex<V, E> tail = vertices.computeIfAbsent(v1, k -> new Vertex<>(v1));
		Vertex<V, E> head = vertices.computeIfAbsent(v2, k -> new Vertex<>(v2));
		Edge<V, E> edge = new Edge<>(tail, head, e);
		edges.add(edge);
		tail.add(edge);
		head.add(edge);
		return this;
	}
}
