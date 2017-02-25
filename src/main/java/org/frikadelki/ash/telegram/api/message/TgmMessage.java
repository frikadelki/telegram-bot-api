/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/10
 */

package org.frikadelki.ash.telegram.api.message;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.frikadelki.ash.telegram.api.base.TgmEntity;
import org.frikadelki.ash.telegram.api.chat.TgmChat;
import org.frikadelki.ash.telegram.api.chat.TgmUser;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.StreamCompat;

import java.util.Collections;
import java.util.List;


@Builder(builderClassName = "Builder")
public final class TgmMessage {

	/** Required. */
	@SerializedName("message_id")
	@Getter private long messageId = TgmEntity.INVALID_ID;

	/** Required. Date the message was sent in Unix time. */
	@Getter private long date = 0;

	/** Required. Conversation the message belongs to. */
	@Getter private TgmChat chat = null;

	/** Optional. Sender, can be empty for messages sent to channels. */
	@Getter private TgmUser from = null;

	/**
	 * Optional. For replies, the original message.
	 * Note that the Message object in this field will not contain further
	 * reply_to_message fields even if it itself is a reply.
	 */
	@SerializedName("reply_to_message")
	@Getter private TgmMessage replyToMessage = null;

	/**
	 * Optional. For text messages, the actual text of the message, 0-4096 characters.
	 */
	@Getter private String text = null;

	/**
	 * Optional. For text messages, special entities like usernames, URLs, bot commands, etc. that appear in the text.
	 */
	private List<TgmMessageEntity> entities = null;
	private List<TgmMessageEntityBotCommand> entitiesCommands = null;

	/**
	 * @param type type fo entities to return, passing null will return all entities
	 * @return iterable of entities matching given type
	 */
	public Iterable<TgmMessageEntity> getEntities(final TgmMessageEntity.Type type) {
		if( null == entities ) {
			return Collections.emptyList();
		}
		return StreamCompat.where(entities, new Lambda.Predicate1<TgmMessageEntity>() {
			@Override
			public boolean is(TgmMessageEntity tgmMessageEntity) {
				return (type == null) || tgmMessageEntity.is(type);
			}
		});
	}

	public Iterable<TgmMessageEntityBotCommand> getEntitiesCommands() {
		if( entitiesCommands == null ) {
			entitiesCommands = StreamCompat.toList(StreamCompat.select(getEntities(TgmMessageEntity.Type.BOT_COMMAND), new Lambda.FactoryCode1<TgmMessageEntityBotCommand, TgmMessageEntity>() {
				@Override
				public TgmMessageEntityBotCommand produce(final TgmMessageEntity tgmMessageEntity) {
					return new TgmMessageEntityBotCommand(TgmMessage.this, tgmMessageEntity);
				}
			}));
		}
		return entitiesCommands;
	}

	public String getEntityText(@NonNull final TgmMessageEntity entity) {
		return entity.getEntityString(text);
	}

	/**
	 * Optional. A new member was added to the group, information about them (this member may be the bot itself)
	 */
	@SerializedName("new_chat_member")
	@Getter private TgmUser newChatMember = null;

	/**
	 * Optional. A member was removed from the group, information about them (this member may be the bot itself)
	 */
	@SerializedName("left_chat_member")
	@Getter private TgmUser leftChatMember = null;

	/**
	 * Optional. Message is a shared contact, information about the contact
	 */
	@Getter private Contact contact = null;

	/**
	 * This object represents a phone contact.
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Contact {
		/**
		 * Contact's phone number
		 */
		@SerializedName("phone_number")
		@Getter private String phoneNumber;

		/**
		 * Contact's first name
		 */
		@SerializedName("first_name")
		@Getter private String firstName;

		/**
		 * Optional. Contact's last name
		 */
		@SerializedName("last_name")
		@Getter private String lastName;

		/**
		 * Optional. Contact's user identifier in Telegram
		 */
		@SerializedName("user_id")
		@Getter private long userId = TgmEntity.INVALID_ID;
	}

	/**
	 * Optional. Message is a shared location, information about the location
	 */
	@Getter private Location location = null;

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Location {
		@Getter private double latitude = Double.NaN;
		@Getter private double longitude = Double.NaN;
	}

	/*
	TODO

	 forward_from	User	Optional. For forwarded messages, sender of the original message
	 forward_from_chat	Chat	Optional. For messages forwarded from a channel, information about the original channel
	 forward_date	Integer	Optional. For forwarded messages, date the original message was sent in Unix time

	 edit_date	Integer	Optional. Date the message was last edited in Unix time

	 audio	Audio	Optional. Message is an audio file, information about the file
	 document	Document	Optional. Message is a general file, information about the file
	 photo	Array of PhotoSize	Optional. Message is a photo, available sizes of the photo
	 sticker	Sticker	Optional. Message is a sticker, information about the sticker
	 video	Video	Optional. Message is a video, information about the video
	 voice	Voice	Optional. Message is a voice message, information about the file
	 caption	String	Optional. Caption for the document, photo or video, 0-200 characters
	 venue	Venue	Optional. Message is a venue, information about the venue

	 new_chat_title	String	Optional. A chat title was changed to this value
	 new_chat_photo	Array of PhotoSize	Optional. A chat photo was change to this value
	 delete_chat_photo	True	Optional. Service message: the chat photo was deleted

	 group_chat_created	True	Optional. Service message: the group has been created
	 supergroup_chat_created	True	Optional. Service message: the supergroup has been created. This field can‘t be received in a message coming through updates, because bot can’t be a member of a supergroup when it is created. It can only be found in reply_to_message if someone replies to a very first message in a directly created supergroup.
	 channel_chat_created	True	Optional. Service message: the channel has been created. This field can‘t be received in a message coming through updates, because bot can’t be a member of a channel when it is created. It can only be found in reply_to_message if someone replies to a very first message in a channel.
	 migrate_to_chat_id	Integer	Optional. The group has been migrated to a supergroup with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
	 migrate_from_chat_id	Integer	Optional. The supergroup has been migrated from a group with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.

	 pinned_message	Message	Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
	 */

	/**
	 * This is to provide lombok builder with some specific defaults.
	 */
	public static class Builder {
		private long messageId = TgmEntity.INVALID_ID;
	}
}
