/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/11/4
 */

package org.frikadelki.ash.telegram.api.update;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.toolset.utils.AshAssert;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.StreamCompat;

import java.util.ArrayList;


@RequiredArgsConstructor
public final class TgmUpdateDispatchRelay {
	private final ArrayList<TgmUpdateHandler> handlers = new ArrayList<>();

	public void addHandler(@NonNull final TgmUpdateHandler handler) {
		AshAssert.aFalse(handlers.contains(handler));
		handlers.add(handler);
	}

	public void dispatchUpdate(@NonNull final TgmUpdateDispatchContext context, @NonNull final TgmUpdate update) {
		StreamCompat.forEach(handlers, new Lambda.Code1<TgmUpdateHandler>() {
			@Override
			public void invoke(final TgmUpdateHandler handler) {
				if (!handler.getFilter().isMatch(update)) {
					return;
				}
				handler.dispatchUpdate(context, update);
			}
		});
	}
}
