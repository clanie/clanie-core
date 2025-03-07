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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class GatherersTest {


	@Test
	void test() {
		Stream<Stream<String>> input = Stream.of(
				Stream.of("a", "f", "z"),
				Stream.of("b", "g", "x", "y"),
				Stream.of("h"));
		String result = input
				.gather(Gatherers.mergeSorting(Comparator.comparing(String::toString)))
				.collect(Collectors.joining(", "));
		assertThat(result).isEqualTo("a, b, f, g, h, x, y, z");
	}


}
