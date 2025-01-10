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

import static dk.clanie.core.Utils.ge;
import static dk.clanie.core.Utils.gt;
import static dk.clanie.core.Utils.le;
import static dk.clanie.core.Utils.lt;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BooleanUtilsTest {


	@Test
	void testGt() {
		assertThat(gt(2, 3)).as("gt(2, 3").isFalse();
		assertThat(gt(3, 2)).as("gt(3, 2").isTrue();
		assertThat(gt(3, 3)).as("gt(3, 3").isFalse();
		assertThat(gt(2, null)).as("gt(2, null").isTrue();
		assertThat(gt(null, 4)).as("gt(null, 4").isFalse();
		assertThat(gt(null, null)).as("gt(null, null").isFalse();
	}


	@Test
	void testGe() {
		assertThat(ge(2, 3)).as("ge(2, 3").isFalse();
		assertThat(ge(3, 2)).as("ge(3, 2").isTrue();
		assertThat(ge(3, 3)).as("ge(3, 3").isTrue();
		assertThat(ge(2, null)).as("ge(2, null").isTrue();
		assertThat(ge(null, 4)).as("ge(null, 4").isFalse();
		assertThat(ge(null, null)).as("ge(null, null").isTrue();
	}


	@Test
	void testLt() {
		assertThat(lt(2, 3)).as("lt(2, 3").isTrue();
		assertThat(lt(3, 2)).as("lt(3, 2").isFalse();
		assertThat(lt(3, 3)).as("lt(3, 3").isFalse();
		assertThat(lt(2, null)).as("lt(2, null").isFalse();
		assertThat(lt(null, 4)).as("lt(null, 4").isTrue();
		assertThat(lt(null, null)).as("lt(null, null").isFalse();
	}


	@Test
	void testLe() {
		assertThat(le(2, 3)).as("le(2, 3").isTrue();
		assertThat(le(3, 2)).as("le(3, 2").isFalse();
		assertThat(le(3, 3)).as("le(3, 3").isTrue();
		assertThat(le(2, null)).as("le(2, null").isFalse();
		assertThat(le(null, 4)).as("le(null, 4").isTrue();
		assertThat(le(null, null)).as("le(null, null").isTrue();
	}


}
