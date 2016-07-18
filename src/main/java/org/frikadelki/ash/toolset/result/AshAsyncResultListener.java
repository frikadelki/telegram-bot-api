/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.result;


public interface AshAsyncResultListener<ResultData> {
	void onResult(final AshResult<ResultData> result);
}
