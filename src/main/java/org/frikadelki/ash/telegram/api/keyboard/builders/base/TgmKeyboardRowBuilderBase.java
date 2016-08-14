/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/28
 */

package org.frikadelki.ash.telegram.api.keyboard.builders.base;

import lombok.NonNull;
import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;

import java.util.ArrayList;


public abstract class TgmKeyboardRowBuilderBase<T extends TgmKeyboardRowBuilderBase> {

	@NonNull protected final ArrayList<TgmKeyboardButton> keyboardRow = new ArrayList<>();

	public T button(@NonNull final String text) {
		return button(new TgmKeyboardButton(text));
	}

	public T button(@NonNull final String text, @NonNull final TgmKeyboardButton.Type type) {
		return button(new TgmKeyboardButton(text, type));
	}

	public T button(@NonNull TgmKeyboardButton button) {
		keyboardRow.add(button);
		return getSelf();
	}

	protected abstract T getSelf();
}
