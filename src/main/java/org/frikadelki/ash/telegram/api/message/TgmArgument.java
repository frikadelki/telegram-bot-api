package org.frikadelki.ash.telegram.api.message;

/*
Created by GeneralGDA on 23-Jul-16.
*/

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TgmArgument
{
	@Getter private final TgmMessageEntity attachedEntity;
	@Getter private final String value;
}
