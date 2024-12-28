/**
 * Copyright (C) 2009-2024, Claus Nielsen, clausn999@gmail.com
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

import static dk.clanie.core.Utils.firstNonNull;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test Util.
 * 
 * @author Claus Nielsen
 */
public class MiscUtilsTest {

	@Test
	public void testFirstNotNull() {
		assertThat("it").isEqualTo(firstNonNull("it"));
		assertThat("it").isEqualTo(firstNonNull("it", "not"));
		assertThat("it").isEqualTo(firstNonNull(null, "it", null, "boo"));
		assertThat((String)null).isEqualTo(firstNonNull());
		assertThat((String)null).isEqualTo(firstNonNull((String)null));
	}

}
