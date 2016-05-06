package org.hisrc.tenet.railwaygraph.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.geometry.model.Point;
import org.hisrc.tenet.railwaygraph.model.RailwayStationsNode.Properties;
import org.hisrc.tenet.railwaynetwork.model.RailwayBaseNode;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode;

public class RailwayStationsNode extends RailwayBaseNode<Properties> {

	public RailwayStationsNode(String railwayStationCode, Collection<RailwayStationNode> railwayStationNodes) {
		super(Point.averageOf(
				railwayStationNodes.stream().map(RailwayStationNode::getGeometry).collect(Collectors.toList())),
				new Properties(railwayStationCode));
	}

	@Override
	public boolean canTurn() {
		return true;
	}

	public static class Properties extends RailwayBaseNode.Properties {
		private final String railwayStationCode;

		public Properties(String railwayStationCode) {
			super(railwayStationCode, null, "pseudoNode", Collections.emptyList(), Collections.emptyList());
			Validate.notNull(railwayStationCode);
			this.railwayStationCode = railwayStationCode;
		}

		public String getRailwayStationCode() {
			return railwayStationCode;
		}

		@Override
		public String toString() {
			return "RailwayStationsNode [" + getRailwayStationCode() + "].";
		}

		@Override
		public int hashCode() {
			return Objects.hash(super.hashCode(), railwayStationCode);
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}
			if (!super.equals(object)) {
				return false;
			}
			if (getClass() != object.getClass()) {
				return false;
			}
			final Properties that = (Properties) object;
			return Objects.equals(this.railwayStationCode, that.railwayStationCode);
		}
	}

}
