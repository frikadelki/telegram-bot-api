/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.bot;

import lombok.Getter;
import lombok.NonNull;
import org.frikadelki.ash.telegram.api.TgmBotApi;
import org.frikadelki.ash.telegram.runtime.TgmBotApiFactory;
import org.frikadelki.ash.telegram.runtime.TgmBotRuntime;
import org.frikadelki.ash.telegram.runtime.TgmQueryIO;


public final class TgmBot {
	@Getter private final TgmBotRuntime runtime;
	@Getter private final TgmBotApi api;

	private TgmBot(@NonNull final String botToken, @NonNull final TgmQueryIO queryIO) {
		runtime = new TgmBotRuntime(botToken, queryIO);
		api = TgmBotApiFactory.makeApi(runtime);
	}
}
