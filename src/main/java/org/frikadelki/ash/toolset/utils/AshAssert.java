/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.utils;

import org.frikadelki.ash.ASHBuildConfig;


public final class AshAssert {
	public static void aTrue(final boolean condition) {
		if (ASHBuildConfig.DEBUG && !condition) {
			throw new AssertionError();
		}
	}

	public static void aFalse(final boolean condition) {
		if (ASHBuildConfig.DEBUG && condition) {
			throw new AssertionError();
		}
	}

	public static void aNonNull(final Object object) {
		if (ASHBuildConfig.DEBUG && (null == object)) {
			throw new AssertionError();
		}
	}

	public static void fail() {
		fail("");
	}

	public static void fail(final String message) {
		if (ASHBuildConfig.DEBUG) {
			throw new AssertionError(message);
		}
	}
}
