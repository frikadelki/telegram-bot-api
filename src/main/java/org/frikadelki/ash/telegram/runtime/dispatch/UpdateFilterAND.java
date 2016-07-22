/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.runtime.dispatch;

import lombok.NonNull;
import org.frikadelki.ash.telegram.api.base.TgmUpdate;


public final class UpdateFilterAND implements TgmUpdateFilter {
	private final TgmUpdateFilter[] filters;

	public UpdateFilterAND(@NonNull TgmUpdateFilter... filters) {
		this.filters = filters;
	}

	@Override
	public boolean isMatch(final TgmUpdate update) {
		for( final TgmUpdateFilter filter : filters ) {
			final boolean check = filter.isMatch(update);
			if( !check ) {
				return false;
			}
		}
		return true;
	}
}
