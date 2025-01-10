/**
 * Copyright (C) 2009-2012, Claus Nielsen, clausn999@gmail.com
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

import static java.util.Arrays.stream;

import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;

/**
 * Miscellaneous static utility methods.
 * 
 * @author Claus Nielsen
 */
public class MiscUtils {


	/**
	 * Selects first non-null argument.
	 *
	 * @param <T>
	 * @param args
	 * @return the first argument which is not null or null if all arguments are null.
	 */
	@SafeVarargs
	public static <T> T firstNonNull(T... args) {
		return stream(args)
				.filter(Objects::nonNull)
				.findFirst().orElse(null);
	}


	/**
	 * Returns an {@code Optional} describing the given value, if
	 * non-{@code null}, otherwise returns an empty {@code Optional}.
	 * 
	 * Short for {@link Optional#ofNullable(Object)}.
	 *
	 * @param value the possibly-{@code null} value to describe
	 * @param <T> the type of the value
	 * @return an {@code Optional} with a present value if the specified value
	 *         is non-{@code null}, otherwise an empty {@code Optional}
	 */
	public static @NonNull <T> Optional<T> opt(T value) {
		return Optional.ofNullable(value);
	}


}
