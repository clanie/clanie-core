/**
 * Copyright (C) 2009-2012, Claus Nielsen, cn@cn-consult.dk
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

/**
 * Miscellaneous static utility methods.
 * 
 * @author Claus Nielsen
 */
public class Util {

	/**
	 * Selects first non-null argument.
	 *
	 * @param <T>
	 * @param args
	 * @return the first argument which is not null or null if all arguments are null.
	 */
	public static <T> T firstNotNull(@SuppressWarnings("unchecked") T... args) {
		for (T arg : args) {
			if (arg != null)
				return arg;
		}
		return null;
	}

}
