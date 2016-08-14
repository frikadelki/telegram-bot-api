/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/14
 */

package org.frikadelki.ash.telegram.api.keyboard;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


public final class TgmKeyboardButton {

	public enum Type {
		DEFAULT,
		LOCATION,
		CONTACT,
		;
	}

	/**
	 * Required. Text of the button. If none of the optional fields are used, it will be sent to the bot as a message
	 * when the button is pressed
	 */
	@Getter @Setter @NonNull private String text;

	/**
	 * Optional. If True, the user's phone number will be sent as a contact when the button is pressed. Available in
	 * private chats only
	 *
	 * Note: request_contact and request_location options will only work in Telegram versions released after 9 April,
	 * 2016. Older clients will ignore them.
	 */
	@SerializedName("request_contact")
	private Boolean requestContact = null;

	/**
	 * Optional. If True, the user's current location will be sent when the button is pressed. Available in private
	 * chats only
	 *
	 * Note: request_contact and request_location options will only work in Telegram versions released after 9 April,
	 * 2016. Older clients will ignore them.
	 */
	@SerializedName("request_location")
	private Boolean requestLocation = null;

	public Type getType() {
		if (requestContact) {
			return Type.CONTACT;
		} else if (requestLocation) {
			return Type.LOCATION;
		} else {
			return Type.DEFAULT;
		}
	}

	public void setType(Type type) {
		requestContact = (type == Type.CONTACT);
		requestLocation = (type == Type.LOCATION);
	}

	public TgmKeyboardButton(String text) {
		this(text, Type.DEFAULT);
	}

	public TgmKeyboardButton(String text, Type type) {
		this.text = text;
		setType(type);
	}
}
