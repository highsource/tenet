package org.hisrc.tenet.railwaygraph.graph;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.hisrc.tenet.jgrapht.ThrowingEdgeFactory;
import org.hisrc.tenet.railwaynetwork.model.RailwayBaseNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class RailwayGraphBuilder {

	private final DirectedGraph<RailwayBaseNode<?>, RailwayLink> graph = new DefaultDirectedGraph<>(
			ThrowingEdgeFactory.instance());

	private final Map<String, RailwayBaseNode<?>> railwayBaseNodeById = new HashMap<>();

	public RailwayGraphBuilder addNode(RailwayBaseNode<?> node) {
		railwayBaseNodeById.put(node.getProperties().getId(), node);
		graph.addVertex(node);
		return this;
	}

	public RailwayGraphBuilder addRailwayLink(RailwayLink link) {
		final RailwayBaseNode<?> startNode = railwayBaseNodeById.computeIfAbsent(link.getProperties().getStartNodeId(),
				id -> {
					throw new IllegalArgumentException(
							MessageFormat.format("Node with id [{0}] could not be found.", id));
				});
		final RailwayBaseNode<?> endNode = railwayBaseNodeById.computeIfAbsent(link.getProperties().getEndNodeId(),
				id -> {
					throw new IllegalArgumentException(
							MessageFormat.format("Node with id [{0}] could not be found.", id));
				});
		if (link.isInForwardDirection()) {
			graph.addEdge(startNode, endNode, link);
		}
		if (link.isInReverseDirection()) {
			graph.addEdge(endNode, startNode, link.reverse());
		}
		return this;
	}

	public DirectedGraph<RailwayBaseNode<?>, RailwayLink> build() {
		return graph;
	}

}
