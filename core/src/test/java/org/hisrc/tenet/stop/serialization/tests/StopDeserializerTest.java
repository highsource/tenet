package org.hisrc.tenet.stop.serialization.tests;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.hisrc.tenet.stop.model.Stop;
import org.hisrc.tenet.stop.serialization.StopDeserializer;
import org.junit.Assert;
import org.junit.Test;

public class StopDeserializerTest {

	@Test
	public void deserializesStops() throws IOException {
		final Set<Stop> stops = new StopDeserializer(getClass().getResourceAsStream("/D_Bahnhof_2016_01_alle.csv"))
				.get().collect(Collectors.toSet());
		Assert.assertFalse(stops.isEmpty());
	}
}
