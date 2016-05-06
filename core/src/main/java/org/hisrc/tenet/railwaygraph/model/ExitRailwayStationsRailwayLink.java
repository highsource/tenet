package org.hisrc.tenet.railwaygraph.model;

public class ExitRailwayStationsRailwayLink extends RailwayStationsRailwayLink {

	public ExitRailwayStationsRailwayLink(RailwayStationsNode node) {
		super(node, new Properties(node.getProperties().getRailwayStationCode(),
				node.getProperties().getRailwayStationCode(), "", "", "", "", ""));
	}

}
