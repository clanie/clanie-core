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

import static dk.clanie.collections.CollectionFactory.newArrayList;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Matcher;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;

/**
 * Immutable collection of LoggingEvents captured by {@link LogCapturingTestTemplate}.
 * 
 * @author Claus Nielsen
 */
public class CapturedLoggingEvents {

	final List<LoggingEvent> events;
	
	CapturedLoggingEvents(List<LoggingEvent> events) {
		this.events = Collections.unmodifiableList(events);
	}

	/**
	 * Returns a new CapturedLoggingEvents instance containing the subset of
	 * LoggingEvents which satisfies the supplied Matcher.
	 * 
	 * @param matcher
	 * @return CapturedLoggingEvents
	 */
	public CapturedLoggingEvents filter(Matcher<LoggingEvent> matcher) {
		List<LoggingEvent> matchingEvents = newArrayList();
		for (LoggingEvent event : events) {
			if (matcher.matches(event))
				matchingEvents.add(event);
		}
		return new CapturedLoggingEvents(matchingEvents);
	}

	/**
	 * Gets all LoggingEvents.
	 * 
	 * @return List&lt;LoggingEvent&gt;
	 */
	public List<LoggingEvent> getEvents() {
		return events;
	}

	/**
	 * Gets all LoggingEvents which satisfies the supplied Matcher.
	 * 
	 * @param matcher
	 * @return List&lt;LoggingEvent&gt;
	 */
	public List<LoggingEvent> getEvents(Matcher<LoggingEvent> matcher) {
		return filter(matcher).getEvents();
	}

	/**
	 * Gets messages from all LoggingEvents.
	 * 
	 * @return List&lt;String&gt;
	 */
	public List<String> getMessages() {
		List<String> messages = newArrayList();
		for (LoggingEvent event : events) {
			messages.add(event.getMessage());
		}
		return messages;
	}

	/**
	 * Gets messages from all LoggingEvents which satisfies the supplied Matcher.
	 * 
	 * @param matcher
	 * @return List&lt;String&gt;
	 */
	public List<String> getMessages(Matcher<LoggingEvent> matcher) {
		return filter(matcher).getMessages();
	}

	/**
	 * Gets Thowables from all LoggingEvents.
	 * 
	 * Note that not all LoggingEvents have Throwables and therefore the 
	 * returned Collection may be less than the number of LoggingEvents.
	 * 
	 * @return List&lt;Throwable&gt;
	 */
	public List<Throwable> getThrowables() {
		List<Throwable> throwables = newArrayList();
		for (LoggingEvent event : events) {
			ThrowableProxy throwableProxy = event.getThrowableProxy();
			if (throwableProxy != null)
				throwables.add(throwableProxy.getThrowable());
		}
		return throwables;
	}

	/**
	 * Gets Thowables from all LoggingEvents which satisfies the supplied Matcher.
	 * 
	 * @param matcher
	 * @return List&lt;Throwable&gt;
	 */
	public List<Throwable> getThrowables(Matcher<LoggingEvent> matcher) {
		return filter(matcher).getThrowables();
	}

	/**
	 * Gets Levels from all LoggingEvents.
	 * 
	 * @return List&lt;Level&gt;
	 */
	public List<Level> getLevels() {
		List<Level> levels = newArrayList();
		for (LoggingEvent event : events) {
			levels.add(event.getLevel());
		}
		return levels;
	}

	/**
	 * Gets Levels from all LoggingEvents which satisfies the supplied Matcher.
	 * 
	 * @param matcher
	 * @return List&lt;Level&gt;
	 */
	public List<Level> getLevels(Matcher<LoggingEvent> matcher) {
		return filter(matcher).getLevels();
	}

	/**
	 * Gets the number of LoggingEvents.
	 * 
	 * @return the number of LoggingEvents.
	 */
	public Integer getSize() {
		return events.size();
	}

}
