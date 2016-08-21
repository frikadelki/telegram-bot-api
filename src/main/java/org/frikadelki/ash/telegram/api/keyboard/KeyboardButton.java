/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by d.morozov on 2016/8/14
 */

package org.frikadelki.ash.telegram.api.keyboard;

import com.google.gson.annotations.SerializedName;
import lombok.NonNull;

public final class KeyboardButton {

    public enum RequestEntity {
        NONE,
        LOCATION,
        CONTACT,
        ;
    }

    /**
     * Required. Text of the button. If none of the optional fields are used, it will be sent to the bot as a message
     * when the button is pressed
     */
    @NonNull private String text;

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

    public KeyboardButton(String text) {
        this(text, RequestEntity.NONE);
    }

    public KeyboardButton(String text, RequestEntity requestEntity) {
        this.text = text;
        requestContact = (requestEntity == RequestEntity.CONTACT);
        requestLocation = (requestEntity == RequestEntity.LOCATION);
    }
}
