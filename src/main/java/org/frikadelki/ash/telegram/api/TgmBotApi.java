/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.api;

import org.frikadelki.ash.telegram.api.chat.TgmBotApiChat;
import org.frikadelki.ash.telegram.api.chat.TgmUser;
import org.frikadelki.ash.toolset.result.AshResult;


public interface TgmBotApi {

	/**
	 * A simple method for testing your bot's auth token. Requires no parameters.
	 * Returns basic information about the bot in form of a User object.
	 * @return  bot's user on success, error on failure
	 */
	AshResult<TgmUser> getMe();

	/**
	 * @return api used to access bot chat functions: sending messages, keyboards, etc.
	 */
	TgmBotApiChat getChatApi();

	/**
	 * @return api used to get updates (i.e. user messages, commands, etc.) from telegram
	 */
	TgmBotApiUpdates getUpdatesApi();
}
