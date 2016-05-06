package org.hisrc.tenet.stop.graph;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.railwaygraph.graph.RailwayStationNodeRouterBuilder;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.model.RailwayNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode;
import org.hisrc.tenet.stop.model.Stop;

public class StopRouterBuilder {

	private Map<String, Stop> stopsById = new HashMap<>();
	private RailwayStationNodeRouterBuilder railwayStationNodeRouterBuilder = new RailwayStationNodeRouterBuilder();

	public StopRouterBuilder addStop(Stop stop) {
		Validate.notNull(stop);
		stopsById.put(stop.getProperties().getId(), stop);
		return this;
	}

	public StopRouterBuilder addRailwayNode(RailwayNode node) {
		railwayStationNodeRouterBuilder.addRailwayNode(node);
		return this;
	}

	public StopRouterBuilder addRailwayStationNode(RailwayStationNode node) {
		railwayStationNodeRouterBuilder.addRailwayStationNode(node);
		return this;
	}

	public StopRouterBuilder addRailwayLink(RailwayLink link) {
		railwayStationNodeRouterBuilder.addRailwayLink(link);
		return this;
	}

	public StopRouter build() {
		return new StopRouter(stopsById, railwayStationNodeRouterBuilder.build());
	}
}
