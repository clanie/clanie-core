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

import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Collection utility methods.
 * 
 * @author Claus Nielsen
 */
public class CollectionUtils {


	/**
	 * Returns a sequential {@link Stream} of the contents of {@code iterable}, delegating to {@link
	 * Collection#stream} if possible.
	 * 
	 * Never returns null. If given iterable is null an empty stream is returned.
	 */
	public static  @NonNull <T> Stream<T> stream(@Nullable Iterable<T> iterable) {
		if (iterable == null) return Stream.of();
		return (iterable instanceof Collection<T> c) ? c.stream() : StreamSupport.stream(iterable.spliterator(), false);
	}


}
