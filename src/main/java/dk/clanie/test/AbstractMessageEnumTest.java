/**
 * Copyright (C) 2008, Claus Nielsen, cn@cn-consult.dk.dk
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
package dk.clanie.test;

import static junit.framework.Assert.fail;

import java.util.ResourceBundle;
import java.util.Set;

import dk.clanie.util.Message;

/**
 * Test Enumeration of Messages.
 * 
 * Tests an Enumeration of Messages against it's ResourceBundle, checking that
 * each Message is defined in the ResourceBundle, and that there are no unused
 * entries in the ResourceBundle.
 * 
 * Assumes that all Messages in the Enumeration are defined in the same
 * ResourceBundle.
 * 
 * @author Claus Nielsen
 */
public abstract class AbstractMessageEnumTest {

	protected <T extends Enum<T> & Message> void testMessageEnumeration(
			Class<T> messageEnum) {
		T[] enumConstants = messageEnum.getEnumConstants();
		ResourceBundle bundle = enumConstants[0].getBundle();
		Set<String> bundleKeys = bundle.keySet();
		for (T t : enumConstants) {
			t.text(); // May throw MissingResourceException
			bundleKeys.remove(t.key());
		}
		// If there are unused keys fail with a message identifying them.
		if (bundleKeys.size() > 0) {
			StringBuffer buf = new StringBuffer("Unused message keys: ");
			for (String key : bundleKeys) {
				buf.append(key).append(", ");
			}
			fail(buf.substring(0, buf.length() - ", ".length()));
		}
	}

}
