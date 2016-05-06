package org.hisrc.tenet.railwaynetwork.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayNodes extends RailwayBaseNodes<RailwayNode, RailwayNode.Properties> {

	@JsonCreator
	public RailwayNodes(@JsonProperty("features") List<RailwayNode> features) {
		super(features);
	}

}
