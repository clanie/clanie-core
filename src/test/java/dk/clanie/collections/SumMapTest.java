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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test SumMap.
 * 
 * @author Claus Nielsen
 */
public class SumMapTest {

	@Test
	public void test() {
		SumMap<String, Integer> intSumMap = new SumMap<String, Integer>(Integer.valueOf(0));
		intSumMap.add("x", 1);
		intSumMap.add("y", 2);
		intSumMap.add("x", 3);
		assertThat(intSumMap.get("x"), is(4));
		assertThat(intSumMap.get("y"), is(2));
	}

}
