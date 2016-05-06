package org.hisrc.tenet.railwaygraph.model;

import org.hisrc.tenet.feature.model.Feature;
import org.hisrc.tenet.geometry.model.MultiLineString;
import org.hisrc.tenet.railwaygraph.model.RailwayTransitionPath.Properties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RailwayTransitionPath extends Feature<MultiLineString, double[][][], Properties> {

	@JsonCreator
	public RailwayTransitionPath(@JsonProperty("geometry") MultiLineString geometry,
			@JsonProperty("properties") Properties properties) {
		super(geometry, properties);
	}

	public static class Properties {

		public String getFoo() {
			return "bar";
		}
	}
}
