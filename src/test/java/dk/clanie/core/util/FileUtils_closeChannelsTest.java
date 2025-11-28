/*
 * Copyright (C) 2008-2024, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.core.util;

import static ch.qos.logback.classic.Level.ERROR;
import static ch.qos.logback.classic.Level.WARN;
import static dk.clanie.core.Utils.closeChannels;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.channels.spi.AbstractInterruptibleChannel;

import org.junit.jupiter.api.Test;

import dk.clanie.test.logging.CapturedLoggingEvents;
import dk.clanie.test.logging.LogCapturer;

public class FileUtils_closeChannelsTest {


	/**
	 * An Channel stub which cannot be closed.
	 * 
	 * Throws a RuntimeException when closed.
	 */
	private static class UnclosableChannelStub extends AbstractInterruptibleChannel {
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
	private static class ChannelStub extends AbstractInterruptibleChannel {
		private boolean closed = false;

		@Override
		protected void implCloseChannel() throws IOException {
			closed = true;
		}

		public boolean isClosed() {
			return closed;
		}
	}


	// Capture logging events while closing come Channels.
	private static final ChannelStub c1 = new ChannelStub();
	private static final ChannelStub c2 = new ChannelStub();
	private static final CapturedLoggingEvents loggingEvents = LogCapturer.capture(FileUtils.class, () -> {
		closeChannels(null, c1, new UnclosableChannelStub(), null, c2);
	});


	@Test
	public void testCloseableChannelsClosed() {
		assertTrue(c1.isClosed(), "Channel c1 wasn't closed..");
		assertTrue(c2.isClosed(), "Channel c2 wasn't closed..");
	}


	@Test
	public void testErrorLoggedForUnclosableChannels() {
		assertThat(loggingEvents.getEvents())
		.anyMatch(e -> e.getMessage().equals(FileUtils.FAILED_TO_CLOSE_CHANNEL) && e.getLevel() == ERROR);
	}


	@Test
	public void testNoAdditionalWarningsOrErrorsLogged() {
		assertThat(loggingEvents.getEvents()).filteredOn(e -> e.getLevel().isGreaterOrEqual(WARN)).hasSize(1);
	}


}
