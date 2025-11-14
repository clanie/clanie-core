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

import static dk.clanie.core.util.StringUtils.csv;
import static dk.clanie.core.util.StringUtils.truncateToUtf8bytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringUtilsTest {


	@Test
	void testCsv_null() {
		assertThat(csv(null)).isEqualTo("");
	}


	@ParameterizedTest
	@CsvSource({
		",                 0,      ",              // null input
		"test,             0,    ''",
		"hello,           10,    hello",
		"hello,            5,    hello",
		"'hello world',    5,    hello",
		"café,             4,    caf",             // café = 5 bytes (c=1, a=1, f=1, é=2)
		"café,             5,    café",            // café = 5 bytes
		"'test€',          6,    test",            // € = 3 bytes, test€ = 7 bytes
		"'test€',          7,    'test€'",         // € = 3 bytes, test€ = 7 bytes
		"'hi😀',           2,    hi",              // 😀 = 4 bytes, hi😀 = 6 bytes
		"'hi😀',           5,    hi",              // 😀 = 4 bytes
		"'hi😀',           6,    'hi😀'",          // 😀 = 4 bytes
		"'hello€world',    7,    hello",            // € = 3 bytes, hello€world = 13 bytes
		"'hello€world',   13,     'hello€world'",   // € = 3 bytes
		"'😀😁😂',         4,    😀",              // 😀 = 4 bytes, 😁 = 4 bytes, 😂 = 4 bytes
		"'😀😁😂',         8,    😀😁",            // total = 12 bytes
		"'😀😁😂',        12,    '😀😁😂'"         // total = 12 bytes
	})
	void testTruncateToUtf8bytes(String input, int maxBytes, String expected) {
		assertThat(truncateToUtf8bytes(input, maxBytes)).isEqualTo(expected);
	}


	@Test
	void testTruncateToUtf8bytes_negativeMaxBytes() {
		assertThatThrownBy(() -> truncateToUtf8bytes("test", -1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("maxBytes must be non-negative");
	}


}
