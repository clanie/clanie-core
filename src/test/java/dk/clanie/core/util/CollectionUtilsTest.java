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

import static dk.clanie.core.Utils.stream;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

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


}
