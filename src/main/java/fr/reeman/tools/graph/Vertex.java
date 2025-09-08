package fr.reeman.tools.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

import lombok.Getter;

@Getter
public class Vertex<V, E extends DoubleSupplier> {

	private V content;
	private List<Edge<V, E>> edges = new ArrayList<>();
	
	protected Vertex(V content) {
		this.content = content;
	}
	
	public Vertex<V, E> add(Edge<V, E> edge) {
		this.edges.add(edge);
		return this;
	}

	public Vertex<V, E> remove(Edge<V, E> edge) {
		this.edges.remove(edge);
		return this;
	}
	
	@Override
	public int hashCode() {
		return content.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		return this.equals(object);
	}
}
