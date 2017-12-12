package org.frikadelki.ash.toolset.utils.collections;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;


public final class ArrayIterableTests {
	@Test
	public void testEmptyIterator() {
		val emptyArray = new String[] {};
		val emptyIterator = new ArrayIterable.Iterator<>(emptyArray);
		Assert.assertFalse(emptyIterator.hasNext());
	}

	@Test
	public void testFullIteratorConsume() {
		val array = new String[] { "abc", "der", "fold" };
		val iterator = new ArrayIterable.Iterator<>(array);
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(array[0], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(array[1], iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(array[2], iterator.next());
		Assert.assertFalse(iterator.hasNext());
	}

	@Test(expected = IllegalStateException.class)
	public void testIteratorNextAfterLast() {
		val array = new String[] { "abc" };
		val iterator = new ArrayIterable.Iterator<>(array);
		Assert.assertTrue(iterator.hasNext());
		Assert.assertSame(iterator.next(), array[0]);
		Assert.assertFalse(iterator.hasNext());
		iterator.next();
	}
}
