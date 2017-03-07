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
	public static final int ARG_INDEX_ANY = -1;

	private static final String WHITE_SPACE_REGULAR_EXPRESSION = "\\s";
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile(WHITE_SPACE_REGULAR_EXPRESSION);

	@Getter @NonNull private final TgmMessage message;
	@Getter @NonNull private final TgmMessageEntity entity;

	public String getCommandName() {
		return entity.getEntityString(message.getText());
	}

	/**
	 * See doc for {@link #getPrefixedArgument}.
	 * @param argPrefix argument prefix to search for
	 * @param atIndex search at specific index, {@link #ARG_INDEX_ANY} if you don't care
	 * @return if there is argument with given prefix at given index
	 */
	public boolean hasPrefixedArgument(@NonNull final String argPrefix, final int atIndex) {
		return null != getPrefixedArgument(argPrefix, atIndex);
	}

	/**
	 * Tries to find given argument with given prefix at a given index.
	 * If index is {@link #ARG_INDEX_ANY} returns first found argument with the given prefix.
	 * Can be useful for finding stuff like "argX=z".
	 * @param argPrefix prefix to search
	 * @param atIndex search at specific index, {@link #ARG_INDEX_ANY} if you don't care
	 * @return argument matching the criteria, or null
	 */
	public TgmCommandArgument getPrefixedArgument(@NonNull final String argPrefix, final int atIndex) {
		final TgmCommandArgument[] arguments = getArguments();
		if (ARG_INDEX_ANY != atIndex) {
			final boolean hasIndexArg = atIndex < arguments.length;
			final boolean prefixMatch = hasIndexArg && arguments[atIndex].getValue().startsWith(argPrefix);
			return prefixMatch ? arguments[atIndex] : null;
		}

		for (final TgmCommandArgument argument : arguments) {
			if (argument.getValue().startsWith(argPrefix)) {
				return argument;
			}
		}

		return null;
	}

	public TgmCommandArgument[] getArguments() {
		return getArguments(WHITE_SPACE_REGULAR_EXPRESSION);
	}

	/**
	 * TODO: full doc
	 * @param delimiterRegex -- TODO
	 * @return parsed arguments or an empty array
	 */
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
