/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.network;

import org.frikadelki.ash.toolset.result.error.AshError;
import org.frikadelki.ash.toolset.result.error.AshErrorDomain;

import java.io.IOException;


public enum AshNetworkErrors implements AshErrorDomain {
	GENERIC,
	CONNECTION,
	HTTP,
	PARSER,
	PROTOCOL,
	;

	@Override
	public AshError.Builder error() {
		return AshError.builder().errorDomain(this);
	}

	public static AshError.Builder generic(final String debug) {
		return GENERIC.error().debugDescription(debug);
	}

	public static AshError.Builder connection(final IOException exception) {
		return CONNECTION.error().debugDescription(exception.getMessage());
	}

	public static AshError.Builder http(final int code, final String statusLine) {
		return HTTP.error().code(code).debugDescription(statusLine);
	}

	public static AshError.Builder parser() {
		return PARSER.error();
	}

	public static AshError.Builder protocol(final long code) {
		return PROTOCOL.error().code(code);
	}
}
