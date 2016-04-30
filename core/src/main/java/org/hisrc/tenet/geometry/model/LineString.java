package org.hisrc.tenet.geometry.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LineString extends Geometry<double[][]> {

	@JsonCreator
	public LineString(@JsonProperty("coordinates") double[][] coordinates) {
		super(coordinates);
	}
}
