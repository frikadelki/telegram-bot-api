/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.bot.commands;

import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.telegram.api.message.TgmMessageEntityBotCommand;
import org.frikadelki.ash.telegram.runtime.dispatch.TgmUpdateDispatchContext;


public interface TgmCommandBody {
	void dispatchCommand(TgmUpdateDispatchContext context, TgmUpdate update, TgmMessageEntityBotCommand command);
}
