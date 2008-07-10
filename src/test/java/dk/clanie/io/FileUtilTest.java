package dk.clanie.io;

import static dk.clanie.io.FileUtil.close;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class FileUtilTest {

	private AtomicInteger closeCount = new AtomicInteger(0);
	private AtomicInteger expectedLogEntryCount = new AtomicInteger(0);

	/**
	 * An Channel stub which cannot be closed.
	 * 
	 * Throws a RuntimeException when closed.
	 */
	private class UnclosableChannelStub extends AbstractInterruptibleChannel {
		@Override
		protected void implCloseChannel() throws IOException {
			closeCount.incrementAndGet();
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
	 * Tests that all supplied Channels are closed even if supplied with nulls
	 * and Channels which fail to close.
	 */
	@Test
	public void testCloseChannels() {
		Logger rootLogger = (Logger) LoggerFactory.getLogger(LoggerContext.ROOT_NAME);
		Appender<LoggingEvent> appender = rootLogger.getAppender("console");
		appender.addFilter(new Filter() {
			@Override
			public FilterReply decide(Object o) {
				LoggingEvent event = (LoggingEvent) o;
				if (event.getMessage().equals("Failed to close channel.")) {
					expectedLogEntryCount.incrementAndGet();
					return FilterReply.DENY;
				}
				return FilterReply.NEUTRAL;
			}
			
		});
		close(null, new UnclosableChannelStub(), null, new ChannelStub(), new ChannelStub());
		assertEquals("Incorrect number of channel closed.", 3, closeCount.intValue());
		assertEquals("Exactly 1 failure to close a Channel should have been logged.", 1, expectedLogEntryCount.intValue());
		appender.clearAllFilters();
	}

}
