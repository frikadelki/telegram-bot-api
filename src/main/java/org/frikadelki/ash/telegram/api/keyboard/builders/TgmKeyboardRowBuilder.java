/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/29
 */

package org.frikadelki.ash.telegram.api.keyboard.builders;

import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.telegram.api.keyboard.builders.base.TgmKeyboardRowBuilderBase;

import java.util.List;


public final class TgmKeyboardRowBuilder extends TgmKeyboardRowBuilderBase<TgmKeyboardRowBuilder> {

	public static TgmKeyboardRowBuilder make() {
		return new TgmKeyboardRowBuilder();
	}

	public List<TgmKeyboardButton> build() {
		return keyboardRow;
	}

	@Override
	protected TgmKeyboardRowBuilder getSelf() {
		return this;
	}
}
