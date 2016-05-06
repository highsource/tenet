package org.hisrc.tenet.railwaynetwork.model;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.geometry.model.Point;
import org.hisrc.tenet.railwaynetwork.model.RailwayStationNode.Properties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayStationNode extends RailwayBaseNode<Properties> {

	@JsonCreator
	public RailwayStationNode(@JsonProperty("geometry") Point geometry,
			@JsonProperty("properties") Properties properties) {
		super(geometry, properties);
	}
	
	@Override
	public boolean canTurn() {
		return true;
	}

	public static class Properties extends RailwayBaseNode.Properties {
		private final String railwayStationCode;

		@JsonCreator
		public Properties(@JsonProperty("id") String id, @JsonProperty("geographicalName") String geographicalName,
				@JsonProperty("formOfNode") String formOfNode,
				@JsonProperty("railwayStationCode") String railwayStationCode,
				@JsonProperty("spokeStartIds") List<String> spokeStartIds,
				@JsonProperty("spokeEndIds") List<String> spokeEndIds) {
			super(id, geographicalName, formOfNode, spokeStartIds, spokeEndIds);
			Validate.notNull(railwayStationCode);
			this.railwayStationCode = railwayStationCode;
		}

		public String getRailwayStationCode() {
			return railwayStationCode;
		}

		@Override
		public String toString() {
			return "RailwayStationNode [" + getId() + ", " + getRailwayStationCode()
					+ (getGeographicalName() == null ? "" : (" (" + getGeographicalName() + ")")) + "].";
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
