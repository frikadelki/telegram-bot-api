/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.runtime.dispatch;

import org.frikadelki.ash.telegram.api.base.TgmUpdate;


public interface TgmUpdateFilter {
	boolean isMatch(TgmUpdate update);

	TgmUpdateFilter EMPTY = new TgmUpdateFilter() {
		@Override public boolean isMatch(final TgmUpdate update) {
			return true;
		}
	};
}
