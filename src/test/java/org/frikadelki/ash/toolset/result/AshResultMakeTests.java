package org.frikadelki.ash.toolset.result;

import lombok.val;
import org.frikadelki.ash.toolset.result.error.AshCommonErrors;
import org.junit.Assert;
import org.junit.Test;


public final class AshResultMakeTests {
	@Test
	public void testSuccess() {
		val emptySuccessResult = AshResultMake.success();
		Assert.assertTrue(emptySuccessResult.isSuccess());

		val simpleSuccessData = "SuccessStuff";
		val simpleSuccessResult = AshResultMake.success(simpleSuccessData);
		Assert.assertTrue(simpleSuccessResult.isSuccess());
		Assert.assertSame(simpleSuccessData, simpleSuccessResult.getData());
	}

	@Test
	public void testError() {
		val error = AshCommonErrors.UNKNOWN.error().build();
		val errorResult = AshResultMake.error(error);
		Assert.assertFalse(errorResult.isSuccess());
		Assert.assertSame(error, errorResult.getError());
	}
}
