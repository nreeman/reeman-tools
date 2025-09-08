package fr.reeman.tools.graph;

import java.util.function.DoubleSupplier;

public class DiEdge<V, E extends DoubleSupplier> extends Edge<V, E> {

	protected DiEdge(Vertex<V, E> tail, Vertex<V, E> head, E content) {
		super(tail, head, content);
	}

	public Vertex<V, E> getTail() {
		return tail;
	}

	public Vertex<V, E> getHead() {
		return head;
	}
}
