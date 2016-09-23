/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/10
 */

package org.frikadelki.ash.telegram.api.message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.toolset.utils.StringSplit;

import java.util.regex.Pattern;


@RequiredArgsConstructor
public final class TgmMessageEntityBotCommand {
	@Getter @NonNull private final TgmMessage message;
	@Getter @NonNull private final TgmMessageEntity entity;

	public static final String WHITE_SPACE_REGULAR_EXPRESSION = "\\s";
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile(WHITE_SPACE_REGULAR_EXPRESSION);

	public String getCommandName() {
		return entity.getEntityString(message.getText());
	}

	public TgmCommandArgument[] getArguments() {
		return getArguments("\\s");
	}

	public TgmCommandArgument[] getArguments(@NonNull final String delimiterRegex) {
		TgmMessageEntity nextAfterMe = null;
		final Iterable<TgmMessageEntity> commands = message.getEntities(TgmMessageEntity.Type.BOT_COMMAND);

		for (final TgmMessageEntity candidate : commands) {
			if (candidate.getOffset() > entity.getOffset() && (null == nextAfterMe || candidate.getOffset() < nextAfterMe.getOffset())) {
				nextAfterMe = candidate;
			}
		}

		final int argumentsStart = entity.getOffset() + entity.getLength();

		final String arguments;
		if (null == nextAfterMe) {
			arguments = message.getText().substring(argumentsStart);
		} else {
			arguments = message.getText().substring(argumentsStart, nextAfterMe.getOffset());
		}

		final Pattern pattern = WHITE_SPACE_REGULAR_EXPRESSION.equals(delimiterRegex) ? WHITESPACE_PATTERN : Pattern.compile(delimiterRegex);
		final StringSplit.StringPiece[] split = StringSplit.splitOrEmpty(arguments, pattern);

		final TgmCommandArgument[] result = new TgmCommandArgument[split.length];

		outer: for (int i = 0; i < split.length; i++) {
			final StringSplit.StringPiece argumentToken = split[i];

			final Iterable<TgmMessageEntity> entities = message.getEntities(null);
			for (final TgmMessageEntity attachment : entities) {
				if (argumentsStart + argumentToken.getStartIndex() == attachment.getOffset()
						&& argumentsStart + argumentToken.getEndIndex() == attachment.getOffset() + attachment.getLength()) {
					result[i] = new TgmCommandArgument(attachment, argumentToken.getValue());
					continue outer;
				}
			}

			result[i] = new TgmCommandArgument(null, argumentToken.getValue());
		}

		return result;
	}
}
