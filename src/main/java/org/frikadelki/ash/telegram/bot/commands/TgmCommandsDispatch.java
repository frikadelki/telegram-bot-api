/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.bot.commands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.val;
import org.frikadelki.ash.telegram.api.message.TgmMessage;
import org.frikadelki.ash.telegram.api.message.TgmMessageEntityBotCommand;
import org.frikadelki.ash.telegram.api.update.*;
import org.frikadelki.ash.toolset.utils.AshAssert;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.collections.StreamCompat;

import java.util.ArrayList;


public final class TgmCommandsDispatch implements TgmUpdateHandler, TgmCommandsRegistry {
	private final TgmCommandsRegistry internalRegistry = new TgmCommandsRegistryDefault();

	private final TgmUpdateDispatchRelay relay = new TgmUpdateDispatchRelay();

	private final ArrayList<Handler> commandHandlers = new ArrayList<>();
	private String filterBotName = null;

	@Override
	public Iterable<TgmCommand> getCommands() {
		return internalRegistry.getCommands();
	}

	@Override
	public void addCommand(@NonNull final TgmCommand command) {
		internalRegistry.addCommand(command);

		val handler = new Handler(command);
		handler.setFilterBotName(filterBotName);

		relay.addHandler(handler);
		commandHandlers.add(handler);
	}

	@Override
	public void addRegistry(final TgmCommandsRegistry registry) {
		StreamCompat.forEach(registry.getCommands(), new Lambda.Code1<TgmCommand>() {
			@Override
			public void invoke(final TgmCommand argument) {
				addCommand(argument);
			}
		});
	}

	public void setFilterBotName(final String filterBotName) {
		this.filterBotName = filterBotName;
		StreamCompat.forEach(commandHandlers, new Lambda.Code1<Handler>() {
			@Override
			public void invoke(final Handler handler) {
				handler.setFilterBotName(filterBotName);
			}
		});
	}

	@Override
	public TgmUpdateFilter getFilter() {
		return TgmUpdateFilters.ACCEPT_ALL;
	}

	@Override
	public void dispatchUpdate(final TgmUpdateDispatchContext context, final TgmUpdate update) {
		relay.dispatchUpdate(context, update);
	}

	@Log
	private static final class Handler implements TgmUpdateHandler {
		private final TgmCommand spec;
		private final CommandNamePredicate commandNamePredicate;
		private final TgmUpdateFilter fullFilter;

		private Handler(@NonNull final TgmCommand spec) {
			this.spec = spec;
			this.commandNamePredicate = new CommandNamePredicate(spec.getName());
			this.fullFilter = TgmUpdateFilters.and(spec.getDispatchFilter(), new TgmUpdateFilter() {
				@Override
				public boolean isMatch(final TgmUpdate update) {
					val message = extractMessage(update);
					return (null != message) && StreamCompat.has(message.getEntitiesCommands(), commandNamePredicate);
				}
			});
		}

		void setFilterBotName(final String filterBotName) {
			commandNamePredicate.setFilterBotName(filterBotName);
		}

		@Override
		public TgmUpdateFilter getFilter() {
			return fullFilter;
		}

		@Override
		public void dispatchUpdate(@NonNull final TgmUpdateDispatchContext context, @NonNull final TgmUpdate update) {
			val message = extractMessage(update);
			AshAssert.aNonNull(message);
			if (null == message) {
				return;
			}

			val command = StreamCompat.first(message.getEntitiesCommands(), commandNamePredicate);
			spec.getCommandBody().dispatchCommand(context, update, command);
		}

		private static TgmMessage extractMessage(final TgmUpdate update) {
			if (update.isNewMessage()) {
				return update.getNewMessage();
			} else if (update.isEditedMessage()) {
				return update.getEditedMessage();
			} else {
				LOG.severe("Encountered unknown update format.");
				return null;
			}
		}
	}

	@RequiredArgsConstructor
	private static final class CommandNamePredicate implements Lambda.Predicate1<TgmMessageEntityBotCommand> {
		@NonNull private final String commandName;

		@Setter private String filterBotName = null;

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
	}
}
