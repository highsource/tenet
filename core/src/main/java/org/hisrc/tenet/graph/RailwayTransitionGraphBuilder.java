package org.hisrc.tenet.graph;

import java.util.Set;
import java.util.function.BiPredicate;

import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayBaseNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.jgrapht.DirectedGraph;

public class RailwayTransitionGraphBuilder {

	public DirectedGraph<RailwayLink, RailwayLinkTransition> buildGraph(
			DirectedGraph<RailwayBaseNode<?>, RailwayLink> graph, BiPredicate<RailwayLink, RailwayLink> predicate) {

		final DirectedGraph<RailwayLink, RailwayLinkTransition> resultingGraph = new RailwayTransitionGraph(NormalRailwayLinkTransitionEdgeFactory.INSTANCE);

		graph.edgeSet().stream().forEach(resultingGraph::addVertex);

		graph.vertexSet().stream().forEach(node -> {
			final Set<RailwayLink> incomingEdges = graph.incomingEdgesOf(node);
			final Set<RailwayLink> outgoingEdges = graph.outgoingEdgesOf(node);
			incomingEdges.stream().forEach(incomingEdge -> {
				outgoingEdges.forEach(outgoingEdge -> {
					if (predicate.test(incomingEdge, outgoingEdge)) {
						resultingGraph.addEdge(incomingEdge, outgoingEdge);
					}
				});
			});
		});
		return resultingGraph;
	}
}
