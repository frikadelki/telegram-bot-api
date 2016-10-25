/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/10/24
 */

package org.frikadelki.ash.telegram.runtime;

import lombok.val;
import org.frikadelki.ash.telegram.TgmConstants;
import org.frikadelki.ash.toolset.network.AshHttp;
import org.frikadelki.ash.toolset.network.AshNetworkErrors;
import org.frikadelki.ash.toolset.result.AshResult;
import org.frikadelki.ash.toolset.utils.AshAssert;
import org.frikadelki.ash.toolset.utils.IOBits;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public final class TgmQueryIODefault implements TgmQueryIO {
	@Override
	public AshResult<String> query(final URL url, final String jsonBody) throws IOException {
		val connection = url.openConnection();
		AshAssert.aNonNull(connection);
		if (null == connection) {
			throw new IOException("Connection failed.");
		}
		AshAssert.aTrue(connection instanceof HttpURLConnection);
		if (!(connection instanceof HttpURLConnection)) {
			throw new IOException("Only HTTP transport is supported.");
		}

		val httpConnection = (HttpURLConnection)connection;
		val postJsonBody = (null != jsonBody);
		httpConnection.setRequestMethod(postJsonBody ? AshHttp.Method.POST : AshHttp.Method.GET);
		if (postJsonBody) {
			httpConnection.addRequestProperty(AshHttp.Header.CONTENT_TYPE, TgmConstants.BODY_JSON_CONTENT_TYPE);
			httpConnection.setDoOutput(true);
			val outputStream = httpConnection.getOutputStream();
			outputStream.write(jsonBody.getBytes(TgmConstants.CHARSET));
			IOBits.flush(outputStream);
			IOBits.close(outputStream);
		}

		val httpResponseCode = httpConnection.getResponseCode();
		if (AshHttp.Code.isSuccessSubtype(httpResponseCode) || AshHttp.Code.isBadRequestSubtype(httpResponseCode)) {
			val httpSuccess = AshHttp.Code.isSuccessSubtype(httpResponseCode);
			val replyInputStream =  httpSuccess ? connection.getInputStream() : httpConnection.getErrorStream();
			val replyJsonBody = IOBits.streamToString(replyInputStream, TgmConstants.CHARSET, -1);
			return new AshResult<>(replyJsonBody);
		} else {
			return new AshResult<>(AshNetworkErrors.http(httpResponseCode, httpConnection.getResponseMessage()).build());
		}
	}
}
