package org.frikadelki.ash.toolset.utils;

/*
Created by GeneralGDA on 23-Jul-16.
*/

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public final class StringSplit {
	
	@RequiredArgsConstructor
	public static final class StringPiece {
		@Getter private final int startIndex;
		@Getter private final int endIndex;
		@Getter private final String value;
	}

	public static StringPiece[] splitOrEmpty(@NonNull final String input, @NonNull final Pattern delimiter) {
		int index = 0;
		val matchList = new ArrayList<StringPiece>();
		val m = delimiter.matcher(input);

		// Add segments before each match found
		while(m.find()) {
			if (index == 0 && index == m.start() && m.start() == m.end()) {
				// no empty leading substring included for zero-width match
				// at the beginning of the input char sequence.
				continue;
			}
			val match = input.subSequence(index, m.start()).toString();
			matchList.add(new StringPiece(index, m.start(), match));
			index = m.end();
		}

		// If no match was found, return this
		if (index == 0) {
			return new StringPiece[]{};
		}

		matchList.add(new StringPiece(index, input.length(), input.subSequence(index, input.length()).toString()));

		for (final Iterator<StringPiece> iterator = matchList.iterator(); iterator.hasNext();) {
			val next = iterator.next();
			val value = next.getValue();
			if (null == value || "".equals(value.trim()) || next.getEndIndex() == next.getStartIndex()) {
				iterator.remove();
			}
		}

		// Construct result
		int resultSize = matchList.size();
		while (resultSize > 0 && "".equals(matchList.get(resultSize-1).getValue().trim())) {
			resultSize--;
		}

		val result = new StringPiece[resultSize];
		return matchList.subList(0, resultSize).toArray(result);
	}
}
