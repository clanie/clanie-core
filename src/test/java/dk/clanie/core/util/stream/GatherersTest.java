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
package dk.clanie.core.util.stream;

import static dk.clanie.core.util.stream.Gatherers.grouping;
import static dk.clanie.core.util.stream.Gatherers.mergeSorted;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class GatherersTest {


	@Test
	void testMergeSorting() {
		Stream<Stream<String>> input = Stream.of(
				Stream.of("a", "f", "z"),
				Stream.of("b", "g", "x", "y"),
				Stream.of("h"));
		String result = input
				.gather(mergeSorted(Comparator.comparing(String::toString)))
				.collect(Collectors.joining(", "));
		assertThat(result).isEqualTo("a, b, f, g, h, x, y, z");
	}


	@Test
	void testMergeSortingStopsPulling() {
		int[] pullCount = new int[] { 0 };
		Stream<Stream<String>> input = Stream.of(
				Stream.of("a", "f", "k", "z").peek(_ -> pullCount[0]++),
				Stream.of("b", "g", "j", "x", "y").peek(_ -> pullCount[0]++),
				Stream.of("h", "i").peek(_ -> pullCount[0]++));
		String result = input
				.gather(mergeSorted(Comparator.comparing(String::toString)))
				.limit(4)
				.collect(Collectors.joining(", "));
		assertThat(result).isEqualTo("a, b, f, g");
		assertThat(pullCount[0]).as("Gatherer should pull no more elements than needed in the output (4) + at most one more element per input stream (3).").isEqualTo(7);
	}


	@Test
	void testGrouping() {
		Stream<Integer> input = Stream.of(1, 1, 2, 2, 3, 3, 3, 4, 4);
		List<List<Integer>> result = input
				.gather(grouping(Comparator.naturalOrder())) // Group same values together
				.toList(); // Collect the result
		assertThat(result.size()).as("There should have been 4 groups").isEqualTo(4);
		assertThat(result.get(0)).isEqualTo(List.of(1, 1));
		assertThat(result.get(1)).isEqualTo(List.of(2, 2));
		assertThat(result.get(2)).isEqualTo(List.of(3, 3, 3));
		assertThat(result.get(3)).isEqualTo(List.of(4, 4));
	}


	@Test
	void testGroupingStopsPulling() {
		int[] pullCount = new int[] { 0 };
		Stream<Integer> input = Stream.of(1, 1, 2, 2, 3, 3, 4, 4);
		List<List<Integer>> result = input
				.peek(_ -> pullCount[0]++)
				.gather(grouping(Comparator.naturalOrder()))
				.limit(2)
				.toList();
		assertThat(result.size()).as("Gatherer should have stopped after 2 groups").isEqualTo(2);
		assertThat(pullCount[0]).as("Gatherer should pull only enough elements to form the first 2 groups").isEqualTo(5);
	}


}
