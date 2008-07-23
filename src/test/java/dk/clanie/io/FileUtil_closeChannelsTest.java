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
package dk.clanie.io;

import static dk.clanie.test.CollectionMatchers.*;
import static dk.clanie.io.FileUtil.closeChannels;
import static dk.clanie.test.logging.LoggingEventMatchers.level;
import static dk.clanie.test.logging.LoggingEventMatchers.levelMin;
import static dk.clanie.test.logging.LoggingEventMatchers.message;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.channels.spi.AbstractInterruptibleChannel;

import org.junit.Test;

import ch.qos.logback.classic.Level;
import dk.clanie.test.logging.CapturedLoggingEvents;
import dk.clanie.test.logging.LogCapturingTestTemplate;

public class FileUtil_closeChannelsTest {

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
		private boolean closed = false;
		@Override
		protected void implCloseChannel() throws IOException {
			closed = true;
		}
		public boolean isClosed() {
			return closed;
		}
	}

	/**
	 * Tests that closeChannels() tries to close all the supplied Channels,
	 * even if supplied with nulls and Channels which fail to close.
	 * Also checks that close-attempts which fails are logged.
	 */
	@Test
	public void testCloseChannels() {
		// Call closeChannels(), capture LoggingEvents
		final ChannelStub c1 = new  ChannelStub();
		final ChannelStub c2 = new  ChannelStub();
		CapturedLoggingEvents loggingEvents = new LogCapturingTestTemplate(FileUtil.class) {
			@Override
			protected void monitorThis() {
				closeChannels(null, c1, new UnclosableChannelStub(), null, c2);
			}
		}.execute();
		// Perform checks
		assertTrue("Channel c1 wasn't closed..", c1.isClosed());
		assertTrue("Channel c2 wasn't closed..", c2.isClosed());
		assertThat(loggingEvents.getMessages(), hasItem(FileUtil.FAILED_TO_CLOSE_CHANNEL));
		assertThat(loggingEvents.getEvents(), hasItem(message(FileUtil.FAILED_TO_CLOSE_CHANNEL)));
		assertThat(loggingEvents.getEvents(), hasItem(level(Level.ERROR)));
		assertThat(loggingEvents.getEvents(), hasItem(allOf(level(Level.ERROR), message(FileUtil.FAILED_TO_CLOSE_CHANNEL))));
		assertThat(loggingEvents.getEvents(levelMin(Level.WARN)),  sizeEq(1));
	}

}
