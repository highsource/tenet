package org.hisrc.tenet.model;

import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.feature.model.Feature;
import org.hisrc.tenet.geometry.model.LineString;
import org.hisrc.tenet.model.RailwayLink.Properties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayLink extends Feature<LineString, double[][], Properties> {

	@JsonCreator
	public RailwayLink(@JsonProperty("geometry") LineString geometry,
			@JsonProperty("properties") Properties properties) {
		super(geometry, properties);
	}

	public RailwayLink reverse() {
		return new RailwayLink(getGeometry().reverse(), getProperties().reverse());
	}

	public static class Properties {
		private final String id;
		private final String startNodeId;
		private final String endNodeId;
		private final String railwayLineId;
		private final String railwayLineCode;
		private final String railwayLineGeographicalName;
		private final String railwayLinkSequenceId;

		@JsonCreator
		public Properties(@JsonProperty("id") String id, @JsonProperty("startNodeId") String startNodeId,
				@JsonProperty("endNodeId") String endNodeId, @JsonProperty("railwayLineId") String railwayLineId,
				@JsonProperty("railwayLineCode") String railwayLineCode,
				@JsonProperty("railwayLineGeographicalName") String railwayLineGeographicalName,
				@JsonProperty("railwayLinkSequenceId") String railwayLinkSequenceId) {
			super();
			Validate.notNull(id);
			Validate.notNull(startNodeId);
			Validate.notNull(endNodeId);
			Validate.notNull(railwayLineId);
			Validate.notNull(railwayLineCode);
			Validate.notNull(railwayLineGeographicalName);
			Validate.notNull(railwayLinkSequenceId);
			this.id = id;
			this.startNodeId = startNodeId;
			this.endNodeId = endNodeId;
			this.railwayLineId = railwayLineId;
			this.railwayLineCode = railwayLineCode;
			this.railwayLineGeographicalName = railwayLineGeographicalName;
			this.railwayLinkSequenceId = railwayLinkSequenceId;
		}

		public Properties reverse() {
			return new Properties(getId(), getEndNodeId(), getStartNodeId(), getRailwayLineId(), getRailwayLineCode(),
					getRailwayLineGeographicalName(), getRailwayLinkSequenceId());
		}

		public String getId() {
			return id;
		}

		public String getStartNodeId() {
			return startNodeId;
		}

		public String getEndNodeId() {
			return endNodeId;
		}

		public String getRailwayLineId() {
			return railwayLineId;
		}

		public String getRailwayLineCode() {
			return railwayLineCode;
		}

		public String getRailwayLineGeographicalName() {
			return railwayLineGeographicalName;
		}

		public String getRailwayLinkSequenceId() {
			return railwayLinkSequenceId;
		}

		@Override
		public String toString() {
			return "RailwayLink [" + id + ", " + startNodeId + "->" + endNodeId
					+ (railwayLineGeographicalName == null ? "" : (" (" + railwayLineGeographicalName + ")")) + "].";
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, startNodeId, endNodeId, railwayLineId, railwayLineCode, railwayLineGeographicalName,
					railwayLinkSequenceId);
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
			Properties that = (Properties) object;
			return Objects.equals(this.id, that.id) && Objects.equals(this.startNodeId, that.startNodeId)
					&& Objects.equals(this.endNodeId, that.endNodeId)
					&& Objects.equals(this.railwayLineId, that.railwayLineId)
					&& Objects.equals(this.railwayLineCode, that.railwayLineCode)
					&& Objects.equals(this.railwayLineGeographicalName, that.railwayLineGeographicalName)
					&& Objects.equals(this.railwayLinkSequenceId, that.railwayLinkSequenceId);
		}

	}
}
