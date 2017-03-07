/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.runtime;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.NonNull;
import org.frikadelki.ash.telegram.TgmConstants;
import org.frikadelki.ash.telegram.api.base.TgmResponse;
import org.frikadelki.ash.toolset.network.AshNetworkErrors;
import org.frikadelki.ash.toolset.result.AshResult;
import org.frikadelki.ash.toolset.result.AshResultMake;
import org.frikadelki.ash.toolset.result.error.AshError;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public final class TgmBotRuntime {

	private final URL baseUrl;
	private final TgmQueryIO queryIO;

	public TgmBotRuntime(@NonNull final String botToken, @NonNull final TgmQueryIO queryIO) {
		try {
			baseUrl = new URL(TgmConstants.makeBaseBotApiUrl(botToken));
		}
		catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
		this.queryIO = queryIO;
	}

	private URL makeMethodUrl(final String method) throws IOException {
		return new URL(baseUrl, method);
	}

	/**
	 * Synchronous.
	 * @param methodName method to query
	 * @param jsonParams object to be serialized to json, and sent as method params
	 * @param dataClass  result's data class to parse from telegram response, pass Void.class if you don't know or don't care
	 * @return result containing either response of the requested type or an error
	 */
	public <TData> AshResult<TData> query(@NonNull final String methodName, final Object jsonParams, @NonNull final Class<TData> dataClass) {
		try {
			final URL methodUrl = makeMethodUrl(methodName);
			final String jsonBodyString = TgmJsonIO.toJson(jsonParams);
			final AshResult<String> queryResult = queryIO.query(methodUrl, jsonBodyString);
			if (queryResult.isSuccess()) {
				return parseResponse(queryResult.getData(), dataClass);
			} else {
				return AshResultMake.error(queryResult.getError());
			}
		}
		catch (final IOException exception) {
			final AshError error = AshNetworkErrors.connection(exception).build();
			return AshResultMake.error(error);
		}
		catch (final JsonParseException exception) {
			final AshError error = AshNetworkErrors.parser().debugDescription(exception.getMessage()).build();
			return AshResultMake.error(error);
		}
		catch (final Exception exception) {
			final AshError error = AshNetworkErrors.GENERIC.error().debugDescription(exception.getMessage()).build();
			return AshResultMake.error(error);
		}
	}

	private <TData> AshResult<TData> parseResponse(final String response, @NonNull final Class<TData> resultClass) throws JsonParseException {
		final JsonElement jsonResponse = TgmJsonIO.parseJsonElement(response);
		if (null == jsonResponse) {
			final AshError error = AshNetworkErrors.protocol(-1).debugDescription("No response.").build();
			return AshResultMake.error(error);
		}
		final TgmResponse tgmResponse = TgmJsonIO.fromJson(jsonResponse, TgmResponse.class);
		if (null == tgmResponse) {
			final AshError error = AshNetworkErrors.protocol(-2).debugDescription("No response.").build();
			return AshResultMake.error(error);
		}

		if (tgmResponse.isOk()) {
			if (Void.class == resultClass) {
				return AshResultMake.success();
			} else {
				final TData data = TgmJsonIO.fromJson(tgmResponse.getResult(), resultClass);
				return AshResultMake.success(data);
			}
		} else {
			final AshError error = AshNetworkErrors
					.protocol(tgmResponse.getErrorCode())
					.debugDescription(tgmResponse.getDescription())
					.build();
			return AshResultMake.error(error);
		}
	}
}
