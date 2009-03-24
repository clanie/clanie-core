/**
 * Copyright (C) 2009, Claus Nielsen, cn@cn-consult.dk
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
package dk.clanie.collections;

import java.util.Collection;
import java.util.Iterator;

import org.hamcrest.Matcher;

/**
 * Collection Filter.
 * 
 * Static methods to filter collections using hamcrest matchers.
 * 
 * @author Claus Nielsen
 */
public class CollectionFilters {

	/**
	 * Removes matching elements from the supplied Collection.
	 * 
	 * @param <E>
	 * @param collection
	 * @param matcher
	 */
	public static <E> void removeMatcing(Collection<E> collection, Matcher<? super E> matcher) {
		Iterator<E> iter = collection.iterator();
		while (iter.hasNext()) {
			E item = iter.next();
			if (matcher.matches(item)) iter.remove();
		}
	}

}