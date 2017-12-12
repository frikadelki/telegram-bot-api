package org.frikadelki.ash.toolset.utils.collections;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public final class StreamCompatTests {
	@Test
	public void testIteratorsArrayConcat() {
		val collection1 = Arrays.asList("a", "b");
		val collection2 = Arrays.asList("c", "d");
		val concatIterable = StreamCompat.concat(collection1, collection2);
		val concatIterator = concatIterable.iterator();
		Assert.assertSame(collection1.get(0), concatIterator.next());
		Assert.assertSame(collection1.get(1), concatIterator.next());
		Assert.assertSame(collection2.get(0), concatIterator.next());
		Assert.assertSame(collection2.get(1), concatIterator.next());
	}
}
