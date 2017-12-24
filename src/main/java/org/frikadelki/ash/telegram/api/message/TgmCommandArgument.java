/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by GeneralGDA on 2016/07/23
 */

package org.frikadelki.ash.telegram.api.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TgmCommandArgument {
	@Getter private final TgmMessageEntity attachedEntity;
	@Getter private final String value;
}
