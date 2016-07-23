/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.runtime.dispatch;

import com.google.gson.JsonParseException;
import lombok.NonNull;
import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.telegram.runtime.TgmJsonIO;


public final class TgmUpdateDispatchFront {
	private final TgmUpdateDispatchCore dispatchCore = new TgmUpdateDispatchCore();

	public void addHandler(@NonNull final TgmUpdateDispatchHandler handler) {
		dispatchCore.addHandler(handler);
	}

	public void dispatchRawUpdate(@NonNull final TgmUpdateDispatchContext dispatchContext, @NonNull final String updateJsonBodyString) {
		try {
			final TgmUpdate update = TgmJsonIO.fromJson(updateJsonBodyString, TgmUpdate.class);
			if (update == null) {
				return;
			}
			dispatchCore.dispatchUpdate(dispatchContext, update);
		}
		catch (final JsonParseException e) {
			e.printStackTrace();
		}
	}

	public void dispatchUpdate(@NonNull final TgmUpdateDispatchContext context, @NonNull final TgmUpdate update) {
		dispatchCore.dispatchUpdate(context, update);
	}
}
