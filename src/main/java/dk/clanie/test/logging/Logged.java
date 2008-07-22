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

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import ch.qos.logback.classic.spi.LoggingEvent;

/**
 * Factory methods for Matchers to check LoggingEvents.
 * 
 * @author Claus Nielsen
 */
public class Logged {

	/**
	 * Does the LoggingEvent contain an Throwable of a particular type?
	 */
	@Factory
	public static Matcher<LoggingEvent> exception(Class<? extends Throwable> type) {
		return new LoggedException(type);
	}

	/**
	 * Does the LoggingEvent have a particular message?
	 */
	@Factory
	public static Matcher<LoggingEvent> message(String message) {
		return new LoggedMessage(message);
	}

}
