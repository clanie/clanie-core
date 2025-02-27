/**
 * Copyright (C) 2025, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.core.util;

import static dk.clanie.core.Utils.isSorted;
import static dk.clanie.core.Utils.stream;
import static dk.clanie.core.util.SortDirection.ASC;
import static dk.clanie.core.util.SortDirection.DESC;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import dk.clanie.core.collections.Tuple.Pair;

class CollectionUtilsTest {


	@Test
	void testStreamIterable() {
		List<Integer> ints = List.of(1, 2, 3);
		assertThat(stream(ints).mapToInt(Integer::intValue).sum()).isEqualTo(6);
	}


	@Test
	void testStreamArray() {
		Integer[] ints = {1, 2, 3};
		assertThat(stream(ints).mapToInt(Integer::intValue).sum()).isEqualTo(6);
	}


	@Test
	void testIsSorted_sortedList() {
		List<Pair<String, Integer>> sortedList = List.of(
				Pair.of("a", 1),
				Pair.of("c", 2),
				Pair.of("x", 3));
		assertThat(isSorted(sortedList, Pair::get1st, ASC)).isTrue();
		assertThat(isSorted(sortedList, Pair::get1st, DESC)).isFalse();
	}


	@Test
	void testIsSorted_unsortedList() {
		List<Pair<String, Integer>> unsortedList = List.of(
				Pair.of("a", 1),
				Pair.of("x", 3),
				Pair.of("c", 2));
		assertThat(isSorted(unsortedList, Pair::get1st, ASC)).isFalse();
		assertThat(isSorted(unsortedList, Pair::get1st, DESC)).isFalse();
	}


}
