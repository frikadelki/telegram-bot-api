package org.frikadelki.ash.telegram.api.message;

import lombok.val;
import org.frikadelki.ash.toolset.utils.AshAssert;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.collections.StreamCompat;
import org.junit.Test;


public final class TgmMessageEntityBotCommandTests {
	@Test
	public void testArgumentsSearch() {
		val message = TgmMessageTestData.withEntities(
				"vii /first f_arg1 f_arg2=val2 f_arg3 /second s_arg1=1 s_arg2",
				new TgmMessageEntity(TgmMessageEntity.Type.BOT_COMMAND, 4, 6, null, null),
				new TgmMessageEntity(TgmMessageEntity.Type.BOT_COMMAND, 37, 7, null, null));
		val commands = message.getEntitiesCommands();

		val firstCommand = StreamCompat.first(commands, new Lambda.Predicate1<TgmMessageEntityBotCommand>() {
			@Override
			public boolean is(final TgmMessageEntityBotCommand command) {
				return command.getCommandName().equals("/first");
			}
		});
		AshAssert.aTrue(null != firstCommand.getPrefixedArgument("f_arg1", 0));
		AshAssert.aTrue(null != firstCommand.getPrefixedArgument("f_arg2", 1));
		AshAssert.aTrue(null != firstCommand.getPrefixedArgument("f_arg3", 2));
		AshAssert.aFalse(null != firstCommand.getPrefixedArgument("s_arg1", TgmMessageEntityBotCommand.ARG_INDEX_ANY));
		AshAssert.aFalse(null != firstCommand.getPrefixedArgument("s_arg2", TgmMessageEntityBotCommand.ARG_INDEX_ANY));

		val secondCommand = StreamCompat.first(commands, new Lambda.Predicate1<TgmMessageEntityBotCommand>() {
			@Override
			public boolean is(final TgmMessageEntityBotCommand command) {
				return command.getCommandName().equals("/second");
			}
		});
		AshAssert.aFalse(null != secondCommand.getPrefixedArgument("s_arg1", 1));
		AshAssert.aTrue(null != secondCommand.getPrefixedArgument("s_arg1", TgmMessageEntityBotCommand.ARG_INDEX_ANY));
		AshAssert.aTrue(null != secondCommand.getPrefixedArgument("s_arg2", TgmMessageEntityBotCommand.ARG_INDEX_ANY));
	}
}
