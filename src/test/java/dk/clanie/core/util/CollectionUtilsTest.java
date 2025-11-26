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
import static dk.clanie.core.util.CollectionUtils.asList;
import static dk.clanie.core.util.CollectionUtils.asMap;
import static dk.clanie.core.util.CollectionUtils.asSet;
import static dk.clanie.core.util.CollectionUtils.filterList;
import static dk.clanie.core.util.CollectionUtils.filterSet;
import static dk.clanie.core.util.CollectionUtils.mapList;
import static dk.clanie.core.util.CollectionUtils.mapSet;
import static dk.clanie.core.util.SortDirection.ASC;
import static dk.clanie.core.util.SortDirection.DESC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


	@Test
	void testAsList_returnsUnmodifiable() {
		List<String> mutableList = new ArrayList<>(List.of("a", "b", "c"));
		List<String> result = asList(mutableList);
		
		assertThat(result).containsExactly("a", "b", "c");
		assertThatThrownBy(() -> result.add("d"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.remove(0))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.set(0, "x"))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testAsList_fromIterable_returnsUnmodifiable() {
		Iterable<String> iterable = Set.of("a", "b", "c");
		List<String> result = asList(iterable);
		
		assertThat(result).hasSize(3);
		assertThatThrownBy(() -> result.add("d"))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testAsSet_returnsUnmodifiable() {
		Set<String> mutableSet = new HashSet<>(Set.of("a", "b", "c"));
		Set<String> result = asSet(mutableSet);
		
		assertThat(result).containsExactlyInAnyOrder("a", "b", "c");
		assertThatThrownBy(() -> result.add("d"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.remove("a"))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testAsSet_fromIterable_returnsUnmodifiable() {
		Iterable<String> iterable = List.of("a", "b", "c");
		Set<String> result = asSet(iterable);
		
		assertThat(result).hasSize(3);
		assertThatThrownBy(() -> result.add("d"))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testAsMap_returnsUnmodifiable() {
		List<String> list = List.of("a", "bb", "ccc");
		Map<Integer, String> result = asMap(list, String::length);
		
		assertThat(result).hasSize(3);
		assertThatThrownBy(() -> result.put(4, "dddd"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.remove(1))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testAsMapWithValueMapper_returnsUnmodifiable() {
		List<String> list = List.of("a", "bb", "ccc");
		Map<Integer, String> result = asMap(list, String::length, String::toUpperCase);
		
		assertThat(result).hasSize(3);
		assertThatThrownBy(() -> result.put(4, "DDDD"))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testFilterList_returnsUnmodifiable() {
		List<Integer> list = List.of(1, 2, 3, 4, 5);
		List<Integer> result = filterList(list, n -> n > 2);
		
		assertThat(result).containsExactly(3, 4, 5);
		assertThatThrownBy(() -> result.add(6))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.remove(0))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testFilterSet_returnsUnmodifiable() {
		Set<Integer> set = Set.of(1, 2, 3, 4, 5);
		Set<Integer> result = filterSet(set, n -> n > 2);
		
		assertThat(result).containsExactlyInAnyOrder(3, 4, 5);
		assertThatThrownBy(() -> result.add(6))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.remove(3))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testMapList_returnsUnmodifiable() {
		List<String> list = List.of("a", "b", "c");
		List<String> result = mapList(list, String::toUpperCase);
		
		assertThat(result).containsExactly("A", "B", "C");
		assertThatThrownBy(() -> result.add("D"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.remove(0))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.set(0, "X"))
			.isInstanceOf(UnsupportedOperationException.class);
	}


	@Test
	void testMapSet_returnsUnmodifiable() {
		Set<String> set = Set.of("a", "b", "c");
		Set<String> result = mapSet(set, String::toUpperCase);
		
		assertThat(result).containsExactlyInAnyOrder("A", "B", "C");
		assertThatThrownBy(() -> result.add("D"))
			.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> result.remove("A"))
			.isInstanceOf(UnsupportedOperationException.class);
	}


}
