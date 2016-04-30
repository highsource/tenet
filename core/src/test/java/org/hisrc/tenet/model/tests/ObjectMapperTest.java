package org.hisrc.tenet.model.tests;

import java.io.IOException;
import java.io.InputStream;

import org.hisrc.tenet.model.RailwayLinks;
import org.hisrc.tenet.model.RailwayNodes;
import org.hisrc.tenet.model.RailwayStationNodes;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperTest {

	@Test
	public void deserializesRailwayLinks() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try (InputStream is = getClass().getResourceAsStream("/railwayLinks.geojson")) {
			RailwayLinks railwayLinks = mapper.readValue(is, RailwayLinks.class);
			System.out.println("Read [" + railwayLinks.getFeatures().size() + "] railway links.");
		}
	}

	@Test
	public void deserializesRailwayNodes() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try (InputStream is = getClass().getResourceAsStream("/railwayNodes.geojson")) {
			RailwayNodes railwayNodes = mapper.readValue(is, RailwayNodes.class);
			System.out.println("Read [" + railwayNodes.getFeatures().size() + "] railway nodes.");
		}
	}

	@Test
	public void deserializesRailwayStationNodes() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try (InputStream is = getClass().getResourceAsStream("/railwayStationNodes.geojson")) {
			RailwayStationNodes railwayStationNodes = mapper.readValue(is, RailwayStationNodes.class);
			System.out.println("Read [" + railwayStationNodes.getFeatures().size() + "] railway station nodes.");
		}
	}
}
