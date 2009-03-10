/**
 * Copyright (C) 2009, Claus Nielsen, cn@cn-consult.dk
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

import static dk.clanie.test.CollectionMatchers.sizeEq;
import static dk.clanie.util.Util.list;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test Util.
 * 
 * @author Claus Nielsen
 */
public class UtilTest {

	@Test
	public void testFirstNotNull() {
		assertThat("it", is(equalTo(Util.firstNotNull("it"))));
		assertThat("it", is(equalTo(Util.firstNotNull("it", "not"))));
		assertThat("it", is(equalTo(Util.firstNotNull(null, "it", null, "boo"))));
		assertThat(null, is(equalTo(Util.firstNotNull())));
		assertThat(null, is(equalTo(Util.firstNotNull((String)null))));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testList() {
		assertThat(list("a", "b", "c"), allOf(hasItems("a", "b", "c"), sizeEq(3)));
		assertThat(list(4, 5, 6), allOf(hasItems(4, 5, 6), sizeEq(3)));
	}

}
