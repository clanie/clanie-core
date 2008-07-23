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

import static dk.clanie.test.CollectionMatchers.sizeEq;
import static dk.clanie.test.logging.LoggingEventMatchers.exception;
import static dk.clanie.test.logging.LoggingEventMatchers.level;
import static dk.clanie.test.logging.LoggingEventMatchers.levelMin;
import static dk.clanie.test.logging.LoggingEventMatchers.message;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.LoggingEvent;

import static ch.qos.logback.classic.Level.*;

/**
 * Tests LoggingEventMatchers.
 * 
 * @author Claus Nielsen
 */
public class LoggingEventMatchersTest {

	private static final Class<LoggingEventMatchersTest> CLASS = LoggingEventMatchersTest.class;

	private static final CapturedLoggingEvents loggingEvents = new LogCapturingTestTemplate(CLASS) {
		@Override
		protected void monitorThis() {
			Logger logger = LoggerFactory.getLogger(CLASS);
			logger.debug("A debug message");
			logger.info("An informational message");
			logger.info("Another informational message", new Exception("FYI"));
			logger.warn("A warning");
			logger.error("An error message", new AssertionError("Not really an error"));
		}
	}.execute();

	@Test
	@SuppressWarnings("unchecked")
	public void testFilteringEventsOnLevel() {
		List<LoggingEvent> infoAndErrorEvents =
			loggingEvents.getEvents(anyOf(level(ERROR), level(INFO)));
		assertThat(infoAndErrorEvents,
			allOf(
				hasItem(level(ERROR)),
				hasItem(level(INFO)),
				not(hasItem(anyOf(level(TRACE), level(DEBUG), level(WARN))))
			)
		);
	}

	@Test
	public void testFilteringEventsOnLevelMin() {
		List<LoggingEvent> warningAndErrorEvents =
			loggingEvents.getEvents(levelMin(WARN));
		assertThat(warningAndErrorEvents, sizeEq(2));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFilteringEventsOnMessageText() {
		List<LoggingEvent> events =
			loggingEvents.getEvents(message("Another informational message"));
		assertThat(events, allOf(sizeEq(1), hasItem(level(INFO))));
	}

	@Test
	public void testFilteringEventsOnException() {
		List<LoggingEvent> events;
		events = loggingEvents.getEvents(exception(Throwable.class));
		assertThat(events, sizeEq(2));
		events = loggingEvents.getEvents(exception(AssertionError.class));
		assertThat(events, sizeEq(1));
	}

	@Test
	public void testThrowables() {
		List<Throwable> throwables = loggingEvents.getThrowables();
		assertThat(throwables, sizeEq(2));
	}

}
