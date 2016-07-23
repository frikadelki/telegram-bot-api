package org.frikadelki.ash.toolset.utils;

/*
Created by GeneralGDA on 23-Jul-16.
*/

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringSplit
{
	@RequiredArgsConstructor
	public static final class StringPiece
	{
		@Getter private final int startIndex;
		@Getter private final int endIndex;
		@Getter private final String value;
	}

	public static StringPiece[] split(@NonNull final String input, @NonNull final Pattern delimiter) {
		int index = 0;
		ArrayList<StringPiece> matchList = new ArrayList<>();
		Matcher m = delimiter.matcher(input);

		// Add segments before each match found
		while(m.find()) {
			if (index == 0 && index == m.start() && m.start() == m.end()) {
				// no empty leading substring included for zero-width match
				// at the beginning of the input char sequence.
				continue;
			}
			String match = input.subSequence(index, m.start()).toString();
			matchList.add(new StringPiece(index, m.start(), match));
			index = m.end();
		}

		// If no match was found, return this
		if (index == 0) {
			return new StringPiece[]{new StringPiece(0, input.length(), input)};
		}

		matchList.add(new StringPiece(index, input.length(), input.subSequence(index, input.length()).toString()));

		for (Iterator<StringPiece> iterator = matchList.iterator(); iterator.hasNext();) {
			final StringPiece next = iterator.next();
			if ("".equals(next.getValue())) {
				iterator.remove();
			}
		}

		// Construct result
		int resultSize = matchList.size();
		while (resultSize > 0 && "".equals(matchList.get(resultSize-1).getValue())) {
			resultSize--;
		}

		final StringPiece[] result = new StringPiece[resultSize];
		return matchList.subList(0, resultSize).toArray(result);
	}
}
