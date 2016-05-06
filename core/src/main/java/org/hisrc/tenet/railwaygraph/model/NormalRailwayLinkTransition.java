package org.hisrc.tenet.railwaygraph.model;

import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;

public class NormalRailwayLinkTransition extends RailwayLinkTransition {

	public NormalRailwayLinkTransition(RailwayLink start, RailwayLink end) {
		super(Validate.notNull(start), Validate.notNull(end));
		Validate.isTrue(Objects.equals(start.getProperties().getEndNodeId(), end.getProperties().getStartNodeId()));
	}

	@Override
	public String toString() {

		return "NormalRailwayLinkTransition [" + getStartNodeId() + "->" + getMiddleNodeId() + "->" + getEndNodeId()
				+ ((getStartRailwayLineGeographicalName() == null && getEndRailwayLineGeographicalName() == null) ? ""
						: (" (" + getStartRailwayLineGeographicalName() + " -> " + getEndRailwayLineGeographicalName()
								+ ")"))
				+ "].";
	}

	public String getStartRailwayLineGeographicalName() {
		return getStart().getProperties().getRailwayLineGeographicalName();
	}

	public String getEndRailwayLineGeographicalName() {
		return getEnd().getProperties().getRailwayLineGeographicalName();
	}

	public String getStartNodeId() {
		return getStart().getProperties().getStartNodeId();
	}

	public String getMiddleNodeId() {
		return getStart().getProperties().getEndNodeId();
	}

	public String getEndNodeId() {
		return getEnd().getProperties().getEndNodeId();
	}
}
