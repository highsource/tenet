package org.hisrc.tenet.railwaynetwork.serialization.tests;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode;
import org.hisrc.tenet.railwaynetwork.serialization.RailwayStationNodesDeserializer;
import org.junit.Assert;
import org.junit.Test;

public class RailwayStationNodesDeserializerTest {

	@Test
	public void deserializesRailwayStationNodes() throws IOException {
		final Set<RailwayStationNode> items = new RailwayStationNodesDeserializer(
				getClass().getResourceAsStream("/railwayStationNodes.geojson")).get().collect(Collectors.toSet());
		Assert.assertFalse(items.isEmpty());
	}
}
