/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/5/1
 */

package org.frikadelki.ash.toolset.utils;

import lombok.Value;

import java.io.Serializable;


@Value
public class AshLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final double MIN_LATITUDE = -90;
	public static final double MAX_LATITUDE = +90;

	public static double MIN_LONGITUDE = -180;
	public static double MAX_LONGITUDE = +180;

	public static boolean isValidLocation(final double latitude, final double longitude) {
		return isValidLatitude(latitude) && isValidLongitude(longitude);
	}

	public static boolean isValidLatitude(final double latitude) {
		return MathUtils.isFinite(latitude) &&
				MIN_LATITUDE <= latitude && latitude <= MAX_LATITUDE;
	}

	public static boolean isValidLongitude(final double longitude) {
		return MathUtils.isFinite(longitude) &&
				MIN_LONGITUDE <= longitude && longitude <= MAX_LONGITUDE;
	}

	private final double latitude;
	private final double longitude;

	public boolean isValid() {
		return isValidLocation(latitude, longitude);
	}
}
