
package org.hisrc.tenet.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.jgrapht.RailwayLinkTransitionAStarAdmissibleHeuristic;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.AStarShortestPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;

public class RailwayTransitionGraphRouter {

	private final AStarAdmissibleHeuristic<RailwayLink> heuristic = RailwayLinkTransitionAStarAdmissibleHeuristic.INSTANCE;
	private final DirectedGraph<RailwayLink, RailwayLinkTransition> graph;

	public RailwayTransitionGraphRouter(DirectedGraph<RailwayLink, RailwayLinkTransition> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	public List<GraphPath<RailwayLink, RailwayLinkTransition>> route(Set<RailwayLink> startLinks,
			Set<RailwayLink> endLinks) {
		final Set<GraphPath<RailwayLink, RailwayLinkTransition>> paths = new TreeSet<>(
				Comparator.comparingDouble(p -> p.getWeight()));
		startLinks.stream().forEach(startLink -> {
			endLinks.stream().map(endLink -> route(startLink, endLink)).forEach(paths::add);
			;
		});
		return new ArrayList<>(paths);
	}

	public GraphPath<RailwayLink, RailwayLinkTransition> route(RailwayLink startLink, RailwayLink endLink) {
		return new AStarShortestPath<>(graph).getShortestPath(startLink, endLink, heuristic);
	}

}
