/**
 * Copyright (C) 2008, Claus Nielsen, cn@cn-consult.dk
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
package dk.clanie.hamcrest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matchers to check Collections.
 * 
 * @author Claus Nielsen
 */
public class CollectionMatchers {

	/**
	 * Does the Collection size match?
	 */
	@Factory
	public static TypeSafeMatcher<Collection<?>> size(Matcher<Integer> matcher) {
		return new SizeMatcher(matcher);
	}

	/**
	 * Does the Collection have a certain number of elements?
	 */
	@Factory
	public static TypeSafeMatcher<Collection<?>> sizeEq(int size) {
		return new SizeMatcher(equalTo(size));
	}

	/**
	 * Does the Collection have at least a certain number of elements?
	 */
	@Factory
	public static TypeSafeMatcher<Collection<?>> sizeMin(int minSize) {
		return new SizeMatcher(greaterThanOrEqualTo(minSize));
	}

	/**
	 * Does the Collection have at most a certain number of elements?
	 */
	@Factory
	public static TypeSafeMatcher<Collection<?>> sizeMax(int maxSize) {
		return new SizeMatcher(lessThanOrEqualTo(maxSize));
	}


	/**
	 * Matcher to check the size of a Collection.
	 *
	 * @author Claus Nielsen
	 */
	private static class SizeMatcher extends TypeSafeMatcher<Collection<?>> {

		private Matcher<Integer> matcher;

		/**
		 * Constructor.
		 * 
		 * @param matcher
		 *            The predicate evaluates to true for Collections
		 *            with a size satisfying this Matcher.
		 */
		private SizeMatcher(Matcher<Integer> matcher) {
			this.matcher = matcher;
		}

		@Override
		public boolean matchesSafely(Collection<?> collection) {
			return collection != null && matcher.matches(collection.size());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("a collection with size = ").appendDescriptionOf(matcher);
		}

	}


}
