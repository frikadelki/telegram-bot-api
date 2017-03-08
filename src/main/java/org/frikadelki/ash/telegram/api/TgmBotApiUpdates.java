/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/10/25
 */

package org.frikadelki.ash.telegram.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.telegram.api.update.TgmUpdate;
import org.frikadelki.ash.toolset.result.AshResult;
import org.frikadelki.ash.toolset.result.AshResultGist;


public interface TgmBotApiUpdates {

	/**
	 * Use this method to receive incoming updates using long polling (wiki). An Array of Update objects is returned.
	 *
	 * @param params parameters to use for query
	 * @return result with list of updates of error on failure
	 */
	AshResult<TgmUpdate[]> getUpdates(final UpdatesParams params);

	@AllArgsConstructor
	@Builder(builderClassName = "Builder")
	final class UpdatesParams {
		/**
		 * Optional
		 * Identifier of the first update to be returned. Must be greater by one than the highest
		 * among the identifiers of previously received updates. By default, updates starting with
		 * the earliest unconfirmed update are returned. An update is considered confirmed as soon as
		 * getUpdates is called with an offset higher than its update_id. The negative offset can be
		 * specified to retrieve updates starting from -offset update from the end of the updates queue.
		 * All previous updates will forgotten.
		 */
		private Long offset = null;

		/**
		 * Optional
		 * Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100.
		 */
		private Long limit = null;

		/**
		 * Optional
		 * Timeout in seconds for long polling. Defaults to 0, i.e.
		 * usual short polling. Should be positive, short polling should
		 * be used for testing purposes only.
		 */
		private Long timeout = null;
	}

	/**
	 * Use this method to specify a url and receive incoming updates via an outgoing webhook.
	 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified
	 * url, containing a JSON-serialized Update. In case of an unsuccessful request, we will give up
	 * after a reasonable amount of attempts.
	 *
	 * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a
	 * secret path in the URL, e.g. https://www.example.com/<token>. Since nobody else knows your bot‘s
	 * token, you can be pretty sure it’s us.
	 *
	 * @param params webhook configuration parameters
	 * @return result indicating success/failure status
	 */
	AshResultGist setWebhook(final WebhookParams params);

	@RequiredArgsConstructor
	final class WebhookParams {
		/**
		 * Optional
		 * HTTPS url to send updates to. Use an empty string to remove webhook integration
		 */
		private final String url;

		/** TODO
		 * certificate	InputFile	Optional	Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide for details.
		 */
	}
}
