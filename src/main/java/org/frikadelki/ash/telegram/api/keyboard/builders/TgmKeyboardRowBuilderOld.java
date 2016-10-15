/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/29
 */

package org.frikadelki.ash.telegram.api.keyboard.builders;

import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.telegram.api.keyboard.builders.base.TgmKeyboardRowBuilderBase;

import java.util.List;


public final class TgmKeyboardRowBuilderOld extends TgmKeyboardRowBuilderBase<TgmKeyboardRowBuilderOld> {

	public static TgmKeyboardRowBuilderOld make() {
		return new TgmKeyboardRowBuilderOld();
	}

	public List<TgmKeyboardButton> build() {
		return keyboardRow;
	}

	@Override
	protected TgmKeyboardRowBuilderOld getSelf() {
		return this;
	}
}
