/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/10
 */

package org.frikadelki.ash.telegram.api.message;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NonNull;
import org.frikadelki.ash.telegram.api.chat.TgmUser;
import org.frikadelki.ash.toolset.utils.AshAssert;


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

	/**
	 *Type of the entity. Can be mention (@username), hashtag, bot_command, url, email, bold (bold text),
	 * italic (italic text), code (monowidth string), pre (monowidth block), text_link (for clickable text URLs),
	 * text_mention (for users without usernames).
	 */
	private Type type = null;

	/**
	 * Offset in UTF-16 code units to the start of the entity.
	 */
	@Getter private int offset = 0;

	/**
	 * Length of the entity in UTF-16 code units.
	 */
	@Getter private int length = 0;

	/**
	 * Optional. For “text_link” only, url that will be opened after user taps on the text.
	 */
	private String url = null;

	/**
	 * Optional. For “text_mention” only, the mentioned user.
	 */
	private TgmUser user = null;

	public boolean is(@NonNull final Type type) {
		return (this.type == type);
	}

	String getEntityString(final String text) {
		return text.substring(offset, offset + length);
	}

	public static TgmUser user(@NonNull final TgmMessageEntity entity) {
		AshAssert.aTrue(entity.is(Type.TEXT_MENTION));
		return entity.user;
	}
}
