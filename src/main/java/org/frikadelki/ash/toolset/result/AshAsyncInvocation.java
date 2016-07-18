/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.result;


public interface AshAsyncInvocation<Params, ResultData> {
	boolean isRunning();
	void start(final Params params, final AshAsyncResultListener<ResultData> listener);
	void cancel();
}
