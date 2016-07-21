/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.api.misc;

import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.telegram.api.chat.TgmUser;
import org.frikadelki.ash.toolset.result.AshResult;
import org.frikadelki.ash.toolset.result.AshResultGist;


public interface TgmBotApiMisc {
	AshResult<TgmUser> getMe();

	AshResultGist setWebhook(final WebhookParams params);

	@RequiredArgsConstructor
	final class WebhookParams {
		private final String url;
	}
}
