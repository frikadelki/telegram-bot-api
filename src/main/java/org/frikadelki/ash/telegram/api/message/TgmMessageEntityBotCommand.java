/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/10
 */

package org.frikadelki.ash.telegram.api.message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public final class TgmMessageEntityBotCommand {
	@Getter @NonNull private final TgmMessage message;
	@Getter @NonNull private final TgmMessageEntity entity;

	public String getCommandName() {
		return entity.getEntityString(message.getText());
	}
}
