package org.hisrc.tenet.railwaygraph.model;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;

public class EntryRailwayLinkTransition extends RailwayLinkTransition {

	public EntryRailwayLinkTransition(RailwayLink end) {
		super(null, Validate.notNull(end));
	}

	@Override
	public String toString() {
		return "EntryRailwayLinkTransition [" + getMiddleNodeId() + "->" + getEndNodeId()
				+ (getEndRailwayLineGeographicalName() == null ? ""
						: (" (" + getEndRailwayLineGeographicalName() + ")"))
				+ "].";
	}

	public String getStartRailwayLineGeographicalName() {
		return null;
	}

	public String getEndRailwayLineGeographicalName() {
		return getEnd().getProperties().getRailwayLineGeographicalName();
	}

	public String getStartNodeId() {
		return null;
	}

	public String getMiddleNodeId() {
		return getEnd().getProperties().getStartNodeId();
	}

	public String getEndNodeId() {
		return getEnd().getProperties().getEndNodeId();
	}
}
