package fr.reeman.tools.graph;

import java.util.Objects;
import java.util.function.DoubleSupplier;

import lombok.Getter;

@Getter
public class Edge<V, E extends DoubleSupplier> {

	final Vertex<V, E> tail;
	final Vertex<V, E> head;
	final E content;

	protected Edge(Vertex<V, E> tail, Vertex<V, E> head, E content) {
		this.tail = tail;
		this.head = head;
		this.content = content;
	}
	
	public double getValue() {
		return content.getAsDouble();
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, tail, head);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?, ?> other = (Edge<?, ?>) obj;
		return Objects.equals(content, other.content)
				&& Objects.equals(tail, other.tail)
				&& Objects.equals(head, other.head);
	}
}
