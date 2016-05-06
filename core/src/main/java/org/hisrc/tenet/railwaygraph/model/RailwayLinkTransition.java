package org.hisrc.tenet.railwaygraph.model;

import java.util.Objects;

import org.hisrc.tenet.railwaynetwork.model.RailwayLink;

public abstract class RailwayLinkTransition {

	private final RailwayLink start;
	private final RailwayLink end;

	public RailwayLinkTransition(RailwayLink start, RailwayLink end) {
		this.start = start;
		this.end = end;
	}

	public RailwayLink getStart() {
		return start;
	}

	public RailwayLink getEnd() {
		return end;
	}

	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}

	public double getWeight() {
		return getEnd() == null ? 0 : getEnd().getWeight();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		RailwayLinkTransition that = (RailwayLinkTransition) object;
		return Objects.equals(this.start, that.start) && Objects.equals(this.start, that.start);
	}
}
