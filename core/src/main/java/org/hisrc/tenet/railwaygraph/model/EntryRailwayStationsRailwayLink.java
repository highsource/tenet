package org.hisrc.tenet.railwaygraph.model;

public class EntryRailwayStationsRailwayLink extends RailwayStationsRailwayLink {

	public EntryRailwayStationsRailwayLink(RailwayStationsNode node) {
		super(node, new Properties(node.getProperties().getRailwayStationCode(), "",
				node.getProperties().getRailwayStationCode(), "", "", "", ""));
	}

}
