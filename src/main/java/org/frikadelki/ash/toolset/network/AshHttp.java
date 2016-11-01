/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.network;


import lombok.experimental.UtilityClass;


@UtilityClass
public final class AshHttp {

	@UtilityClass
	public static final class Header {
		public static final String CONTENT_TYPE = "Content-Type";
	}

	@UtilityClass
	public static final class Method {
		public static final String GET = "GET";
		public static final String POST = "POST";
	}

	@UtilityClass
	public static final class Code {
		public static final int _200_OK = 200;
		public static final int _204_NO_CONTENT = 204;

		public static final int _300_MULTIPLE_CHOICES = 300;
		public static final int _301_MOVED_PERMANENTLY = 301;
		public static final int _302_FOUND = 302;
		public static final int _303_SEE_OTHER = 303;
		public static final int _304_MODIFIED = 304;
		public static final int _307_TEMPORARY_REDIRECT = 307;

		public static final int _400_BAD_REQUEST = 400;
		public static final int _401_UNAUTHORIZED = 401;
		public static final int _403_FORBIDDEN = 403;
		public static final int _404_NOT_FOUND = 404;
		public static final int _405_METHOD_NOT_ALLOWED = 405;
		public static final int _409_CONFLICT = 409;

		public static final int _500_SERVER_ERROR = 500;
		public static final int _502_BAD_GATEWAY = 502;
		public static final int _503_SERVICE_UNAVAILABLE = 503;

		public static boolean isSuccessSubtype(final int statusCode) {
			return (statusCode >= 200) && (statusCode < 300);
		}

		public static boolean isBadRequestSubtype(final int statusCode) {
			return (statusCode >= 400) && (statusCode < 500);
		}
	}
}
