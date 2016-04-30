package org.hisrc.tenet.model;

import java.util.List;

import org.hisrc.tenet.feature.model.FeatureCollection;
import org.hisrc.tenet.geometry.model.LineString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayLinks extends FeatureCollection<RailwayLink, LineString, double[][], RailwayLink.Properties> {

	@JsonCreator
	public RailwayLinks(@JsonProperty("features") List<RailwayLink> features) {
		super(features);
	}

}
