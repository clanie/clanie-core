/**
 * Copyright (C) 2007, 2008, Claus Nielsen, cn@cn-consult.dk
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package dk.clanie.util;

import static dk.clanie.util.CollectionFactory.newHashSet;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

public class ResourceBundleEnumeratorTest {

	@Test
	public void test() {
		Set<String> set = newHashSet();
		set.add("one");
		set.add("two");
		Enumeration<String> enumeration = new Enumeration<String>() {
			String[] strings = { "three", "four" };
			int i = 0;

			@Override
			public boolean hasMoreElements() {
				return i < 2;
			}

			@Override
			public String nextElement() {
				return strings[i++];
			}
		};
		ResourceBundleEnumeration bundleEnumeration = new ResourceBundleEnumeration(
				set, enumeration);
		;
		Set<String> values = newHashSet();
		for(int i = 0; i < 4; i++) {
			String element = bundleEnumeration.nextElement();
			values.add(element);
		}
		assertTrue(values.contains("one"));
		assertTrue(values.contains("two"));
		assertTrue(values.contains("three"));
		assertTrue(values.contains("four"));
		try {
			bundleEnumeration.nextElement();
			fail("Expected " + NoSuchElementException.class.getName());
		} catch (NoSuchElementException nsee) {
			// OK
		}
	}

}
