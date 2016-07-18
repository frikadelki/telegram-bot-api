// ASH Toolset
// Copyright 2016 ASH Dev Team
// Created by ein on 2016/5/20

package org.frikadelki.ash.toolset.utils;

import lombok.NonNull;

import java.io.*;
import java.nio.charset.Charset;


public final class IOBits {
	/**
	 * @param stream stream to read from, it will be closed whatever happens
	 * @param charset charset to use while reading
	 * @param estimatedSize pass negative value if you don't know
	 * @return string from stream. If stream is null or exception is thrown while reading - null is returned.
	 */
	public static String streamToString(final InputStream stream, @NonNull final Charset charset, final int estimatedSize) {
		if( null == stream ) {
			return null;
		}

		final char[] BUFFER = new char[512];
		final Reader bufferedReader = new InputStreamReader(stream, charset);
		final StringWriter writer = new StringWriter((estimatedSize > 0) ? estimatedSize : BUFFER.length);
		try {
			while( true ) {
				final int charsCount = bufferedReader.read(BUFFER);
				if( charsCount < 0 ) {
					break;
				}
				writer.write(BUFFER, 0, charsCount);
			}
			return writer.toString();
		}
		catch( final IOException ignored ) {
			return null;
		}
		finally {
			close(bufferedReader);
		}
	}

	public static void close(final Closeable closeable) {
		if (null == closeable) {
			return;
		}

		try {
			closeable.close();
		}
		catch (final IOException ignored) {
		}
	}
}
