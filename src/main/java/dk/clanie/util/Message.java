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
package dk.clanie.util;

import java.util.ResourceBundle;

/** Message Interface.
 * 
 * When using <code>String</code> constants to refer to messages
 * in ResourceBundles (property files), it's easy to forget to
 * define a message and to type it's key in wrong when referring
 * to it.
 * 
 * If you declare all your message keys in an <code>enum</code>
 * which implements this interface, and define them all in one
 * ResourceBundle (or property file), you can use
 * {@link MessageEnumTest} to test that you aren't
 * referring to undefined messages. It will also check if
 * the ResourceBundle has unused entries in it.
 * 
 * For an example of how to do this, refer to the unit-test
 * <code>MessageEnumTestTest</code>.
 * 
 * @author Claus Nielsen
 */
public interface Message {

	String key();
	String text(Object... args);
	ResourceBundle getBundle();

}
