/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.runtime;

import lombok.NonNull;
import org.frikadelki.ash.telegram.api.TgmBotApi;


public final class TgmBotApiFactory {
	public static TgmBotApi makeApi(@NonNull final TgmBotRuntime runtime) {
		return new TgmBotApiImpl(runtime);
	}
}
