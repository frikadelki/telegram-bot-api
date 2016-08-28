/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.bot.commands;

import lombok.NonNull;
import lombok.Setter;
import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.telegram.api.message.TgmMessageEntityBotCommand;
import org.frikadelki.ash.telegram.runtime.dispatch.*;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.StreamCompat;


final class TgmNewMessageCommandHandler implements TgmUpdateDispatchHandler {
	private final String commandName;
	private final TgmUpdateFilter commandFilter;
	private final TgmCommandBody commandBody;

	@Setter private String filterBotName = null;

	TgmNewMessageCommandHandler(@NonNull final String commandName, @NonNull final TgmUpdateFilter baseFilter, @NonNull final TgmCommandBody commandBody) {
		this.commandName = commandName;
		this.commandFilter = new UpdateFilterAND(baseFilter, new TgmUpdateFilter() {
			@Override
			public boolean isMatch(final TgmUpdate update) {
				return update.isNewMessage() && StreamCompat.has(update.getNewMessage().getEntitiesCommands(), commandNameFilterPredicate);
			}
		});
		this.commandBody = commandBody;
	}

	@Override
	public TgmUpdateFilter getFilter() {
		return commandFilter;
	}

	@Override
	public void dispatchUpdate(final TgmUpdateDispatchContext context, final TgmUpdate update) {
		final Iterable<TgmMessageEntityBotCommand> matchingCommands = StreamCompat.where(update.getNewMessage().getEntitiesCommands(), commandNameFilterPredicate);
		StreamCompat.forEach(matchingCommands, new Lambda.Code1<TgmMessageEntityBotCommand>() {
			@Override
			public void invoke(final TgmMessageEntityBotCommand command) {
				commandBody.dispatchCommand(context, update, command);
			}
		});
	}

	private final Lambda.Predicate1<TgmMessageEntityBotCommand> commandNameFilterPredicate = new Lambda.Predicate1<TgmMessageEntityBotCommand>() {
		@Override
		public boolean is(final TgmMessageEntityBotCommand command) {
			final String fullCommandName = command.getCommandName();
			final int lastAtIndex = fullCommandName.lastIndexOf('@');
			if ((null == filterBotName) || (lastAtIndex < 0)) {
				return commandName.equalsIgnoreCase(fullCommandName);
			} else {
				final String pureCommandName = fullCommandName.substring(0, lastAtIndex);
				final String botName = fullCommandName.substring(lastAtIndex + 1);
				return filterBotName.equalsIgnoreCase(botName) && commandName.equalsIgnoreCase(pureCommandName);
			}
		}
	};
}
