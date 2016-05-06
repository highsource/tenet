package org.hisrc.tenet.railwaygraph.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

import org.hisrc.tenet.graph.NormalRailwayLinkTransitionEdgeFactory;
import org.hisrc.tenet.graph.RailwayStationNodeRouter;
import org.hisrc.tenet.graph.RailwayTransitionGraph;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayBaseNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.model.RailwayNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode;
import org.jgrapht.DirectedGraph;

public class RouterBuilder {

	private final BiPredicate<RailwayLink, RailwayLink> predicate = RailwayLinkCoordinatesAreCloseEnoughPredicate.INSTANCE
			.and(RailwayLinkAngleIsSuitablePredicate.INSTANCE);

	private final RailwayGraphBuilder railwayGraphBuilder = new RailwayGraphBuilder();

	private Map<String, Set<RailwayStationNode>> railwayStationNodesByRailwayStationCode = new HashMap<>();

	public RouterBuilder() {
	}

	private RouterBuilder addRailwayBaseNode(RailwayBaseNode<?> node) {
		railwayGraphBuilder.addNode(node);
		return this;
	}

	public RouterBuilder addRailwayNode(RailwayNode node) {
		return addRailwayBaseNode(node);
	}

	public RouterBuilder addRailwayStationNode(RailwayStationNode node) {
		railwayStationNodesByRailwayStationCode
				.computeIfAbsent(node.getProperties().getRailwayStationCode(), code -> new HashSet<>()).add(node);
		return addRailwayBaseNode(node);
	}

	public RouterBuilder addRailwayLink(RailwayLink link) {
		railwayGraphBuilder.addRailwayLink(link);
		return this;
	}

	public RailwayStationNodeRouter build() {
		final DirectedGraph<RailwayBaseNode<?>, RailwayLink> railwayGraph = railwayGraphBuilder.build();

		final DirectedGraph<RailwayLink, RailwayLinkTransition> railwayTransitionGraph = new RailwayTransitionGraph(
				NormalRailwayLinkTransitionEdgeFactory.INSTANCE);

		railwayGraph.edgeSet().stream().forEach(railwayTransitionGraph::addVertex);

		railwayGraph.vertexSet().stream().forEach(node -> {
			final Set<RailwayLink> incomingEdges = railwayGraph.incomingEdgesOf(node);
			final Set<RailwayLink> outgoingEdges = railwayGraph.outgoingEdgesOf(node);
			incomingEdges.forEach(incomingEdge -> {
				outgoingEdges.forEach(outgoingEdge -> {
					if (predicate.test(incomingEdge, outgoingEdge)) {
						railwayTransitionGraph.addEdge(incomingEdge, outgoingEdge);
					}
				});
			});
		});

		return new RailwayStationNodeRouter(railwayStationNodesByRailwayStationCode, railwayGraph,
				railwayTransitionGraph);
	}

}
