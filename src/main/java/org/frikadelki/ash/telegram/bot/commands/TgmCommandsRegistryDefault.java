package org.frikadelki.ash.telegram.bot.commands;

import org.frikadelki.ash.toolset.utils.AshAssert;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.collections.StreamCompat;

import java.util.ArrayList;


final class TgmCommandsRegistryDefault implements TgmCommandsRegistry {
	private final ArrayList<TgmCommand> commands = new ArrayList<>();

	@Override
	public Iterable<TgmCommand> getCommands() {
		return commands;
	}

	@Override
	public void addCommand(final TgmCommand command) {
		AshAssert.aFalse(commands.contains(command));
		if (commands.contains(command)) {
			return;
		}
		commands.add(command);
	}

	@Override
	public void addRegistry(final TgmCommandsRegistry registry) {
		StreamCompat.forEach(registry.getCommands(), new Lambda.Code1<TgmCommand>() {
			@Override
			public void invoke(final TgmCommand command) {
				addCommand(command);
			}
		});
	}
}
