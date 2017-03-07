package org.frikadelki.ash.toolset.utils.collections;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;


public final class ArrayIterableTests {
	@Test
	public void testIterator() {
		val emptyArray = new String[] {};
		val emptyIterator = new ArrayIterable.Iterator<>(emptyArray);
		Assert.assertFalse(emptyIterator.hasNext());

		val otherArray = new String[] { "abc", "der", "fold" };
		val otherIterator = new ArrayIterable.Iterator<>(otherArray);
		Assert.assertTrue(otherIterator.hasNext());
		Assert.assertTrue(otherIterator.next().equals("abc"));
		Assert.assertTrue(otherIterator.hasNext());
		Assert.assertTrue(otherIterator.next().equals("der"));
		Assert.assertTrue(otherIterator.hasNext());
		Assert.assertTrue(otherIterator.next().equals("fold"));
		Assert.assertFalse(otherIterator.hasNext());
	}
}
