package org.hisrc.tenet.railwaynetwork.serialization;

import java.io.InputStream;

import org.hisrc.tenet.base.serialization.JacksonFeatureDeserializer;
import org.hisrc.tenet.geometry.model.Point;
import org.hisrc.tenet.railwaynetwork.model.RailwayNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayNode.Properties;
import org.hisrc.tenet.railwaynetwork.model.RailwayNodes;

public class RailwayNodesDeserializer
		extends JacksonFeatureDeserializer<RailwayNodes, RailwayNode, Point, double[], Properties> {

	public RailwayNodesDeserializer(InputStream inputStream) {
		super(RailwayNodes.class, inputStream);
	}
}