package org.hisrc.tenet.railwaynetwork.serialization;

import java.io.InputStream;

import org.hisrc.tenet.base.serialization.JacksonJsonFeatureDeserializer;
import org.hisrc.tenet.geometry.model.Point;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode.Properties;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNodes;

public class RailwayStationNodesDeserializer
		extends JacksonJsonFeatureDeserializer<RailwayStationNodes, RailwayStationNode, Point, double[], Properties> {

	public RailwayStationNodesDeserializer(InputStream inputStream) {
		super(RailwayStationNodes.class, inputStream);
	}
}
