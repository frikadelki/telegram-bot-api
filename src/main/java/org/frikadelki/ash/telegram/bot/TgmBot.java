/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.bot;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import lombok.extern.java.Log;

import org.frikadelki.ash.telegram.api.TgmBotApi;
import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.telegram.api.chat.TgmUser;
import org.frikadelki.ash.telegram.bot.commands.TgmCommandBody;
import org.frikadelki.ash.telegram.bot.commands.TgmCommandsDispatch;
import org.frikadelki.ash.telegram.runtime.dispatch.*;

import org.frikadelki.ash.telegram.runtime.TgmBotApiFactory;
import org.frikadelki.ash.telegram.runtime.TgmBotRuntime;
import org.frikadelki.ash.telegram.runtime.TgmQueryIO;

@Log
public final class TgmBot {
	private static final String GET_ME_WAS_NOT_SUCCESSFUL = "getMe was not successful.";
	
	@Getter private final TgmBotRuntime runtime;
	@Getter private final TgmBotApi api;

	private final TgmUpdateDispatchFront updateDispatcher = new TgmUpdateDispatchFront();
	private final TgmCommandsDispatch commandsDispatch = new TgmCommandsDispatch(updateDispatcher);

	public TgmBot(@NonNull final String botToken, @NonNull final TgmQueryIO queryIO) {
		runtime = new TgmBotRuntime(botToken, queryIO);
		api = TgmBotApiFactory.makeApi(runtime);
	}
	
	public void warmup() {
		val getMeResult = getApi().getMiscApi().getMe();
		
		TgmUser botInfo = null;
		if (
			getMeResult.isSuccess() &&
			null != (botInfo = getMeResult.getData())
		) {
			commandsDispatch.setFilterBotName(botInfo.getUsername());
		} else {
			log.severe(GET_ME_WAS_NOT_SUCCESSFUL);
			throw new IllegalStateException(GET_ME_WAS_NOT_SUCCESSFUL);
		}
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

	public void dispatchUpdate(@NonNull final TgmUpdateDispatchContext dispatchContext, @NonNull final TgmUpdate update) {
		updateDispatcher.dispatchUpdate(dispatchContext, update);
	}
}
