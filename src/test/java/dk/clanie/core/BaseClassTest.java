/**
 * Copyright (C) 2008, Claus Nielsen, cn@cn-consult.dk.dk
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
package dk.clanie.core;

import static org.junit.Assert.*;
import org.junit.Test;

/** Test BaseClass.
 * 
 * @author Claus Nielsen
 */
public class BaseClassTest {

	@SuppressWarnings("serial")
	class ExtendedClass extends BaseClass {
		String id;

		public ExtendedClass(String value) {
			id = value;
		}
	};

	/**
	 * Test equals.
	 * 
	 * Test that equals dosn't use object identity, but actually compares the
	 * values of fields.
	 */
	@Test
	public void testEquals() {
		BaseClass o1 = new ExtendedClass("same");
		BaseClass o2 = new ExtendedClass("same");
		assertTrue(o1.equals(o2));

		BaseClass o3 = new ExtendedClass("other");
		assertFalse(o1.equals(o3));
	}

}
