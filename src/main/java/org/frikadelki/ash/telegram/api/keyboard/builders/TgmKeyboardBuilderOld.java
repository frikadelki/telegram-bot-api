/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/29
 */

package org.frikadelki.ash.telegram.api.keyboard.builders;

import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.telegram.api.keyboard.builders.base.TgmKeyboardBuilderBase;

import java.util.List;


public final class TgmKeyboardBuilderOld extends TgmKeyboardBuilderBase<TgmKeyboardBuilderOld> {

	public static TgmKeyboardBuilderOld make() {
		return new TgmKeyboardBuilderOld();
	}

	public List<List<TgmKeyboardButton>> build() {
		return keyboard;
	}

	@Override
	protected TgmKeyboardBuilderOld getSelf() {
		return this;
	}
}
