/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/11/5
 */

package org.frikadelki.ash.telegram.bot.commands;

import lombok.*;
import org.frikadelki.ash.telegram.api.update.TgmUpdateFilter;


@RequiredArgsConstructor
@EqualsAndHashCode(of = {"name"})
public final class TgmCommand {
	@Getter @NonNull private final String name;
	@Getter @NonNull private final String description;

	@Getter(AccessLevel.PACKAGE) @NonNull private final TgmUpdateFilter dispatchFilter;
	@Getter(AccessLevel.PACKAGE) @NonNull private final TgmCommandBody commandBody;
}
