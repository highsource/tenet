package org.hisrc.tenet.stop.model;

import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.geometry.model.Point;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "EVA_NR", "DS100", "NAME", "VERKEHR", "LAENGE", "BREITE" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stop {

	private Point geometry;
	private Properties properties;

	@JsonIgnore
	public Point getGeometry() {
		return geometry;
	}

	@JsonIgnore
	public Properties getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return getProperties().toString();
	}

	@JsonCreator
	public Stop(@JsonProperty("EVA_NR") String id, @JsonProperty("DS100") String railwayStationCode,
			@JsonProperty("NAME") String name, @JsonProperty("VERKEHR") String transportType,
			@JsonProperty("LAENGE") Double longitude, @JsonProperty("BREITE") Double latitude) {
		this.geometry = new Point(
				new double[] { Validate.notNull(longitude).doubleValue(), Validate.notNull(latitude).doubleValue() });
		this.properties = new Properties(id, railwayStationCode, name, transportType);
	}

	public static class Properties {
		private String id;
		private String railwayStationCode;
		private String name;
		private String transportType;

		public Properties(String id, String railwayStationCode, String name, String transportType) {
			Validate.notNull(id);
			Validate.notNull(railwayStationCode);
			Validate.notNull(name);
			Validate.notNull(transportType);
			this.id = id;
			this.railwayStationCode = railwayStationCode;
			this.name = name;
			this.transportType = transportType;
		}

		public String getId() {
			return id;
		}

		public String getRailwayStationCode() {
			return railwayStationCode;
		}

		public String getName() {
			return name;
		}

		public String getTransportType() {
			return transportType;
		}

		@Override
		public String toString() {
			return "Stop [" + getId() + ", " + getRailwayStationCode()
					+ (getName() == null ? "" : (" (" + getName() + ")")) + "].";
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, railwayStationCode, name, transportType);
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
			final Properties that = (Properties) object;
			return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name)
					&& Objects.equals(this.railwayStationCode, that.railwayStationCode)
					&& Objects.equals(this.transportType, that.transportType);
		}

	}
}
