/**
 * Copyright (C) 2008-2024, Claus Nielsen, clausn999@gmail.com
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


import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Test Enumeration of MessageDefinitions.
 * <p>
 * Tests an Enumeration of MessageDefinitions against it's ResourceBundle,
 * checking that each Message is defined in the ResourceBundle, and that
 * there are no unused entries in the ResourceBundle.
 * </p><p>
 * Assumes that all MessageDefinitions in the Enumeration are defined in
 * the same ResourceBundle.
 * </p>
 * @author Claus Nielsen
 */
public class MessageEnumTest {

	
	public static <T extends Enum<T> & MessageDefinition> void testMessageEnumeration(Class<T> messageEnum) {
		EnumSet.allOf(messageEnum); 
		T[] enumConstants = messageEnum.getEnumConstants();
		ResourceBundle bundle = enumConstants[0].getBundle();
		Set<String> unusedKeys = bundle.keySet();
		List<String> missingKeys = new ArrayList<>();
		for (T t : enumConstants) {
			if (unusedKeys.remove(t.key()) || missingKeys.add(t.key()));
		}
		// If there are unused or missing keys fail with a message identifying them.
		if (unusedKeys.size() > 0 || missingKeys.size() > 0) {
			StringBuilder builder = new StringBuilder();
			if (missingKeys.size() > 0) {
				builder.append("Missing keys: ");
				for (String key : missingKeys) {
					builder.append(key).append(", ");
				}
				builder.setLength(builder.length() - ", ".length());
			}
			if (unusedKeys.size() > 0) {
				if (builder.length() > 0) builder.append("; ");
				builder.append("Unused keys: ");
				for (String key : unusedKeys) {
					builder.append(key).append(", ");
				}
				builder.setLength(builder.length() - ", ".length());
			}
			fail(builder.toString());
		}
	}

}
