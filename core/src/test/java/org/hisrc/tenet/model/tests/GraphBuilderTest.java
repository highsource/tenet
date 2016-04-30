package org.hisrc.tenet.model.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hisrc.tenet.graph.GraphBuilder;
import org.hisrc.tenet.model.RailwayBaseNode;
import org.hisrc.tenet.model.RailwayLink;
import org.hisrc.tenet.model.RailwayLinks;
import org.hisrc.tenet.model.RailwayNodes;
import org.hisrc.tenet.model.RailwayStationNode;
import org.hisrc.tenet.model.RailwayStationNodes;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.AllDirectedPaths;
import org.jgrapht.alg.DijkstraShortestPath;
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

		final Set<RailwayBaseNode<?>> ffs = new HashSet<>(nodesByCode.getOrDefault("FF", Collections.emptySet()));
		final Set<RailwayBaseNode<?>> bls = new HashSet<>(nodesByCode.getOrDefault("RK", Collections.emptySet()));

		AllDirectedPaths<RailwayBaseNode<?>, RailwayLink> allDirectedPaths = new AllDirectedPaths<>(graph);

		List<GraphPath<RailwayBaseNode<?>, RailwayLink>> allPaths = allDirectedPaths.getAllPaths(ffs, bls, true, null);

		allPaths.forEach(System.out::println);

	}
}
