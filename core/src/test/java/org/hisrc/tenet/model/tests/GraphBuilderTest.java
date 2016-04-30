package org.hisrc.tenet.model.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.hisrc.tenet.graph.GraphBuilder;
import org.hisrc.tenet.model.RailwayBaseNode;
import org.hisrc.tenet.model.RailwayLink;
import org.hisrc.tenet.model.RailwayLinks;
import org.hisrc.tenet.model.RailwayNodes;
import org.hisrc.tenet.model.RailwayStationNode;
import org.hisrc.tenet.model.RailwayStationNodes;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.AStarShortestPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
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

		final Map<String, Set<RailwayStationNode>> nodesByCode = new HashMap<>();

		railwayStationNodes.getFeatures().forEach(node -> {
			nodesByCode.computeIfAbsent(node.getProperties().getRailwayStationCode(), code -> new HashSet<>())
					.add(node);
		});

		List<RailwayBaseNode<?>> nodes = Stream
				.concat(railwayNodes.getFeatures().stream(), railwayStationNodes.getFeatures().stream())
				.collect(Collectors.toList());

		List<RailwayLink> links = railwayLinks.getFeatures();

		DirectedGraph<RailwayBaseNode<?>, RailwayLink> graph = new GraphBuilder().buildGraph(nodes, links);

		final Set<RailwayBaseNode<?>> starts = new HashSet<>(nodesByCode.getOrDefault("BL", Collections.emptySet()));
		final Set<RailwayBaseNode<?>> ends = new HashSet<>(nodesByCode.getOrDefault("RK", Collections.emptySet()));

		final AStarAdmissibleHeuristic<RailwayBaseNode<?>> heuristic = new AStarAdmissibleHeuristic<RailwayBaseNode<?>>() {

			@Override
			public double getCostEstimate(RailwayBaseNode<?> sourceVertex, RailwayBaseNode<?> targetVertex) {
				double[] lonLat0 = sourceVertex.getGeometry().getCoordinates();
				double[] lonLat1 = sourceVertex.getGeometry().getCoordinates();
				double dLon = lonLat1[0] - lonLat0[0];
				double dLat = lonLat1[1] - lonLat0[1];
				return Math.sqrt(dLon * dLon + dLat * dLat);
			}
		};

		List<GraphPath<RailwayBaseNode<?>, RailwayLink>> paths = new ArrayList<>();
		for (RailwayBaseNode<?> start : starts) {
			for (RailwayBaseNode<?> end : ends) {
				AStarShortestPath<RailwayBaseNode<?>, RailwayLink> aStarShortestPath = new AStarShortestPath<>(graph);
				GraphPath<RailwayBaseNode<?>, RailwayLink> path = aStarShortestPath.getShortestPath(start, end,
						heuristic);
				if (path != null) {
					paths.add(path);
				}
			}
		}

		paths.forEach(path -> {
			System.out.println(path.getStartVertex() + "->" + path.getEndVertex() + ":" + path.getWeight());

			Vector2D firstVector = null;
			Vector2D lastVector = null;
			double[] lastCoordinates = null;
			for (RailwayLink edge : path.getEdgeList()) {
				RailwayBaseNode<?> edgeTarget = graph.getEdgeTarget(edge);
				if (edgeTarget instanceof RailwayStationNode) {
					RailwayStationNode rsn = (RailwayStationNode) edgeTarget;
					System.out.println(rsn.getProperties().getRailwayStationCode() + "/"
							+ rsn.getProperties().getGeographicalName());
				}

				final double[][] coordinates = edge.getGeometry().getCoordinates();
				if (lastVector != null) {
					firstVector = new Vector2D(coordinates[1][0] - coordinates[0][0],
							coordinates[1][1] - coordinates[0][1]);
					
					double distance = new Vector2D(coordinates[0]).subtract(new Vector2D(lastCoordinates)).getNorm();
					System.out.println("Angle: " + 180*Vector2D.angle(lastVector, firstVector)/Math.PI);
					System.out.println("Distance: " + distance);
				}
				lastCoordinates = coordinates[coordinates.length - 1];
				lastVector = new Vector2D(
						coordinates[coordinates.length - 1][0] - coordinates[coordinates.length - 2][0],
						coordinates[coordinates.length - 1][1] - coordinates[coordinates.length - 2][1]);
			}
		});
	}
}
