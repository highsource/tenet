package org.hisrc.tenet.railwaynetwork.model;

import java.util.List;

import org.hisrc.tenet.geometry.model.Point;
import org.hisrc.tenet.railwaynetwork.model.RailwayNode.Properties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RailwayNode extends RailwayBaseNode<Properties> {
	
	@Override
	public boolean canTurn() {
		return false;
	}

	@JsonCreator
	public RailwayNode(@JsonProperty("geometry") Point geometry, @JsonProperty("properties") Properties properties) {
		super(geometry, properties);
	}

	public static class Properties extends RailwayBaseNode.Properties {
		@JsonCreator
		public Properties(@JsonProperty("id") String id, @JsonProperty("geographicalName") String geographicalName,
				@JsonProperty("formOfNode") String formOfNode,
				@JsonProperty("spokeStartIds") List<String> spokeStartIds,
				@JsonProperty("spokeEndIds") List<String> spokeEndIds) {
			super(id, geographicalName, formOfNode, spokeStartIds, spokeEndIds);
		}

		@Override
		public String toString() {
			return "RailwayNode [" + getId()
					+ (getGeographicalName() == null ? "" : (" (" + getGeographicalName() + ")")) + "].";
		}
	}
}
