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

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/**
 * Helper class that simplifies capturing LoggingEvents.
 * 
 * Intended to be used in unit tests to monitor what's logged from the class under test.
 * <p>
 * This class is <a href="http://www.logback.org">Logback</a> specific
 * because it needs to configure logging, but logging in the monitored
 * code need not be. It must use <a href="http://www.slf4j">SLF4J</a>, though.
 * </p>
 * 
 * @author Claus Nielsen
 */
public abstract class LogCapturingTestTemplate {

	String loggerName = null;
	
	/**
	 * Constructor.
	 *
	 * @param logger - name of the logger to capture LoggingEvents from.
	 */
	public LogCapturingTestTemplate(String logger) {
		this.loggerName = logger;
	}

	/**
	 * Constructor.
	 *
	 * @param logger - Class whose name identifies the logger to capture LoggingEvents from.
	 */
	@SuppressWarnings({ "rawtypes" })
	public LogCapturingTestTemplate(Class logger) {
		this(logger.getName());
	}

	/**
	 * Subclasses should override this to execute the code they want monitored.
	 */
	protected abstract void monitorThis();
	
	/**
	 * Executes the monitored code and returns the LoggingEvents captured from it.
	 * 
	 * @return List&lt;LoggingEvent&gt;
	 */
	public CapturedLoggingEvents execute() {
		// Configure logging so that LoggingEvents can be captured
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		logger.setAdditive(false);
		ListAppender<LoggingEvent> listAppender = new ListAppender<LoggingEvent>();
		listAppender.start();
		logger.addAppender(listAppender);
		// Perform monitored action
		monitorThis();
		// Restore normal logging
		logger.detachAppender(listAppender);
		logger.setAdditive(true);
		// Return captured LoggingEvents
		return new CapturedLoggingEvents(listAppender.list);
	}

}
