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
import org.hamcrest.TypeSafeMatcher;

import ch.qos.logback.classic.spi.LoggingEvent;

/**
 * Matcher to check if an LoggingEvent contains an exception of a particular type.
 *
 * @author Claus Nielsen
 */
public class LoggedException extends TypeSafeMatcher<LoggingEvent> {

	private final Class<? extends Throwable> theClass;

	/**
	 * Constructor.
	 * 
	 * Don't use this - use the factory method in {@link Logged#exception(Class)}.
	 * 
	 * @param theClass
	 *            The predicate evaluates to true for LoggingEvents
	 *            containing a Throwable of this class or one of its subclasses.
	 */
	LoggedException(Class<? extends Throwable> theClass) {
		this.theClass = theClass;
	}

	@Override
	public boolean matchesSafely(LoggingEvent event) {
		return theClass.isInstance(event.getThrowableInformation().getThrowable());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("an instance of ").appendText(theClass.getName());
	}

}
