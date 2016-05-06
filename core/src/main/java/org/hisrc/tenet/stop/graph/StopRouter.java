package org.hisrc.tenet.stop.graph;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.graph.RailwayStationNodeRouter;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.stop.model.Stop;
import org.hisrc.tenet.stop.model.Stop.Properties;
import org.jgrapht.GraphPath;

public class StopRouter {

	private final Map<String, Stop> stopsById;
	private final RailwayStationNodeRouter railwayStationNodeRouter;

	public StopRouter(Map<String, Stop> stopsById, RailwayStationNodeRouter railwayStationNodeRouter) {
		super();
		this.stopsById = stopsById;
		this.railwayStationNodeRouter = railwayStationNodeRouter;
	}

	public GraphPath<RailwayLink, RailwayLinkTransition> route(String... stopIds) {
		return route(Arrays.asList(stopIds));
	}

	public GraphPath<RailwayLink, RailwayLinkTransition> route(List<String> stopIds) {
		Validate.noNullElements(stopIds);
		Validate.isTrue(stopIds.size() >= 2);
		final List<String> railwayStationCodes = stopIds.stream().map(stopId -> {
			final Stop stop = stopsById.get(stopId);
			if (stop == null) {
				throw new IllegalArgumentException(
						MessageFormat.format("Stop with id [{0}] could not be found.", stopId));
			}
			return stop;
		}).map(Stop::getProperties).map(Properties::getRailwayStationCode).collect(Collectors.toList());
		return railwayStationNodeRouter.route(railwayStationCodes);
	}

/*	public GraphPath<RailwayLink, RailwayLinkTransition> route(String startStopId, String endStopId) {
		Validate.notNull(startStopId);
		Validate.notNull(endStopId);

		final Stop startStop = stopsById.get(startStopId);
		final Stop endStop = stopsById.get(endStopId);
		if (startStop == null) {
			throw new IllegalArgumentException(
					MessageFormat.format("Stop with id [{0}] could not be found.", startStopId));
		}
		if (endStop == null) {
			throw new IllegalArgumentException(
					MessageFormat.format("Stop with id [{0}] could not be found.", endStopId));
		}

		return railwayStationNodeRouter.route(startStop.getProperties().getRailwayStationCode(),
				endStop.getProperties().getRailwayStationCode());
	}*/

}
