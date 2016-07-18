/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.result;

import org.frikadelki.ash.toolset.result.error.AshError;
import org.frikadelki.ash.toolset.utils.AshAssert;


public final class AshResult<Data> implements AshResultGist {
	private final boolean success;

	private final Data data;
	private final AshError error;

	public AshResult(final Data data) {
		this.success = true;
		this.data = data;
		this.error = null;
	}

	public AshResult(final AshError error) {
		this.success = false;
		this.data = null;
		this.error = error;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	public Data getData() {
		AshAssert.aTrue(success);
		return data;
	}

	@Override
	public AshError getError() {
		AshAssert.aFalse(success);
		return error;
	}
}
