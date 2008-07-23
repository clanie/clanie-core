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
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableInformation;

/**
 * Matchers to check LoggingEvents.
 * 
 * @author Claus Nielsen
 */
public class LoggingEventMatchers {

	/**
	 * Does the LoggingEvent contain a Throwable of a particular type?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> exception(Class<? extends Throwable> type) {
		return new ExceptionMatcher(type);
	}

	/**
	 * Does the LoggingEvent have a particular message?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> message(String message) {
		return new MessageMatcher(message);
	}

	/**
	 * Does the LoggingEvent have a particular Level?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> level(Level level) {
		return new LevelMatcher(level);
	}
	
	/**
	 * Does the LoggingEvent have a Level >= a particular Level?
	 */
	@Factory
	public static TypeSafeMatcher<LoggingEvent> levelMin(Level level) {
		return new LevelMinMatcher(level);
	}
	

	/**
	 * Matcher to check if an LoggingEvent has a particular message.
	 *
	 * @author Claus Nielsen
	 */
	private static class MessageMatcher extends TypeSafeMatcher<LoggingEvent> {

		private final String theMessage;

		/**
		 * Constructor.
		 * 
		 * @param theMessage
		 *            The predicate evaluates to true for LoggingEvents
		 *            with this message.
		 */
		private MessageMatcher(String theMessage) {
			this.theMessage = theMessage;
		}

		@Override
		public boolean matchesSafely(LoggingEvent event) {
			return theMessage.equals(event.getMessage());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("the message ").appendValue(theMessage);
		}

	}


	/**
	 * Matcher to check if an LoggingEvent contains an exception of a particular type.
	 *
	 * @author Claus Nielsen
	 */
	private static class ExceptionMatcher extends TypeSafeMatcher<LoggingEvent> {

		private final Class<? extends Throwable> theClass;

		/**
		 * Constructor.
		 * 
		 * @param theClass
		 *            The predicate evaluates to true for LoggingEvents
		 *            containing a Throwable of this class or one of its subclasses.
		 */
		private ExceptionMatcher(Class<? extends Throwable> theClass) {
			this.theClass = theClass;
		}

		@Override
		public boolean matchesSafely(LoggingEvent event) {
			ThrowableInformation throwableInformation = event.getThrowableInformation();
			if (throwableInformation == null) return false;
			return theClass.isInstance(throwableInformation.getThrowable());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("an instance of ").appendText(theClass.getName());
		}

	}


	/**
	 * Matcher to check if an LoggingEvent has a particular Level.
	 *
	 * @author Claus Nielsen
	 */
	private static class LevelMatcher extends TypeSafeMatcher<LoggingEvent> {

		private final Level theLevel;

		/**
		 * Constructor.
		 * 
		 * @param theLevel
		 *            The predicate evaluates to true for LoggingEvents
		 *            with the specified Level.
		 */
		private LevelMatcher(Level theLevel) {
			this.theLevel = theLevel;
		}

		@Override
		public boolean matchesSafely(LoggingEvent event) {
			return event.getLevel() == theLevel;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("a LoggingEvent with Level ").appendText(theLevel.toString());
		}

	}


	/**
	 * Matcher to check if an LoggingEvent has at least the specified Level.
	 *
	 * @author Claus Nielsen
	 */
	private static class LevelMinMatcher extends TypeSafeMatcher<LoggingEvent> {

		private final Level minLevel;

		/**
		 * Constructor.
		 * 
		 * @param minLevel
		 *            The predicate evaluates to true for LoggingEvents
		 *            with Level >= this Level.
		 */
		private LevelMinMatcher(Level minLevel) {
			this.minLevel = minLevel;
		}

		@Override
		public boolean matchesSafely(LoggingEvent event) {
			return event.getLevel().isGreaterOrEqual(minLevel);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("a LoggingEvent with Level >= ").appendText(minLevel.toString());
		}

	}


}
