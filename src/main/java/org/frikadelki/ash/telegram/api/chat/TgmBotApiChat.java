/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/18
 */

package org.frikadelki.ash.telegram.api.chat;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.NonNull;
import org.frikadelki.ash.toolset.result.AshResultGist;


public interface TgmBotApiChat {

	AshResultGist sendMessage(@NonNull final SendMessageParams params);

	@Builder(builderClassName = "Builder")
	final class SendMessageParams {
		public static String PARSE_MODE_MARKDOWN = "Markdown";
		public static String PARSE_MODE_HTML = "HTML";

		@SerializedName("chat_id")
		@NonNull private final Long chatId;
		@NonNull private final String text;

		/**
		 * Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
		 */
		@SerializedName("parse_mode")
		private final String parseMode;

		/**
		 * Optional. Disables link previews for links in this message.
		 */
		@SerializedName("disable_web_page_preview")
		private final Boolean disableWebPagePreview;

		/**
		 * Optional. Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
		 */
		@SerializedName("disable_notification")
		private final Boolean disableNotification;

		/**
		 * Optional. If the message is a reply, ID of the original message.
		 */
		@SerializedName("reply_to_message_id")
		private final Long replyToMessageId;

		/**
		 * Optional. InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply
		 * Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard,
		 * instructions to hide reply keyboard or to force a reply from the user.
		 */
		@SerializedName("reply_markup")
		private final Object replyMarkup;
	}

}
