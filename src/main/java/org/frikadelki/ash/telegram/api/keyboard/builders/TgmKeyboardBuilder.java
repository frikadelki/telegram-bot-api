/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/29
 */

package org.frikadelki.ash.telegram.api.keyboard.builders;

import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.telegram.api.keyboard.builders.base.TgmKeyboardBuilderBase;

import java.util.List;


public final class TgmKeyboardBuilder extends TgmKeyboardBuilderBase<TgmKeyboardBuilder> {

	public static TgmKeyboardBuilder make() {
		return new TgmKeyboardBuilder();
	}

	public List<List<TgmKeyboardButton>> build() {
		return keyboard;
	}

	@Override
	protected TgmKeyboardBuilder getSelf() {
		return this;
	}
}
