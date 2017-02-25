/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/11/4
 */

package org.frikadelki.ash.telegram.bot.commands;


public interface TgmCommandsRegistry {
	Iterable<TgmCommand> getCommands();

	void addCommand(TgmCommand command);
	void addRegistry(TgmCommandsRegistry registry);
}
