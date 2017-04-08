/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.result.error;


public enum AshCommonErrors implements AshErrorDomain {
	UNKNOWN,
	IO,
	RELEASE_ASSERTION,
	;

	@Override
	public AshError.Builder error() {
		return AshError.builder().errorDomain(this);
	}
}
