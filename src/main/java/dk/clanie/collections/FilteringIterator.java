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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matcher;

/**
 * Filtering Iterator.
 * 
 * An iterator which only returns elements matching a hamcrest matcher.
 * 
 * @author Claus Nielsen
 *
 * @param <E>
 */
public class FilteringIterator<E> implements Iterator<E> {

	Iterator<? extends E> iter;
	Matcher<? extends E> matcher;

	E next = null;

	/**
	 * Wraps the supplied Iterator and returns only elements satisfying the supplied matcher.
	 * 
	 * @param iter
	 * @param matcher
	 */
	public FilteringIterator(Iterator<? extends E> iter, Matcher<? extends E> matcher) {
		this.iter = iter;
		this.matcher = matcher;
		findNextNext();
	}

	/**
	 * Creates an Iterator which returns elements from the supplied Collection
	 * satisfying the supplied matcher.
	 * 
	 * @param collection
	 * @param matcher
	 */
	public FilteringIterator(Collection<? extends E> collection, Matcher<? super E> matcher) {
		iter = collection.iterator();
		findNextNext();
	}

	/**
	 * Returns a List with all matching elements.
	 *
	 * @return List of matching elements.
	 */
	List<? extends E> list() {
		List<E> result = new ArrayList<E>();
		while (hasNext()) {
			result.add(next());
		}
		return result;
	}

	private void findNextNext() {
		while (iter.hasNext()) {
			next = iter.next();
			if (matcher.matches(next)) return;
		}
		next = null;
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public E next() {
		E result = next;
		findNextNext();
		return result;
	}

	/**
	 * Unsupported operation.
	 * 
	 * @see java.util.Iterator#remove()
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
