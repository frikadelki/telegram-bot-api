/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.runtime;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.telegram.api.TgmBotApi;
import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.telegram.api.chat.TgmBotApiChat;
import org.frikadelki.ash.telegram.api.chat.TgmUser;
import org.frikadelki.ash.telegram.api.TgmBotApiUpdates;
import org.frikadelki.ash.toolset.result.AshResult;
import org.frikadelki.ash.toolset.result.AshResultGist;


@RequiredArgsConstructor
final class TgmBotApiImpl implements TgmBotApi {
	@NonNull private final TgmBotRuntime runtime;

	@Override
	public AshResult<TgmUser> getMe() {
		return runtime.query("getMe", null, TgmUser.class);
	}

	@Getter
	private final TgmBotApiChat chatApi = new TgmBotApiChat() {
		@Override
		public AshResultGist sendMessage(@NonNull final SendMessageParams params) {
			return runtime.query("sendMessage", params, Void.class);
		}
	};

	@Getter
	private final TgmBotApiUpdates updatesApi = new TgmBotApiUpdates() {
		@Override
		public AshResultGist setWebhook(final WebhookParams params) {
			return runtime.query("setWebhook", params, Void.class);
		}

		@Override
		public AshResult<TgmUpdate[]> getUpdates(final UpdatesParams params) {
			return runtime.query("getUpdates", params, TgmUpdate[].class);
		}
	};
}
