/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.runtime.dispatch;

import lombok.NonNull;
import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.toolset.utils.AshAssert;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.StreamCompat;

import java.util.ArrayList;


public final class TgmUpdateDispatchCore {
	private final ArrayList<TgmUpdateDispatchHandler> handlers = new ArrayList<>();

	public void addHandler(@NonNull final TgmUpdateDispatchHandler handler) {
		AshAssert.aFalse(handlers.contains(handler));
		handlers.add(handler);
	}

	public void dispatchUpdate(@NonNull final TgmUpdateDispatchContext context, @NonNull final TgmUpdate update) {
		final Iterable<TgmUpdateDispatchHandler> matchingHandlers = StreamCompat.where(handlers, new Lambda.Predicate1<TgmUpdateDispatchHandler>() {
			@Override public boolean is(final TgmUpdateDispatchHandler handler) {
				return handler.getFilter().isMatch(update);
			}
		});
		StreamCompat.forEach(matchingHandlers, new Lambda.Code1<TgmUpdateDispatchHandler>() {
			@Override public void invoke(final TgmUpdateDispatchHandler handler) {
				handler.dispatchUpdate(context, update);
			}
		});
	}
}
