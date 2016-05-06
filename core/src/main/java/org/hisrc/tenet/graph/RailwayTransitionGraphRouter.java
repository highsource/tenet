
package org.hisrc.tenet.graph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.jgrapht.RailwayLinkTransitionAStarAdmissibleHeuristic;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.AStarShortestPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.graph.GraphPathImpl;

public class RailwayTransitionGraphRouter {

	private final AStarAdmissibleHeuristic<RailwayLink> heuristic = RailwayLinkTransitionAStarAdmissibleHeuristic.INSTANCE;
	private final DirectedGraph<RailwayLink, RailwayLinkTransition> graph;

	public RailwayTransitionGraphRouter(DirectedGraph<RailwayLink, RailwayLinkTransition> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	public GraphPath<RailwayLink, RailwayLinkTransition> route(List<Set<RailwayLink>> links) {
		Validate.isTrue(links.size() >= 2);

		Set<RailwayLink> preLastLinks = links.get(links.size() - 2);
		Set<RailwayLink> lastLinks = links.get(links.size() - 1);
		List<GraphPath<RailwayLink, RailwayLinkTransition>> currentPaths = route(preLastLinks, lastLinks);

		for (int index = links.size() - 3; index >= 0; index--) {
			final Set<RailwayLink> currentLinks = links.get(index);
			System.out.println(MessageFormat.format("Number of current paths [{0}].", currentPaths.size()));
			System.out.println(MessageFormat.format("Number of next links [{0}].", currentLinks.size()));
			currentPaths = route(currentLinks, currentPaths);
		}

		return currentPaths.isEmpty() ? null : currentPaths.get(0);
	}

	private List<GraphPath<RailwayLink, RailwayLinkTransition>> route(Set<RailwayLink> startLinks,
			Set<RailwayLink> endLinks) {
		final Set<GraphPath<RailwayLink, RailwayLinkTransition>> paths = new TreeSet<>(
				Comparator.comparingDouble(p -> p.getWeight()));
		endLinks.stream().map(endLink -> route(startLinks, endLink)).filter(Objects::nonNull).forEach(paths::add);
		return new ArrayList<>(paths);
	}

	private List<GraphPath<RailwayLink, RailwayLinkTransition>> route(Set<RailwayLink> startLinks,
			List<GraphPath<RailwayLink, RailwayLinkTransition>> currentPaths) {
		final Set<GraphPath<RailwayLink, RailwayLinkTransition>> paths = new TreeSet<>(
				Comparator.comparingDouble(p -> p.getWeight()));
		startLinks.stream().map(startLink -> route(startLink, currentPaths)).filter(Objects::nonNull)
				.forEach(paths::add);
		return new ArrayList<>(paths);
	}

	private GraphPath<RailwayLink, RailwayLinkTransition> route(RailwayLink startLink,
			List<GraphPath<RailwayLink, RailwayLinkTransition>> currentPaths) {
		return currentPaths.stream().map(currentPath -> route(startLink, currentPath)).filter(Objects::nonNull)
				.min(Comparator.comparingDouble(p -> p.getWeight())).orElse(null);
	}

	private GraphPath<RailwayLink, RailwayLinkTransition> route(RailwayLink startLink,
			GraphPath<RailwayLink, RailwayLinkTransition> currentPath) {
		final GraphPath<RailwayLink, RailwayLinkTransition> precedingPath = route(startLink,
				currentPath.getStartVertex());
		if (precedingPath == null) {
			return null;
		} else {
			return new GraphPathImpl<RailwayLink, RailwayLinkTransition>(graph, precedingPath.getStartVertex(),
					currentPath.getEndVertex(),
					Stream.concat(precedingPath.getEdgeList().stream(), currentPath.getEdgeList().stream())
							.collect(Collectors.toList()),
					precedingPath.getWeight() + currentPath.getWeight());
		}
	}

	private GraphPath<RailwayLink, RailwayLinkTransition> route(Set<RailwayLink> startLinks, RailwayLink endLink) {
		final Set<GraphPath<RailwayLink, RailwayLinkTransition>> paths = new TreeSet<>(
				Comparator.comparingDouble(p -> p.getWeight()));
		return startLinks.stream().map(startLink -> route(startLink, endLink)).filter(Objects::nonNull)
				.min(Comparator.comparingDouble(p -> p.getWeight())).orElse(null);
	}

	public GraphPath<RailwayLink, RailwayLinkTransition> route(RailwayLink startLink, RailwayLink endLink) {
		return new AStarShortestPath<>(graph).getShortestPath(startLink, endLink, heuristic);
	}

}
