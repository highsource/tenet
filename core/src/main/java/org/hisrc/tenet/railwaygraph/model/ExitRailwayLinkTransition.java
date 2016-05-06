package org.hisrc.tenet.railwaygraph.model;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;

public class ExitRailwayLinkTransition extends RailwayLinkTransition {

	public ExitRailwayLinkTransition(RailwayLink start) {
		super(Validate.notNull(start), null);
	}

	@Override
	public String toString() {
		return "ExitRailwayLinkTransition [" + getStartNodeId() + "->" + getMiddleNodeId()
				+ (getStartRailwayLineGeographicalName() == null ? ""
						: (" (" + getEndRailwayLineGeographicalName() + ")"))
				+ "].";
	}

	public String getStartRailwayLineGeographicalName() {
		return getStart().getProperties().getRailwayLineGeographicalName();
	}

	public String getEndRailwayLineGeographicalName() {
		return null;
	}

	public String getStartNodeId() {
		return getStart().getProperties().getStartNodeId();
	}

	public String getMiddleNodeId() {
		return getStart().getProperties().getEndNodeId();
	}

	public String getEndNodeId() {
		return null;
	}
}