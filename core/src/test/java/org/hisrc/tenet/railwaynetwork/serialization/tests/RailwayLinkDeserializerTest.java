package org.hisrc.tenet.railwaynetwork.serialization.tests;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.serialization.RailwayLinksDeserializer;
import org.junit.Assert;
import org.junit.Test;

public class RailwayLinkDeserializerTest {

	@Test
	public void deserializesRailwayLinks() throws IOException {
		final Set<RailwayLink> items = new RailwayLinksDeserializer(
				getClass().getResourceAsStream("/railwayLinks.geojson")).get().collect(Collectors.toSet());
		Assert.assertFalse(items.isEmpty());
	}
}
