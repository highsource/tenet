package org.hisrc.tenet.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.feature.model.Feature;
import org.hisrc.tenet.geometry.model.Point;
import org.hisrc.tenet.model.RailwayBaseNode.Properties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RailwayBaseNode<P extends Properties> extends Feature<Point, double[], P> {

	public RailwayBaseNode(Point geometry, P properties) {
		super(geometry, properties);
	}

	public static class Properties {
		private final String id;
		private final String geographicalName;
		private final String formOfNode;
		private final List<String> spokeStartIds;
		private final List<String> spokeEndIds;

		@JsonCreator
		public Properties(@JsonProperty("id") String id, @JsonProperty("geographicalName") String geographicalName,
				@JsonProperty("formOfNode") String formOfNode,
				@JsonProperty("spokeStartIds") List<String> spokeStartIds,
				@JsonProperty("spokeEndIds") List<String> spokeEndIds) {
			super();
			Validate.notNull(id);
			// Validate.notNull(geographicalName);
			Validate.notNull(formOfNode);
			Validate.noNullElements(spokeStartIds);
			Validate.noNullElements(spokeEndIds);
			this.id = id;
			this.geographicalName = geographicalName;
			this.formOfNode = formOfNode;
			this.spokeStartIds = Collections.unmodifiableList(new ArrayList<>(spokeStartIds));
			this.spokeEndIds = Collections.unmodifiableList(new ArrayList<>(spokeEndIds));
		}

		public String getId() {
			return id;
		}

		public String getGeographicalName() {
			return geographicalName;
		}

		public String getFormOfNode() {
			return formOfNode;
		}

		public List<String> getSpokeStartIds() {
			return spokeStartIds;
		}

		public List<String> getSpokeEndIds() {
			return spokeEndIds;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, geographicalName, formOfNode, spokeStartIds, spokeEndIds);
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
			return Objects.equals(this.id, that.id) && Objects.equals(this.geographicalName, that.geographicalName)
					&& Objects.equals(this.formOfNode, that.formOfNode)
					&& Objects.equals(this.spokeStartIds, that.spokeStartIds)
					&& Objects.equals(this.spokeEndIds, that.spokeEndIds);
		}

	}
}
