package org.hisrc.tenet.graph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

	public GraphPath<RailwayLink, RailwayLinkTransition> route(String... railwayStationCodes) {
		return route(Arrays.asList(railwayStationCodes));
	}

	public GraphPath<RailwayLink, RailwayLinkTransition> route(List<String> railwayStationCodes) {
		Validate.noNullElements(railwayStationCodes);
		Validate.isTrue(railwayStationCodes.size() >= 2);

//		if (railwayStationCodes.size() == 2) {
//			return route(railwayStationCodes.get(0), railwayStationCodes.get(1));
//		} else 
		{
			final List<Set<RailwayLink>> links = new ArrayList<>(railwayStationCodes.size());

			final String firstRailwayStationCode = railwayStationCodes.get(0);

			final Set<RailwayStationNode> firstRailwayStationNodes = railwayStationNodesByRailwayStationCode
					.get(firstRailwayStationCode);
			if (firstRailwayStationNodes.isEmpty()) {
				throw new IllegalArgumentException(MessageFormat
						.format("No railway station nodes found for the code [{0}].", firstRailwayStationCode));
			}

			final Set<RailwayLink> firstRailwayLinks = firstRailwayStationNodes.stream()
					.map(railwayGraph::outgoingEdgesOf).flatMap(Collection::stream).collect(Collectors.toSet());
			links.add(firstRailwayLinks);

			railwayStationCodes.subList(1, railwayStationCodes.size()).forEach(railwayStationCode -> {
				final Set<RailwayStationNode> railwayStationNodes = railwayStationNodesByRailwayStationCode
						.get(railwayStationCode);
				if (railwayStationNodes.isEmpty()) {
					throw new IllegalArgumentException(MessageFormat
							.format("No railway station nodes found for the code [{0}].", railwayStationCode));
				}

				final Set<RailwayLink> railwayLinks = railwayStationNodes.stream().map(railwayGraph::incomingEdgesOf)
						.flatMap(Collection::stream).collect(Collectors.toSet());
				links.add(railwayLinks);
			});

			final RailwayTransitionGraphRouter railwayTransitionGraphRouter = new RailwayTransitionGraphRouter(
					railwayTransitionGraph);
			return railwayTransitionGraphRouter.route(links);
		}

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
