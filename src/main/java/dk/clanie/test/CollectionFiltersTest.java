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
package dk.clanie.test;

import static dk.clanie.collections.CollectionFilters.removeMatcing;
import static dk.clanie.test.CollectionMatchers.sizeEq;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CollectionFiltersTest {

	private static class Super {
	}
	
	private static class Sub extends Super {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		/*
		 * Most of this is just to make sure I got the generics-voodoo right
		 * and that a superclass-matcher can be used on a collection of it's subclass.
		 */
		Super x1 = new Super();
		Sub y1 = new Sub();
		Sub y2 = new Sub();
		List<Sub> list = new ArrayList<Sub>();
		list.add(y1);
		list.add(y2);
		removeMatcing(list, equalTo(x1));
		removeMatcing(list, equalTo(y1));
		assertThat(list, allOf(sizeEq(1), hasItem(y2)));
	}

}
