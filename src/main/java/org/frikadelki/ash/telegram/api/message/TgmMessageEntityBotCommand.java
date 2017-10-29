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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

		final ArrayList<TgmCommandArgument> commandArguments = new ArrayList<>();		
		final Iterable<TgmMessageEntity> entities = message.getEntities(null);
		
		fillArgumentsFromEntities(commandArguments, entities, arguments, argumentsStart);
		fillArgumentsWithoutEntity(commandArguments, entities, split, argumentsStart);

		return postProcessArguments(commandArguments);
	}

	private void fillArgumentsFromEntities(
		final ArrayList<TgmCommandArgument> commandArguments,
		final Iterable<TgmMessageEntity> entities,
		final String arguments,
		final int argumentsStart
	) {
		for (final TgmMessageEntity attachment : entities) {
			// attachment interval: [attachmentBegin; attachmentEnd)
			int attachmentBegin = attachment.getOffset();
			int attachmentEnd = attachment.getOffset() + attachment.getLength();
			if (attachmentBegin < argumentsStart) {
				continue;
			}
			
			attachmentBegin -= argumentsStart; 
			attachmentEnd -= argumentsStart;
			if (attachmentEnd > arguments.length()) {
				// Something strange is happening.
				continue;
			}

			final String attachmentValue = arguments.substring(attachmentBegin, attachmentEnd);
			commandArguments.add(new TgmCommandArgument(attachment, attachmentValue));
		}
	}
	
	private void fillArgumentsWithoutEntity(
		final ArrayList<TgmCommandArgument> commandArguments,
		final Iterable<TgmMessageEntity> entities,
		final StringSplit.StringPiece[] split,
		final int argumentsStart
	) {
		for (int i = 0; i < split.length; i++) {
			final StringSplit.StringPiece argumentToken = split[i];
			
			boolean hasEntity = false;
			for (final TgmMessageEntity attachment : entities) {
				if (
					   (argumentsStart + argumentToken.getStartIndex() >= attachment.getOffset())
					&& (argumentsStart + argumentToken.getEndIndex() <= attachment.getOffset() + attachment.getLength())
				) {
					hasEntity = true;
					break;
				}
			}
			
			if (false == hasEntity) {
				final TgmMessageEntity messageEntityNull = new TgmMessageEntity();
				messageEntityNull.setOffset(argumentsStart + argumentToken.getStartIndex());
				commandArguments.add(new TgmCommandArgument(messageEntityNull, argumentToken.getValue()));
			}
		}
	}
	
	private TgmCommandArgument[] postProcessArguments(final ArrayList<TgmCommandArgument> commandArguments) {
		Collections.sort(commandArguments, new Comparator<TgmCommandArgument>() {
			@Override
			public int compare(final TgmCommandArgument lhs, final TgmCommandArgument rhs) {
				return lhs.getAttachedEntity().getOffset() - rhs.getAttachedEntity().getOffset();
			}
		});
		
		final int commandArgumentsSize = commandArguments.size();
		for (int i = 0; i < commandArgumentsSize; ++i) {
			final TgmCommandArgument argument = commandArguments.get(i);
			if (0 == argument.getAttachedEntity().getLength()) {
				commandArguments.set(i, new TgmCommandArgument(null, argument.getValue()));
			}
		}
		
		final TgmCommandArgument[] result = new TgmCommandArgument[commandArguments.size()]; 
		return commandArguments.toArray(result);
	}
}
