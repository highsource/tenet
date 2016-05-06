package org.hisrc.tenet.railwaynetwork.model;

import java.util.List;

import org.hisrc.tenet.feature.model.FeatureCollection;
import org.hisrc.tenet.geometry.model.Point;
import org.hisrc.tenet.railwaynetwork.model.RailwayBaseNode.Properties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayBaseNodes<R extends RailwayBaseNode<P>, P extends Properties>
		extends FeatureCollection<R, Point, double[], P> {

	@JsonCreator
	public RailwayBaseNodes(@JsonProperty("features") List<R> features) {
		super(features);
	}

}
