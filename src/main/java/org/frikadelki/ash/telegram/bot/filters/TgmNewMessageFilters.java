/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/22
 */

package org.frikadelki.ash.telegram.bot.filters;

import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.telegram.api.message.TgmMessageEntity;
import org.frikadelki.ash.telegram.runtime.dispatch.TgmUpdateFilter;


public final class TgmNewMessageFilters {

	public static final TgmUpdateFilter IS_REPLY = new TgmUpdateFilter() {
		@Override
		public boolean isMatch(final TgmUpdate update) {
			return update.isNewMessage() && (update.getNewMessage().getReplyToMessage() != null);
		}
	};

	public static final TgmUpdateFilter HAS_SENDER = new TgmUpdateFilter() {
		@Override
		public boolean isMatch(final TgmUpdate update) {
			return update.isNewMessage() && (update.getNewMessage().getFrom() != null);
		}
	};

	public static final TgmUpdateFilter HAS_TEXT = new TgmUpdateFilter() {
		@Override
		public boolean isMatch(final TgmUpdate update) {
			return update.isNewMessage() && (update.getNewMessage().getText() != null);
		}
	};

	public static final TgmUpdateFilter HAS_ENTITY_BOT_COMMAND = new TgmUpdateFilter() {
		@Override
		public boolean isMatch(final TgmUpdate update) {
			return update.isNewMessage() && update.getNewMessage().getEntities(TgmMessageEntity.Type.BOT_COMMAND).iterator().hasNext();
		}
	};
}
