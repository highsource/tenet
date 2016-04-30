package org.hisrc.tenet.geometry.model;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LineString extends Geometry<double[][]> {

	@JsonCreator
	public LineString(@JsonProperty("coordinates") double[][] coordinates) {
		super(coordinates);
	}

	public LineString reverse() {
		final double[][] reversedCoordinates = ArrayUtils.clone(getCoordinates());
		ArrayUtils.reverse(reversedCoordinates);
		return new LineString(reversedCoordinates);
	}
}
