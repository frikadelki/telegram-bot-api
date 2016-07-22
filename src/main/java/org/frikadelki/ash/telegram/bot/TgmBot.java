/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.bot;

import lombok.Getter;
import lombok.NonNull;
import org.frikadelki.ash.telegram.api.TgmBotApi;
import org.frikadelki.ash.telegram.bot.commands.TgmCommandBody;
import org.frikadelki.ash.telegram.bot.commands.TgmCommandsDispatch;
import org.frikadelki.ash.telegram.runtime.dispatch.*;
import org.frikadelki.ash.telegram.runtime.TgmBotApiFactory;
import org.frikadelki.ash.telegram.runtime.TgmBotRuntime;
import org.frikadelki.ash.telegram.runtime.TgmQueryIO;


public final class TgmBot {
	@Getter private final TgmBotRuntime runtime;
	@Getter private final TgmBotApi api;

	private final TgmUpdateDispatchFront updateDispatcher = new TgmUpdateDispatchFront();
	private final TgmCommandsDispatch commandsDispatch = new TgmCommandsDispatch(updateDispatcher);

	public TgmBot(@NonNull final String botToken, @NonNull final TgmQueryIO queryIO) {
		runtime = new TgmBotRuntime(botToken, queryIO);
		api = TgmBotApiFactory.makeApi(runtime);
	}

	public void addNewMessageCommandHandler(@NonNull final String commandName, @NonNull final TgmUpdateFilter baseFilter, @NonNull final TgmCommandBody commandBody) {
		commandsDispatch.addNewMessageCommand(commandName, baseFilter, commandBody);
	}

	public void addGenericHandler(@NonNull final TgmUpdateDispatchHandler handler) {
		updateDispatcher.addHandler(handler);
	}

	public void dispatchRawUpdate(@NonNull final TgmUpdateDispatchContext dispatchContext, @NonNull final String updateJsonBodyString) {
		updateDispatcher.dispatchRawUpdate(dispatchContext, updateJsonBodyString);
	}
}
