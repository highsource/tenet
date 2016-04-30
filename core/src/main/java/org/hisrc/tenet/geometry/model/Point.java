package org.hisrc.tenet.geometry.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Point extends Geometry<double[]> {

	@JsonCreator
	public Point(@JsonProperty("coordinates") double[] coordinates) {
		super(coordinates);
	}
}
