package org.hisrc.tenet.model.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.hisrc.tenet.graph.RailwayStationNodeRouter;
import org.hisrc.tenet.railwaygraph.graph.RouterBuilder;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.model.RailwayLinks;
import org.hisrc.tenet.railwaynetwork.model.RailwayNodes;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNodes;
import org.jgrapht.GraphPath;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GraphBuilderTest {
	@Test
	public void deserializesRailwayLinks() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		final RailwayLinks railwayLinks;
		try (InputStream is = getClass().getResourceAsStream("/railwayLinks.geojson")) {
			railwayLinks = mapper.readValue(is, RailwayLinks.class);
		}
		final RailwayNodes railwayNodes;
		try (InputStream is = getClass().getResourceAsStream("/railwayNodes.geojson")) {
			railwayNodes = mapper.readValue(is, RailwayNodes.class);
		}
		final RailwayStationNodes railwayStationNodes;
		try (InputStream is = getClass().getResourceAsStream("/railwayStationNodes.geojson")) {
			railwayStationNodes = mapper.readValue(is, RailwayStationNodes.class);
		}
		System.out.println("Building");

		final RouterBuilder routerBuilder = new RouterBuilder();

		railwayNodes.getFeatures().stream().forEach(routerBuilder::addRailwayNode);
		railwayStationNodes.getFeatures().stream().forEach(routerBuilder::addRailwayStationNode);
		railwayLinks.getFeatures().stream().forEach(routerBuilder::addRailwayLink);

		final RailwayStationNodeRouter railwayStationNodeRouter = routerBuilder.build();

		GraphPath<RailwayLink, RailwayLinkTransition> path = railwayStationNodeRouter.route("BL", "RK");
		/*
		 * final Map<String, Set<RailwayStationNode>> nodesByCode = new
		 * HashMap<>();
		 * 
		 * railwayStationNodes.getFeatures().forEach(node -> {
		 * nodesByCode.computeIfAbsent(node.getProperties().
		 * getRailwayStationCode(), code -> new HashSet<>()) .add(node); });
		 * 
		 * RailwayGraphBuilder railwayGraphBuilder = new RailwayGraphBuilder();
		 * 
		 * railwayNodes.getFeatures().stream().map(railwayGraphBuilder::addNode)
		 * ;
		 * railwayStationNodes.getFeatures().stream().map(railwayGraphBuilder::
		 * addNode);
		 * railwayLinks.getFeatures().stream().map(railwayGraphBuilder::
		 * addRailwayLink);
		 * 
		 * final DirectedGraph<RailwayBaseNode<?>, RailwayLink> graph =
		 * railwayGraphBuilder.build();
		 * 
		 * final DirectedGraph<RailwayLink, RailwayLinkTransition>
		 * railwayTransitionGraph = new RailwayTransitionGraphBuilder()
		 * .buildGraph(graph, (start, end) -> true);
		 * 
		 * final DirectedGraph<RailwayLink, RailwayLinkTransition>
		 * entryAndExitGraph = new DefaultDirectedWeightedGraph<>(
		 * ThrowingEdgeFactory.instance());
		 * 
		 * final String startRailwayCode = "BL"; final String endRailwayCode =
		 * "RK"; final Set<RailwayStationNode> startNodes = new HashSet<>(
		 * nodesByCode.getOrDefault(startRailwayCode, Collections.emptySet()));
		 * final Set<RailwayStationNode> endNodes = new
		 * HashSet<>(nodesByCode.getOrDefault("RK", Collections.emptySet()));
		 * final RailwayLink entryLink = new EntryRailwayStationsRailwayLink(
		 * new RailwayStationsNode(startRailwayCode, startNodes)); final
		 * RailwayLink exitLink = new ExitRailwayStationsRailwayLink(new
		 * RailwayStationsNode(endRailwayCode, endNodes));
		 * 
		 * entryAndExitGraph.addVertex(entryLink);
		 * entryAndExitGraph.addVertex(exitLink);
		 * 
		 * startNodes.stream().map(graph::outgoingEdgesOf).flatMap(Collection::
		 * stream).forEach(outgoingEdge -> {
		 * entryAndExitGraph.addVertex(outgoingEdge);
		 * entryAndExitGraph.addEdge(entryLink, outgoingEdge, new
		 * EntryRailwayLinkTransition(outgoingEdge)); });
		 * 
		 * endNodes.stream().map(graph::incomingEdgesOf).flatMap(Collection::
		 * stream).forEach(incomingEdge -> {
		 * entryAndExitGraph.addVertex(incomingEdge);
		 * entryAndExitGraph.addEdge(incomingEdge, exitLink, new
		 * ExitRailwayLinkTransition(incomingEdge)); });
		 * 
		 * final Graph<RailwayLink, RailwayLinkTransition> transitionGraph = new
		 * DirectedGraphUnion<RailwayLink, RailwayLinkTransition>(
		 * railwayTransitionGraph, entryAndExitGraph, WeightCombiner.MAX);
		 * 
		 * final AStarShortestPath<RailwayLink, RailwayLinkTransition>
		 * shortestPath = new AStarShortestPath<>( transitionGraph); final
		 * GraphPath<RailwayLink, RailwayLinkTransition> path =
		 * shortestPath.getShortestPath(entryLink, exitLink, new
		 * RailwayLinkTransitionAStarAdmissibleHeuristic());
		 * 
		 * // final DijkstraShortestPath<RailwayLink, RailwayLinkTransition> //
		 * shortestPath = new DijkstraShortestPath<>( // transitionGraph,
		 * entryLink, exitLink);
		 * 
		 * // GraphPath<RailwayLink, RailwayLinkTransition> path = //
		 * shortestPath.getPath();
		 */

		List<RailwayLinkTransition> edgeList = path.getEdgeList();
		List<RailwayLink> links1 = edgeList.subList(0, edgeList.size() - 1).stream().map(RailwayLinkTransition::getEnd)
				.collect(Collectors.toList());

		System.out.println(path.getStartVertex() + "->" + path.getEndVertex() + ":" + path.getWeight());

		Vector2D firstVector = null;
		Vector2D lastVector = null;
		double[] lastCoordinates = null;
		for (RailwayLink edge : links1) {
////			RailwayBaseNode<?> edgeTarget = graph.getEdgeTarget(edge);
//			if (edgeTarget instanceof RailwayStationNode) {
//				RailwayStationNode rsn = (RailwayStationNode) edgeTarget;
//				System.out.println(
//						rsn.getProperties().getRailwayStationCode() + "/" + rsn.getProperties().getGeographicalName());
//			}

			final double[][] coordinates = edge.getGeometry().getCoordinates();
			if (lastVector != null) {
				firstVector = new Vector2D(coordinates[1][0] - coordinates[0][0],
						coordinates[1][1] - coordinates[0][1]);

				double distance = new Vector2D(coordinates[0]).subtract(new Vector2D(lastCoordinates)).getNorm();
				System.out.println("Angle: " + 180 * Vector2D.angle(lastVector, firstVector) / Math.PI);
				System.out.println("Distance: " + distance);
			}
			lastCoordinates = coordinates[coordinates.length - 1];
			lastVector = new Vector2D(coordinates[coordinates.length - 1][0] - coordinates[coordinates.length - 2][0],
					coordinates[coordinates.length - 1][1] - coordinates[coordinates.length - 2][1]);
		}
	}
}
