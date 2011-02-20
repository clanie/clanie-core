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
package dk.clanie.hamcrest;

import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matchers to check Collections.
 * 
 * @author Claus Nielsen
 */
public class RegexMatcher {

	/**
	 * Does the String match the regular expression?
	 */
	@Factory
	public static TypeSafeMatcher<String> matchesRegex(String regex) {
		return new InternalRegexMatcher(regex);
	}


	/**
	 * Matcher to check if a String matces an regular expression.
	 *
	 * @author Claus Nielsen
	 */
	private static class InternalRegexMatcher extends TypeSafeMatcher<String> {

		private String regex;

		/**
		 * Constructor.
		 * 
		 * @param regex
		 *            The predicate evaluates to true for Strings
		 *            matching this regular expression.
		 */
		private InternalRegexMatcher(String regex) {
			this.regex = regex;
		}

		@Override
		public boolean matchesSafely(String input) {
			return input != null && Pattern.matches(regex, input);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("a String matching the regular expression \"" + regex + "\".");
		}

	}


}
