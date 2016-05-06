package org.hisrc.tenet.geometry.model;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LineString extends Geometry<double[][]> {

	@JsonCreator
	public LineString(@JsonProperty("coordinates") double[][] coordinates) {
		super(coordinates);
		Validate.isTrue(coordinates.length >= 2);
	}

	public LineString reverse() {
		final double[][] reversedCoordinates = ArrayUtils.clone(getCoordinates());
		ArrayUtils.reverse(reversedCoordinates);
		return new LineString(reversedCoordinates);
	}

	public double[] getFirstPoint() {
		return getCoordinates()[0];
	}

	public double[] getSecondPoint() {
		return getCoordinates()[1];
	}

	public double[] getPreLastPoint() {
		final double[][] coordinates = getCoordinates();
		return getCoordinates()[coordinates.length - 2];
	}

	public double[] getLastPoint() {
		final double[][] coordinates = getCoordinates();
		return coordinates[coordinates.length - 1];
	}
}
