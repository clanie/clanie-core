/**
 * Copyright (C) 2024, Claus Nielsen, clausn999@gmail.com
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

import java.util.Objects;

public class BooleanUtils {


	public BooleanUtils() {
		// Not meant to be instantiated
	}


	/**
	 * Statically importable alias for Objects.equals.
	 */
	public static boolean eq(Object o1, Object o2) {
		return Objects.equals(o1, o2);
	}


	/**
	 * Statically importable alias for !Objects.equals.
	 */
	public static boolean ne(Object o1, Object o2) {
		return !Objects.equals(o1, o2);
	}


	/**
	 * Checks if a is greater than b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean gt(T a, T b) {
		if (a == null) return false;
		if (b == null) return true;
		return a.compareTo(b) > 0;
	}


	/**
	 * Checks if a is greater than or equal to b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean ge(T a, T b) {
		if (a == null) return b == null;
		if (b == null) return true;
		return a.compareTo(b) >= 0;
	}


	/**
	 * Checks if a is less than b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean lt(T a, T b) {
		if (a == null) return b != null;
		if (b == null) return false;
		return a.compareTo(b) < 0;
	}


	/**
	 * Checks if a is less than or equal to b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean le(T a, T b) {
		if (a == null) return true;
		if (b == null) return false;
		return a.compareTo(b) <= 0;
	}


}
