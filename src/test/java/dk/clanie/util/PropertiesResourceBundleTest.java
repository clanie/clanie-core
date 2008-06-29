/**
 * This file is part of the Util library.
 * Copyright (C) 2007, Claus Nielsen, cn@cn-consult.dk
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
package dk.clanie.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Test;

public class PropertiesResourceBundleTest {

	private static ResourceBundle bundle;

	private static class TestBundle extends PropertiesResourceBundle {
		TestBundle() {
			super(
					new String[] {
							"dk/clanie/util/PropertiesResourceBundleTestResources1.properties",
							"dk/clanie/util/PropertiesResourceBundleTestResources2.properties" });
		}
	}

	@BeforeClass
	public static void init() {
		bundle = new TestBundle();
	}

	@Test
	public void testPropertyFromFirstPropertiesFile() {
		assertThat("A", is(bundle.getString("test.a")));
	}

	@Test
	public void testPropertyFromSecondPropertiesFile() {
		assertThat("B", is(bundle.getString("test.b")));
	}

	@Test(expected=MissingResourceException.class)
	public void testNoneexistingProperty() {
		bundle.getString("test.no_such_property_key");
	}

}
