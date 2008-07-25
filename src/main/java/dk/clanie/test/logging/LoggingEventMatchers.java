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

import static dk.clanie.test.logging.LogLevelMatchers.ge;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableInformation;

/**
 * Matchers to check LoggingEvents.
 * 
 * @author Claus Nielsen
 */
public class LoggingEventMatchers {

	/**
	 * Does the LoggingEvent have a matching Level?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> level(Matcher<Level> levelMatcher) {
		return new LevelMatcher(levelMatcher);
	}
	
	/**
	 * Does the LoggingEvent have a particular Level?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> level(Level level) {
		return new LevelMatcher(equalTo(level));
	}
	
	/**
	 * Does the LoggingEvent have at least a particular Level?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> levelGE(Level level) {
		return new LevelMatcher(ge(level));
	}
	
	/**
	 * Does the LoggingEvent have a matching message?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> message(Matcher<String> messageMatcher) {
		return new MessageMatcher(messageMatcher);
	}

	/**
	 * Does the LoggingEvent have a particular message?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> message(String message) {
		return new MessageMatcher(equalTo(message));
	}

	/**
	 * Does the LoggingEvent contain a matching Throwable?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> exception(Matcher<Object> exceptionMatcher) {
		return new ExceptionMatcher(exceptionMatcher);
	}

	
	/**
	 * Does the LoggingEvent contain a matching Throwable?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> exception(Class<?> exceptionClass) {
		return exception(instanceOf(exceptionClass));
	}

	
	/**
	 * Matcher to check if an LoggingEvent has a matching Level.
	 *
	 * @author Claus Nielsen
	 */
	private static class LevelMatcher extends TypeSafeMatcher<LoggingEvent> {

		private final Matcher<Level> theMatcher;

		/**
		 * Constructor.
		 * 
		 * @param theMatcher
		 *            The predicate evaluates to true for LoggingEvents
		 *            with a Level satisfying this Matcher.
		 */
		private LevelMatcher(Matcher<Level> theMatcher) {
			this.theMatcher = theMatcher;
		}

		@Override
		public boolean matchesSafely(LoggingEvent event) {
			return theMatcher.matches(event.getLevel());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("an LoggingEvent with Level ").appendDescriptionOf(theMatcher);
		}

	}


	/**
	 * Matcher to check if an LoggingEvent has a matching message.
	 *
	 * @author Claus Nielsen
	 */
	private static class MessageMatcher extends TypeSafeMatcher<LoggingEvent> {

		private final Matcher<String> theMatcher;

		/**
		 * Constructor.
		 * 
		 * @param theMatcher
		 *            The predicate evaluates to true for LoggingEvents
		 *            with a message satisfying this matcher.
		 */
		private MessageMatcher(Matcher<String> theMatcher) {
			this.theMatcher = theMatcher;
		}

		@Override
		public boolean matchesSafely(LoggingEvent event) {
			return theMatcher.matches(event.getMessage());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("an LoggingEvent with a message ").appendValue(theMatcher);
		}

	}


	/**
	 * Matcher to check if an LoggingEvent contains an matching type of exception.
	 *
	 * @author Claus Nielsen
	 */
	private static class ExceptionMatcher extends TypeSafeMatcher<LoggingEvent> {

		private final Matcher<Object> theMatcher;

		/**
		 * Constructor.
		 * 
		 * @param theMatcher
		 *            The predicate evaluates to true for LoggingEvents
		 *            containing a Throwable which satisfies this matcher.
		 */
		private ExceptionMatcher(Matcher<Object> theMatcher) {
			this.theMatcher = theMatcher;
		}

		@Override
		public boolean matchesSafely(LoggingEvent event) {
			ThrowableInformation throwableInformation = event.getThrowableInformation();
			if (throwableInformation == null) return false;
			return theMatcher.matches(throwableInformation.getThrowable());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("an LoggingEvent with a Throwable ").appendDescriptionOf(theMatcher);
		}

	}


}
