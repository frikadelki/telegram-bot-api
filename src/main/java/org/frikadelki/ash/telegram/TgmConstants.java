/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/18
 */

package org.frikadelki.ash.telegram;

import lombok.NonNull;

import java.nio.charset.Charset;


public final class TgmConstants {
	private static final String BOT_API_URL_PREFIX = "https://api.telegram.org/bot";

	public static final Charset CHARSET = Charset.forName("UTF-8");
	public static final String BODY_JSON_CONTENT_TYPE = "application/json";

	public static String makeBaseBotApiUrl(@NonNull final String botToken) {
		return BOT_API_URL_PREFIX + botToken + "/";
	}
}
