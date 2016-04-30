package org.hisrc.tenet.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayStationNodes extends RailwayBaseNodes<RailwayStationNode, RailwayStationNode.Properties> {

	@JsonCreator
	public RailwayStationNodes(@JsonProperty("features") List<RailwayStationNode> features) {
		super(features);
	}
}
