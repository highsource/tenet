package org.hisrc.tenet.model;

import java.util.List;

import org.hisrc.tenet.feature.model.FeatureCollection;
import org.hisrc.tenet.geometry.model.Point;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayStationNodes
		extends FeatureCollection<RailwayStationNode, Point, double[], RailwayStationNode.Properties> {

	@JsonCreator
	public RailwayStationNodes(@JsonProperty("features") List<RailwayStationNode> features) {
		super(features);
	}
}
