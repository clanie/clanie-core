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
package dk.clanie.test.logging;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import ch.qos.logback.classic.Level;

/**
 * Matchers to check log Levels.
 * 
 * @author Claus Nielsen
 */
public class LogLevelMatchers {

	/**
	 * Is the Level equal to or greater than a particular level?
	 */
	@Factory
	public static TypeSafeMatcher<Level> ge(Level level) {
		return new LevelEqualToOrGreaterThanMatcher(level);
	}

	/**
	 * Matcher to check if a log Level is equal or greater than a particular
	 * Level.
	 * 
	 * @author Claus Nielsen
	 */
	private static class LevelEqualToOrGreaterThanMatcher extends TypeSafeMatcher<Level> {

		private final Level theLevel;

		/**
		 * Constructor.
		 * 
		 * @param theLevel
		 *            The predicate evaluates to true for this Level.
		 */
		private LevelEqualToOrGreaterThanMatcher(Level level) {
			this.theLevel = level;
		}

		@Override
		public boolean matchesSafely(Level level) {
			return level.isGreaterOrEqual(theLevel);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("log Level greater than or equal to ").appendValue(theLevel);
		}

	}

}
