/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/14
 */

package org.frikadelki.ash.telegram.api.chat.keyboard;

import com.google.gson.annotations.SerializedName;
import lombok.*;


@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class TgmKeyboardButton {

	/**
	 * Required. Text of the button. If none of the optional fields are used, it will be sent to the bot as a message
	 * when the button is pressed
	 */
	@Setter @NonNull private String text;

	/**
	 * Optional. If True, the user's phone number will be sent as a contact when the button is pressed. Available in
	 * private chats only
	 *
	 * Note: request_contact and request_location options will only work in Telegram versions released after 9 April,
	 * 2016. Older clients will ignore them.
	 */
	@SerializedName("request_contact")
	@Setter private Boolean requestContact = null;

	/**
	 * Optional. If True, the user's current location will be sent when the button is pressed. Available in private
	 * chats only
	 *
	 * Note: request_contact and request_location options will only work in Telegram versions released after 9 April,
	 * 2016. Older clients will ignore them.
	 */
	@SerializedName("request_location")
	@Setter private Boolean requestLocation = null;
}
