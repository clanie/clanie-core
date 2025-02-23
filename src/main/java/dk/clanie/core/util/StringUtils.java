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

import static dk.clanie.core.Utils.opt;
import static dk.clanie.core.Utils.stream;
import static java.util.stream.Collectors.joining;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class StringUtils {


	private StringUtils() {
		// Not meant to be instantiated
	}


	/**
	 * Null-safe toString.
	 */
	public static String asString(@Nullable Object object) {
		return object == null ? null : object.toString();
	}


	/**
	 * Null-safe String to UUID conversion.
	 */
	public static UUID asUuid(String s) {
		return opt(s).map(UUID::fromString).orElse(null);
	}


	/**
	 * Generates a comma separated list of given values converted to strings.
	 * 
	 * No escaping is performed.
	 */
	public static @NonNull String csv(@Nullable Iterable<?> iterable) {
		return stream(iterable)
				.map(StringUtils::asString)
				.collect(joining(","));
	}


	/**
	 * Generates a comma separated list of given values first mapped using given mapper and then converted to strings.
	 * 
	 * No escaping is performed.
	 */
	public static @NonNull <T> String csv(@Nullable Iterable<T> iterable, Function<? super T, ?> mapper) {
		return stream(iterable)
				.map(mapper)
				.map(StringUtils::asString)
				.collect(joining(","));
	}


}
