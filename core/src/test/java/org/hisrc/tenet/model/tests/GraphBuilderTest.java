package org.hisrc.tenet.model.tests;

import java.io.IOException;
import java.io.InputStream;

import org.hisrc.tenet.graph.RailwayStationNodeRouter;
import org.hisrc.tenet.railwaygraph.graph.RailwayStationNodeRouterBuilder;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.model.RailwayLinks;
import org.hisrc.tenet.railwaynetwork.model.RailwayNodes;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNodes;
import org.jgrapht.GraphPath;
import org.junit.Assert;
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

		final RailwayStationNodeRouterBuilder routerBuilder = new RailwayStationNodeRouterBuilder();

		railwayNodes.getFeatures().stream().forEach(routerBuilder::addRailwayNode);
		railwayStationNodes.getFeatures().stream().forEach(routerBuilder::addRailwayStationNode);
		railwayLinks.getFeatures().stream().forEach(routerBuilder::addRailwayLink);

		final RailwayStationNodeRouter railwayStationNodeRouter = routerBuilder.build();

		final GraphPath<RailwayLink, RailwayLinkTransition> path = railwayStationNodeRouter.route("BL", "RK");
		Assert.assertNotNull(path);
		System.out.println(path.getStartVertex() + "->" + path.getEndVertex() + ":" + path.getWeight());
	}
}
