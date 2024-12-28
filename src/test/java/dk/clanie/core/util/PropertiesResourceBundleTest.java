/**
 * Copyright (C) 2007-2024, Claus Nielsen, clausn999@gmail.com
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PropertiesResourceBundleTest {

	private static ResourceBundle bundle;

	private static class TestBundle extends PropertiesResourceBundle {
		TestBundle() {
			super(new String[] { //
					"dk/clanie/core/util/PropertiesResourceBundleTestResources1.properties",
					"dk/clanie/core/util/PropertiesResourceBundleTestResources2.properties" });
		}
	}

	@BeforeAll
	public static void init() {
		bundle = new TestBundle();
	}

	@Test
	public void testPropertyFromFirstPropertiesFile() {
		assertThat("A").isEqualTo(bundle.getString("test.a"));
	}

	@Test
	public void testPropertyFromSecondPropertiesFile() {
		assertThat("B").isEqualTo(bundle.getString("test.b"));
	}

	@Test
	public void testNoneexistingProperty() {
		assertThrows(MissingResourceException.class, () -> bundle.getString("test.no_such_property_key"));
	}

}
