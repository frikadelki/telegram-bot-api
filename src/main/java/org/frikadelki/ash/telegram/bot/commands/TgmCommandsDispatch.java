/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.bot.commands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.telegram.runtime.dispatch.TgmUpdateDispatchFront;
import org.frikadelki.ash.telegram.runtime.dispatch.TgmUpdateFilter;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.StreamCompat;

import java.util.ArrayList;


@RequiredArgsConstructor
public final class TgmCommandsDispatch {
	@NonNull private final TgmUpdateDispatchFront dispatchFront;
	private final ArrayList<TgmNewMessageCommandHandler> commandHandlers = new ArrayList<>();

	public void addNewMessageCommand(@NonNull final String commandName, @NonNull final TgmUpdateFilter baseFilter, @NonNull final TgmCommandBody commandBody) {
		final TgmNewMessageCommandHandler handler = new TgmNewMessageCommandHandler(commandName, baseFilter, commandBody);
		dispatchFront.addHandler(handler);
		commandHandlers.add(handler);
	}

	public void setFilterBotName(final String filterBotName) {
		StreamCompat.forEach(commandHandlers, new Lambda.Code1<TgmNewMessageCommandHandler>() {
			@Override public void invoke(final TgmNewMessageCommandHandler handler) {
				handler.setFilterBotName(filterBotName);
			}
		});
	}
}
