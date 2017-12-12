package org.frikadelki.ash.toolset.utils.collections;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public final class CollectionsConvertTests {
	@Test
	public void testIterableFromArray() {
		val e1 = "a";
		val e2 = "b";
		val iterable = CollectionsConvert.iterableFromArray(e1, e2);
		val iterator = iterable.iterator();
		Assert.assertSame(e1, iterator.next());
		Assert.assertSame(e2, iterator.next());
	}
}
