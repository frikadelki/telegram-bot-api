/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.runtime;

import org.frikadelki.ash.toolset.result.AshResult;

import java.io.IOException;
import java.net.URL;


public interface TgmQueryIO {
	/**
	 * @param url url to query
	 * @param jsonParams query params serialized as json string, can be null
	 * @return result containing either response body content as a
	 * string (which can be null in case of an empty response) or an error
	 * @throws IOException indicates some kind of low-level io/connection problems which prevented query from proper completion
	 */
	AshResult<String> query(URL url, String jsonParams) throws IOException;
}
