package org.hisrc.tenet.jgrapht;

import org.jgrapht.EdgeFactory;

public class ThrowingEdgeFactory<V, E> implements EdgeFactory<V, E> {

	@Override
	public E createEdge(V sourceVertex, V targetVertex) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings({"rawtypes" })
	public static final EdgeFactory INSTANCE = new ThrowingEdgeFactory();

	@SuppressWarnings("unchecked")
	public static <V1, E1> EdgeFactory<V1, E1> instance() {
		return INSTANCE;
	}
}
