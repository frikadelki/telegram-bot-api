/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by d.morozov on 2016/8/14
 */

package org.frikadelki.ash.telegram.api.keyboard;

import com.google.gson.annotations.SerializedName;
import lombok.NonNull;

public final class ReplyKeyboardHide
{
    /**
     * Required. Requests clients to hide the custom keyboard
     */
    @SerializedName("hide_keyboard")
    @NonNull private Boolean hideKeyboard;

    /**
     * Optional. Use this parameter if you want to hide keyboard for specific users only. Targets: 1) users that are
     * @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id),
     * sender of the original message.
     *
     * Example: A user votes in a poll, bot returns confirmation message in reply to the vote and hides keyboard for
     * that user, while still showing the keyboard with poll options to users who haven't voted yet.
     */
    private Boolean selective = null;

    public ReplyKeyboardHide() {
        this(true, null);
    }

    public ReplyKeyboardHide(Boolean selective) {
        this(true, selective);
    }

    private ReplyKeyboardHide(@NonNull Boolean hideKeyboard, Boolean selective) {
        this.hideKeyboard = hideKeyboard;
        this.selective = selective;
    }
}
