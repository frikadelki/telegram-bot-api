/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/11/5
 */

package org.frikadelki.ash.telegram.api.update;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.frikadelki.ash.telegram.api.message.TgmMessageEntity;


@UtilityClass
public final class TgmUpdateFilters {
	public static TgmUpdateFilter ACCEPT_ALL = new TgmUpdateFilter() {
		@Override
		public boolean isMatch(final TgmUpdate update) {
			return true;
		}
	};

	public static TgmUpdateFilter and(@NonNull final TgmUpdateFilter... filters) {
		if ((null == filters) || (0 == filters.length)) {
			return ACCEPT_ALL;
		}
		return new TgmUpdateFilter() {
			@Override
			public boolean isMatch(final TgmUpdate update) {
				for (final TgmUpdateFilter filter : filters) {
					final boolean check = (null == filter) || filter.isMatch(update);
					if (!check) {
						return false;
					}
				}
				return true;
			}
		};
	}

	@UtilityClass
	public static final class NewMessage {
		public static TgmUpdateFilter NEW_MESSAGE = new TgmUpdateFilter() {
			@Override
			public boolean isMatch(final TgmUpdate update) {
				return update.isNewMessage();
			}
		};

		public static final TgmUpdateFilter IS_REPLY = new TgmUpdateFilter() {
			@Override
			public boolean isMatch(final TgmUpdate update) {
				return update.isNewMessage() && (null != update.getNewMessage().getReplyToMessage());
			}
		};

		public static final TgmUpdateFilter HAS_SENDER = new TgmUpdateFilter() {
			@Override
			public boolean isMatch(final TgmUpdate update) {
				return update.isNewMessage() && (null != update.getNewMessage().getFrom());
			}
		};

		public static final TgmUpdateFilter HAS_TEXT = new TgmUpdateFilter() {
			@Override
			public boolean isMatch(final TgmUpdate update) {
				return update.isNewMessage() && (null != update.getNewMessage().getText());
			}
		};

		public static final TgmUpdateFilter HAS_USER_BOT_COMMAND = new TgmUpdateFilter() {
			@Override
			public boolean isMatch(final TgmUpdate update) {
				return update.isNewMessage() &&
						update.getNewMessage().getFrom() != null &&
						update.getNewMessage().hasEntities(TgmMessageEntity.Type.BOT_COMMAND);
			}
		};
	}
}
