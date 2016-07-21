/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/10
 */

package org.frikadelki.ash.telegram.api.message;

import com.google.gson.annotations.SerializedName;
import lombok.NonNull;
import org.frikadelki.ash.telegram.api.chat.TgmUser;


public final class TgmMessageEntity {
	public enum Type {
		@SerializedName("mention")		MENTION,		// @username
		@SerializedName("hashtag")		HASHTAG,		// #tag
		@SerializedName("bot_command") 	BOT_COMMAND,	// /command
		@SerializedName("url")			URL,			// duh
		@SerializedName("email")		EMAIL,			// duh
		@SerializedName("bold")			BOLD_TEXT,		// *text*
		@SerializedName("italic")		ITALIC_TEXT,	// _text_
		@SerializedName("code")			CODE_LINE,		// `line`
		@SerializedName("pre")			CODE_BLOCK,		// ```line\nline\nline```
		@SerializedName("text_link")	TEXT_LINK,		// for clickable text URLs
		@SerializedName("text_mention")	TEXT_MENTION,	// for users without usernames
		UNKNOWN,
		;
	}

	private Type type = null;
	private int offset = 0;
	private int length = 0;

	private String url = null;	// optional, text_link only
	private TgmUser user = null; // optional, text_mention only

	public boolean is(@NonNull final Type type) {
		return (this.type == type);
	}

	String getEntityString(final String text) {
		return text.substring(offset, length);
	}
}
