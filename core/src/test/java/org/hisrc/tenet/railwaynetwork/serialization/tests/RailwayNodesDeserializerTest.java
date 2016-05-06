package org.hisrc.tenet.railwaynetwork.serialization.tests;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.hisrc.tenet.railwaynetwork.model.RailwayNode;
import org.hisrc.tenet.railwaynetwork.serialization.RailwayNodesDeserializer;
import org.junit.Assert;
import org.junit.Test;

public class RailwayNodesDeserializerTest {

	@Test
	public void deserializesRailwayNodes() throws IOException {
		final Set<RailwayNode> items = new RailwayNodesDeserializer(
				getClass().getResourceAsStream("/railwayNodes.geojson")).get().collect(Collectors.toSet());
		Assert.assertFalse(items.isEmpty());
	}
}
