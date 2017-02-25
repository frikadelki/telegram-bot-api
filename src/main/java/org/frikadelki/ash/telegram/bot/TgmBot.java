/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.bot;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import lombok.val;
import org.frikadelki.ash.telegram.api.TgmBotApi;
import org.frikadelki.ash.telegram.api.chat.TgmUser;
import org.frikadelki.ash.telegram.api.update.TgmUpdate;
import org.frikadelki.ash.telegram.api.update.TgmUpdateDispatch;
import org.frikadelki.ash.telegram.api.update.TgmUpdateDispatchContext;
import org.frikadelki.ash.telegram.api.update.TgmUpdateHandler;
import org.frikadelki.ash.telegram.bot.commands.TgmCommandsDispatch;
import org.frikadelki.ash.telegram.bot.commands.TgmCommandsRegistry;
import org.frikadelki.ash.telegram.runtime.TgmBotApiFactory;
import org.frikadelki.ash.telegram.runtime.TgmBotRuntime;
import org.frikadelki.ash.telegram.runtime.TgmQueryIO;

@Log
public final class TgmBot {
	private static final String GET_ME_WAS_NOT_SUCCESSFUL = "getMe was not successful.";
	
	@Getter private final TgmBotRuntime runtime;
	@Getter private final TgmBotApi api;

	private final TgmUpdateDispatch updateDispatcher = new TgmUpdateDispatch();
	private final TgmCommandsDispatch commandsDispatch = new TgmCommandsDispatch();

	{
		updateDispatcher.addHandler(commandsDispatch);
	}

	public TgmBot(@NonNull final String botToken, @NonNull final TgmQueryIO queryIO) {
		runtime = new TgmBotRuntime(botToken, queryIO);
		api = TgmBotApiFactory.makeApi(runtime);
	}
	
	public void warmup() {
		val getMeResult = getApi().getMe();
		
		TgmUser botInfo = null;
		if (
			getMeResult.isSuccess() &&
			null != (botInfo = getMeResult.getData())
		) {
			commandsDispatch.setFilterBotName(botInfo.getUsername());
		} else {
			LOG.severe(GET_ME_WAS_NOT_SUCCESSFUL);
			throw new IllegalStateException(GET_ME_WAS_NOT_SUCCESSFUL);
		}
	}

	public void addUpdateHandler(@NonNull final TgmUpdateHandler handler) {
		updateDispatcher.addHandler(handler);
	}

	public TgmCommandsRegistry getCommandsRegistry() {
		return commandsDispatch;
	}

	public void dispatchRawUpdate(@NonNull final TgmUpdateDispatchContext dispatchContext, @NonNull final String updateJsonBodyString) {
		updateDispatcher.dispatchRawUpdate(dispatchContext, updateJsonBodyString);
	}

	public void dispatchUpdate(@NonNull final TgmUpdateDispatchContext dispatchContext, @NonNull final TgmUpdate update) {
		updateDispatcher.dispatchUpdate(dispatchContext, update);
	}
}
