/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.api;

import org.frikadelki.ash.telegram.api.chat.TgmBotApiChat;
import org.frikadelki.ash.telegram.api.misc.TgmBotApiMisc;


public interface TgmBotApi {
	TgmBotApiChat getChatApi();
	TgmBotApiMisc getMiscApi();
}
