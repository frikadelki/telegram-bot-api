package org.frikadelki.ash.toolset.utils;

import org.junit.Assert;
import org.junit.Test;

public final class MathUtilsTests {
	@Test
	public void testIsFinite() {
		Assert.assertFalse(MathUtils.isFinite(Double.POSITIVE_INFINITY));
		Assert.assertFalse(MathUtils.isFinite(Double.NEGATIVE_INFINITY));
		Assert.assertFalse(MathUtils.isFinite(Double.NaN));

		Assert.assertTrue(MathUtils.isFinite(Double.MIN_VALUE));
		Assert.assertTrue(MathUtils.isFinite(-Double.MIN_VALUE));
		Assert.assertTrue(MathUtils.isFinite(Double.MAX_VALUE));
		Assert.assertTrue(MathUtils.isFinite(-Double.MAX_VALUE));
		Assert.assertTrue(MathUtils.isFinite(0.0));
		Assert.assertTrue(MathUtils.isFinite(-0.0));
		Assert.assertTrue(MathUtils.isFinite(42));
	}
}
