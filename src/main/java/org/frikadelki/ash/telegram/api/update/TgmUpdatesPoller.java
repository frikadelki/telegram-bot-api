/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/3/8
 */

package org.frikadelki.ash.telegram.api.update;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.val;
import org.frikadelki.ash.telegram.api.TgmBotApi;
import org.frikadelki.ash.telegram.api.TgmBotApiUpdates;
import org.frikadelki.ash.toolset.utils.Lambda;


@Log
@RequiredArgsConstructor
public final class TgmUpdatesPoller {
	private static final long POLL_CYCLE_TIMEOUT_SECONDS = 10L;

	@NonNull private final TgmBotApi botApi;

	@Setter private Lambda.Code1<TgmUpdate> onUpdateHandler;
	@Setter private Lambda.Code0 onPollCycleCompleted;

	private long offset = -1L;

	public void poll(final long timeoutMillis) {
		long timeSpent = 0;
		while (timeSpent < timeoutMillis) {
			val pollStartTime = System.currentTimeMillis();

			pollCycle();
			Lambda.safeCall(onPollCycleCompleted);

			timeSpent += System.currentTimeMillis() - pollStartTime;
		}
	}

	private void pollCycle() {
		LOG.fine("[poll-cycle::start-cycle]");
		val pollParams = new TgmBotApiUpdates.UpdatesParams(offset + 1, null, POLL_CYCLE_TIMEOUT_SECONDS);
		val updatesResult = botApi.getUpdatesApi().getUpdates(pollParams);
		if (updatesResult.isSuccess()) {
			for (val update : updatesResult.getData()) {
				offset = Math.max(update.getUpdateId(), offset);
				LOG.fine("[poll-cycle::got-message]:\n <<< " + update.getNewMessage().getText());
				Lambda.safeCall(onUpdateHandler, update);
			}
		} else {
			LOG.severe("[poll-cycle::cycle-failed]:\n <<< " + updatesResult.getError().toString());
		}
	}
}
