/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/4/9
 */

package org.frikadelki.ash.toolset.utils;

import lombok.experimental.UtilityClass;
import sun.misc.DoubleConsts;


@UtilityClass
public final class MathUtils {
	public static boolean isFinite(final double val) {
		return (DoubleConsts.MAX_VALUE >= Math.abs(val));
	}
}
