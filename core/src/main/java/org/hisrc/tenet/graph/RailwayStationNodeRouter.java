package org.hisrc.tenet.graph;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.jgrapht.ThrowingEdgeFactory;
import org.hisrc.tenet.railwaygraph.model.EntryRailwayLinkTransition;
import org.hisrc.tenet.railwaygraph.model.EntryRailwayStationsRailwayLink;
import org.hisrc.tenet.railwaygraph.model.ExitRailwayLinkTransition;
import org.hisrc.tenet.railwaygraph.model.ExitRailwayStationsRailwayLink;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaygraph.model.RailwayStationsNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayBaseNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DirectedGraphUnion;
import org.jgrapht.util.WeightCombiner;

public class RailwayStationNodeRouter {

	private final Map<String, Set<RailwayStationNode>> railwayStationNodesByRailwayStationCode;
	private final DirectedGraph<RailwayBaseNode<?>, RailwayLink> railwayGraph;
	private final DirectedGraph<RailwayLink, RailwayLinkTransition> railwayTransitionGraph;

	public RailwayStationNodeRouter(Map<String, Set<RailwayStationNode>> railwayStationNodesByRailwayStationCode,
			DirectedGraph<RailwayBaseNode<?>, RailwayLink> railwayGraph,
			DirectedGraph<RailwayLink, RailwayLinkTransition> railwayTransitionGraph) {
		this.railwayStationNodesByRailwayStationCode = railwayStationNodesByRailwayStationCode;
		this.railwayGraph = railwayGraph;
		this.railwayTransitionGraph = railwayTransitionGraph;
	}

	public GraphPath<RailwayLink, RailwayLinkTransition> route(String startRailwayStationCode,
			String endRailwayStationCode) {
		Validate.notNull(startRailwayStationCode);
		Validate.notNull(endRailwayStationCode);
		final Set<RailwayStationNode> startNodes = new HashSet<>(
				railwayStationNodesByRailwayStationCode.getOrDefault(startRailwayStationCode, Collections.emptySet()));
		final Set<RailwayStationNode> endNodes = new HashSet<>(
				railwayStationNodesByRailwayStationCode.getOrDefault(endRailwayStationCode, Collections.emptySet()));
		if (startNodes.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat
					.format("No railway station nodes found for the code [{0}].", startRailwayStationCode));
		}
		if (endNodes.isEmpty()) {
			throw new IllegalArgumentException(
					MessageFormat.format("No railway station nodes found for the code [{0}].", endRailwayStationCode));
		}
		final RailwayLink entryLink = new EntryRailwayStationsRailwayLink(
				new RailwayStationsNode(startRailwayStationCode, startNodes));
		final RailwayLink exitLink = new ExitRailwayStationsRailwayLink(
				new RailwayStationsNode(endRailwayStationCode, endNodes));

		final DirectedGraph<RailwayLink, RailwayLinkTransition> entryAndExitGraph = new DefaultDirectedWeightedGraph<>(
				ThrowingEdgeFactory.instance());

		entryAndExitGraph.addVertex(entryLink);
		entryAndExitGraph.addVertex(exitLink);

		startNodes.stream().map(railwayGraph::outgoingEdgesOf).flatMap(Collection::stream).forEach(outgoingEdge -> {
			entryAndExitGraph.addVertex(outgoingEdge);
			entryAndExitGraph.addEdge(entryLink, outgoingEdge, new EntryRailwayLinkTransition(outgoingEdge));
		});

		endNodes.stream().map(railwayGraph::incomingEdgesOf).flatMap(Collection::stream).forEach(incomingEdge -> {
			entryAndExitGraph.addVertex(incomingEdge);
			entryAndExitGraph.addEdge(incomingEdge, exitLink, new ExitRailwayLinkTransition(incomingEdge));
		});

		final DirectedGraph<RailwayLink, RailwayLinkTransition> graph = new DirectedGraphUnion<RailwayLink, RailwayLinkTransition>(
				railwayTransitionGraph, entryAndExitGraph, WeightCombiner.MAX);

		final RailwayTransitionGraphRouter railwayTransitionGraphRouter = new RailwayTransitionGraphRouter(graph);
		return railwayTransitionGraphRouter.route(entryLink, exitLink);
	}

}
