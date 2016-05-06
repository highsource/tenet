package org.hisrc.tenet.stop.graph.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hisrc.tenet.geometry.model.LineString;
import org.hisrc.tenet.geometry.model.MultiLineString;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaygraph.model.RailwayTransitionPath;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.serialization.RailwayLinksDeserializer;
import org.hisrc.tenet.railwaynetwork.serialization.RailwayNodesDeserializer;
import org.hisrc.tenet.railwaynetwork.serialization.RailwayStationNodesDeserializer;
import org.hisrc.tenet.stop.graph.StopRouter;
import org.hisrc.tenet.stop.graph.StopRouterBuilder;
import org.hisrc.tenet.stop.serialization.StopDeserializer;
import org.jgrapht.GraphPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class StopRouterTest {

	private StopRouter router;

	@Before
	public void initializeRoiter() {
		final StopRouterBuilder stopRouterBuilder = new StopRouterBuilder();

		new RailwayNodesDeserializer(getClass().getResourceAsStream("/railwayNodes.geojson")).get()
				.forEach(stopRouterBuilder::addRailwayNode);
		new RailwayStationNodesDeserializer(getClass().getResourceAsStream("/railwayStationNodes.geojson")).get()
				.forEach(stopRouterBuilder::addRailwayStationNode);
		new RailwayLinksDeserializer(getClass().getResourceAsStream("/railwayLinks.geojson")).get()
				.forEach(stopRouterBuilder::addRailwayLink);
		new StopDeserializer(getClass().getResourceAsStream("/D_Bahnhof_2016_01_alle.csv")).get()
				.forEach(stopRouterBuilder::addStop);

		router = stopRouterBuilder.build();
	}

	@Test
	public void routesICE_578() throws JsonGenerationException, JsonMappingException, IOException {
		final GraphPath<RailwayLink, RailwayLinkTransition> path = router.route(
				"8000096", "8000244", "8070003",
				"8000105", "8003200", "8000128",
				"8000152", "8002549", "8002548", "8002553");
		Assert.assertNotNull(path);
		System.out.println(path);

		List<RailwayLink> railwayLinks = new ArrayList<>(path.getEdgeList().size() + 1);
		railwayLinks.add(path.getStartVertex());
		path.getEdgeList().stream().map(RailwayLinkTransition::getEnd).forEach(railwayLinks::add);

		double[][][] coordinates = new double[railwayLinks.size()][][];
		for (int index = 0; index < railwayLinks.size(); index++) {
			final RailwayLink railwayLink = railwayLinks.get(index);
			final LineString geometry = railwayLink.getGeometry();
			coordinates[index] = geometry.getCoordinates();
		}

		final RailwayTransitionPath p = new RailwayTransitionPath(new MultiLineString(coordinates),
				new RailwayTransitionPath.Properties());

		final ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File("ICE_578.geojson"), p);
	}
	
	@Test
	public void routesICE_885() throws JsonGenerationException, JsonMappingException, IOException {
		final GraphPath<RailwayLink, RailwayLinkTransition> path = router.route(
				"8002553", "8002548", "8002549",
				"8000147", "8000152", "8000128",
				"8003200", "8000115", "8000260",
				"8000284", "8000183", "8000261");
		Assert.assertNotNull(path);
		System.out.println(path);

		List<RailwayLink> railwayLinks = new ArrayList<>(path.getEdgeList().size() + 1);
		railwayLinks.add(path.getStartVertex());
		path.getEdgeList().stream().map(RailwayLinkTransition::getEnd).forEach(railwayLinks::add);

		double[][][] coordinates = new double[railwayLinks.size()][][];
		for (int index = 0; index < railwayLinks.size(); index++) {
			final RailwayLink railwayLink = railwayLinks.get(index);
			final LineString geometry = railwayLink.getGeometry();
			coordinates[index] = geometry.getCoordinates();
		}

		final RailwayTransitionPath p = new RailwayTransitionPath(new MultiLineString(coordinates),
				new RailwayTransitionPath.Properties());

		final ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File("ICE_885.geojson"), p);
	}

	
	
	
	
	
	
	
	
	
	
	
	

	@Test
	public void routesIC_2273() throws JsonGenerationException, JsonMappingException, IOException {
		final GraphPath<RailwayLink, RailwayLinkTransition> path = router.route("8003200", "8000368", "8000129",
				"8005661", "8000337", "8000124", "8000111", "8002042", "8000105", "8000068", "8000031", "8000377",
				"8000156", "8006421", "8000055", "8000191");
		Assert.assertNotNull(path);
		System.out.println(path);

		List<RailwayLink> railwayLinks = new ArrayList<>(path.getEdgeList().size() + 1);
		railwayLinks.add(path.getStartVertex());
		path.getEdgeList().stream().map(RailwayLinkTransition::getEnd).forEach(railwayLinks::add);

		double[][][] coordinates = new double[railwayLinks.size()][][];
		for (int index = 0; index < railwayLinks.size(); index++) {
			final RailwayLink railwayLink = railwayLinks.get(index);
			final LineString geometry = railwayLink.getGeometry();
			coordinates[index] = geometry.getCoordinates();
		}

		final RailwayTransitionPath p = new RailwayTransitionPath(new MultiLineString(coordinates),
				new RailwayTransitionPath.Properties());

		final ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File("out.geojson"), p);
	}

	@Test
	public void routesBL_RK() throws JsonGenerationException, JsonMappingException, IOException {
		String startStopId = "8098160";
		String endStopId = "8000191";

		final GraphPath<RailwayLink, RailwayLinkTransition> path = router.route(startStopId, endStopId);
		Assert.assertNotNull(path);

		List<RailwayLink> railwayLinks = new ArrayList<>(path.getEdgeList().size() + 1);
		railwayLinks.add(path.getStartVertex());
		path.getEdgeList().stream().map(RailwayLinkTransition::getEnd).forEach(railwayLinks::add);

		double[][][] coordinates = new double[railwayLinks.size()][][];
		for (int index = 0; index < railwayLinks.size(); index++) {
			final RailwayLink railwayLink = railwayLinks.get(index);
			final LineString geometry = railwayLink.getGeometry();
			coordinates[index] = geometry.getCoordinates();
		}

		final RailwayTransitionPath p = new RailwayTransitionPath(new MultiLineString(coordinates),
				new RailwayTransitionPath.Properties());

		final ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File("IC_2273.geojson"), p);

	}

}
