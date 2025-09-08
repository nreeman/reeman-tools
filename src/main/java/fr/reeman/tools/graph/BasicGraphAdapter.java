package fr.reeman.tools.graph;

import java.util.List;
import java.util.Map;
import java.util.function.DoubleSupplier;

import lombok.Getter;
import lombok.NonNull;

public class BasicGraphAdapter<E extends Number> implements Cloneable {

	@Getter
	private Graph<String, NumberToDoubleSupplier> graph = new Graph<>();
	
	public class NumberToDoubleSupplier implements DoubleSupplier {
		private Number number;
		
		private NumberToDoubleSupplier(@NonNull Number number) { this.number = number; }

		@Override
		public double getAsDouble() { return number.doubleValue(); }
	}
	
	public BasicGraphAdapter<E> addVertex(String vertex) {
		graph.addVertex(vertex);
		return this;
	}
	
	public BasicGraphAdapter<E> addEdge(String left, String right, E e) {
		graph.addEdge(left, right, new NumberToDoubleSupplier(e));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public Object clone() {
		BasicGraphAdapter<E> clone = new BasicGraphAdapter<>();
		clone.graph = (Graph<String, NumberToDoubleSupplier>)this.graph.clone();
		return clone;
	}
	
	public Map<String, Vertex<String, NumberToDoubleSupplier>> getVertices() {
		return graph.getVertices();
	}
	
	public List<Edge<String, NumberToDoubleSupplier>> getEdges() {
		return graph.getEdges();
	}

}
