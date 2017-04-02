/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/3/11
 */

package org.frikadelki.ash.toolset.utils;


import lombok.experimental.UtilityClass;


@UtilityClass
public final class RuntimeAssert {
	public static void aTrue(final boolean condition) {
		if (!condition) {
			fail("Condition is false.");
		}
	}

	public static void aFalse(final boolean condition) {
		if (condition) {
			fail("Condition is true.");
		}
	}

	public static void aNonNull(final Object object) {
		if (null == object) {
			fail("Object is null.");
		}
	}

	public static void fail() {
		fail("");
	}

	public static void fail(final String message) {
		throw new IllegalStateException(message);
	}
}
