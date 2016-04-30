package org.hisrc.tenet.graph;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.hisrc.tenet.model.RailwayBaseNode;
import org.hisrc.tenet.model.RailwayLink;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

public class GraphBuilder {

	public DirectedGraph<RailwayBaseNode<?>, RailwayLink> buildGraph(Collection<RailwayBaseNode<?>> nodes, Collection<RailwayLink> links) {
		final Map<String, RailwayBaseNode<?>> nodesMap = nodes.stream()
				.collect(Collectors.toMap(node -> node.getProperties().getId(), node -> node));

		final DirectedGraph<RailwayBaseNode<?>, RailwayLink> graph = new DefaultDirectedGraph<>(new EdgeFactory<RailwayBaseNode<?>, RailwayLink>() {
			@Override
			public RailwayLink createEdge(RailwayBaseNode<?> sourceVertex, RailwayBaseNode<?> targetVertex) {
				throw new UnsupportedOperationException();
			}
		});

		nodes.forEach(graph::addVertex);

		links.forEach(link -> {
			final RailwayBaseNode<?> startNode = nodesMap.computeIfAbsent(link.getProperties().getStartNodeId(), id -> {
				throw new IllegalArgumentException(MessageFormat.format("Node with id [{0}] could not be found.", id));
			});
			final RailwayBaseNode<?> endNode = nodesMap.computeIfAbsent(link.getProperties().getEndNodeId(), id -> {
				throw new IllegalArgumentException(MessageFormat.format("Node with id [{0}] could not be found.", id));
			});
			graph.addEdge(startNode, endNode, link);
			// TODO this must not be necessarily true
			graph.addEdge(endNode, startNode, link.reverse());
		});
		return graph;
	}

}
