/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.api.update;

import com.google.gson.JsonParseException;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.frikadelki.ash.telegram.runtime.TgmJsonIO;

import java.util.logging.Level;


@Log
public final class TgmUpdateDispatch {
	private final TgmUpdateDispatchRelay rootRelay = new TgmUpdateDispatchRelay();

	public void addHandler(@NonNull final TgmUpdateHandler handler) {
		rootRelay.addHandler(handler);
	}

	public void dispatchRawUpdate(@NonNull final TgmUpdateDispatchContext dispatchContext, @NonNull final String updateJsonBodyString) {
		try {
			final TgmUpdate update = TgmJsonIO.fromJson(updateJsonBodyString, TgmUpdate.class);
			if (update == null) {
				return;
			}
			dispatchUpdate(dispatchContext, update);
		}
		catch (final JsonParseException e) {
			LOG.log(Level.SEVERE, "Failed to parse json update body string.", e);
		}
	}

	public void dispatchUpdate(@NonNull final TgmUpdateDispatchContext context, @NonNull final TgmUpdate update) {
		rootRelay.dispatchUpdate(context, update);
	}
}
