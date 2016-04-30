package org.hisrc.tenet.feature.model;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.geometry.model.Geometry;

public class Feature<G extends Geometry<C>, C, P> {

	private G geometry;
	private P properties;

	public Feature(G geometry, P properties) {
		Validate.notNull(geometry);
		Validate.notNull(properties);
		this.geometry = geometry;
		this.properties = properties;
	}

	public G getGeometry() {
		return geometry;
	}

	public P getProperties() {
		return properties;
	}
}
