package org.hisrc.tenet.feature.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.geometry.model.Geometry;

public abstract class FeatureCollection<F extends Feature<G, C, P>, G extends Geometry<C>, C, P> {

	private final List<F> features;

	public FeatureCollection(List<F> features) {
		Validate.noNullElements(features);
		this.features = Collections.unmodifiableList(new ArrayList<>(features));
	}

	public List<F> getFeatures() {
		return features;
	}
}
