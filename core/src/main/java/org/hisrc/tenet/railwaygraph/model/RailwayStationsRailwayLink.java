package org.hisrc.tenet.railwaygraph.model;

import org.hisrc.tenet.geometry.model.LineString;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;

public class RailwayStationsRailwayLink extends RailwayLink {

	public RailwayStationsRailwayLink(RailwayStationsNode node, Properties properties) {
		super(new LineString(
				new double[][] { node.getGeometry().getCoordinates(), node.getGeometry().getCoordinates() }),
				properties);
	}
}
