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
package dk.clanie.collections;

import static dk.clanie.hamcrest.CollectionMatchers.sizeEq;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class FilteringIteratorTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		List<String> aList = asList("one", "or", "another");
		FilteringIterator<String> iter = new FilteringIterator<String>(aList.iterator(), startsWith("o"));
		List<? extends String> filteredList = iter.list();
		assertThat(filteredList, allOf(sizeEq(2), hasItem("one"), hasItem("or")));
	}

}
