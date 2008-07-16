/**
 * Copyright (C) 2008, Claus Nielsen, cn@cn-consult.dk.dk
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
package dk.clanie.io;

import static dk.clanie.io.FileUtil.closeChannels;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class FileUtil_closeChannelsTest {

	private AtomicInteger closeCount = new AtomicInteger(0);

	/**
	 * An Channel stub which cannot be closed.
	 * 
	 * Throws a RuntimeException when closed.
	 */
	private class UnclosableChannelStub extends AbstractInterruptibleChannel {
		@Override
		protected void implCloseChannel() throws IOException {
			throw new RuntimeException("Won't close!");
		}
	}

	/**
	 * Dummy Channel stub.
	 * 
	 * Does nothing.
	 */
	private class ChannelStub extends AbstractInterruptibleChannel {
		@Override
		protected void implCloseChannel() throws IOException {
			closeCount.incrementAndGet();
		}
	}

	/**
	 * Tests that closeChannels() tries to close all the supplied Channels,
	 * even if supplied with nulls and Channels which fail to close.
	 */
	@Test
	public void testCloseChannels() {
		// Capture log entries from FileUtil
		Logger logger = (Logger) LoggerFactory.getLogger(FileUtil.class);
		logger.setAdditive(false);
		ListAppender<LoggingEvent> listAppender = new ListAppender<LoggingEvent>();
		listAppender.start();
		logger.addAppender(listAppender);
		// Close a mix of normal, broken and completely missing channels
		closeChannels(null, new UnclosableChannelStub(), null, new ChannelStub(), new ChannelStub());
		// Restore normal logging
		logger.detachAppender(listAppender);
		logger.setAdditive(true);
		// Perform checks
		assertEquals("Incorrect number of channel closed.", 2, closeCount.intValue());
		int closeFailedCount = 0;
		for (LoggingEvent loggingEvent : listAppender.list) {
			if (FileUtil.FAILED_TO_CLOSE_CHANNEL.equals(loggingEvent.getMessage())) closeFailedCount++;
		}
		assertEquals("Exactly 1 failure to close a Channel should have been logged.", 1, closeFailedCount);
	}

}
