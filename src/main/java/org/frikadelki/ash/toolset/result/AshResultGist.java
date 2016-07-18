/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.result;

import org.frikadelki.ash.toolset.result.error.AshError;


public interface AshResultGist {
	boolean isSuccess();
	AshError getError();
}
