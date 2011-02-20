/**
 * Copyright (C) 2008, Claus Nielsen, cn@cn-consult.dk
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
package dk.clanie.hamcrest;

import static dk.clanie.hamcrest.CollectionMatchers.sizeEq;
import static dk.clanie.hamcrest.CollectionMatchers.sizeMax;
import static dk.clanie.hamcrest.CollectionMatchers.sizeMin;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Tests CollectionMatchers.
 * 
 * @author Claus Nielsen
 */
public class CollectionMatchersTest {

	@Test
	public void testSizeMatchers() {
		List<String> collection = Arrays.asList(new String[] {"one", "two", "three"});
		// sizeEq should match only <3>
		assertThat(collection, not(sizeEq(2)));
		assertThat(collection, sizeEq(3));
		assertThat(collection, not(sizeEq(4)));
		// sizeMin should match anything <= 3
		assertThat(collection, sizeMin(-1));
		assertThat(collection, sizeMin(2));
		assertThat(collection, sizeMin(3));
		assertThat(collection, not(sizeMin(4)));
		// sizeMax should match anything >= 3
		assertThat(collection, not(sizeMax(-1)));
		assertThat(collection, not(sizeMax(2)));
		assertThat(collection, sizeMax(3));
		assertThat(collection, sizeMax(4));
	}

}
