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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Utility Methods.
 * 
 * @author Claus Nielsen
 */
public class FileUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(FileUtil.class);

	/**
	 * Deletes a file or directory.
	 * 
	 * When deleting a directory files and subdirectories in it are also
	 * deleted.
	 * 
	 * @param fileOrDir
	 */
	public static void emptyAndDelete(File fileOrDir) {
		if (fileOrDir.isDirectory()) {
			File[] files = fileOrDir.listFiles();
			for (File file : files) {
				emptyAndDelete(file);
			}
		}
		fileOrDir.delete();
	}


	/**
	 * Creates a copy of a file.
	 * 
	 * @param from
	 *            - source File
	 * @param to
	 *            - destination File
	 * @throws IOException
	 */
	public static void copyFile(File from, File to) throws IOException {
		if (!to.exists()) to.createNewFile();

		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(from).getChannel();
			outputChannel = new FileOutputStream(to).getChannel();
			long size = inputChannel.size();
			long copied = 0L;
			while (copied < size)
				copied += outputChannel.transferFrom(inputChannel, copied, size - copied);
		} finally {
			close(inputChannel, outputChannel);
		}
	}


	/**
	 * Closes nio Channels.
	 * <p>
	 * Suppresses errors - if an Exception occurs it will be logged, but
	 * otherwise silently ignored.
	 * </p><p>
	 * Nulls in the channels argument are ignored.
	 * </p>
	 * 
	 * @param channels
	 *            - AbstractInterruptibleChannels to close.
	 */
	public static void close(AbstractInterruptibleChannel... channels) {
		for (AbstractInterruptibleChannel channel : channels) {
			if (channel == null) continue;
			try {
				channel.close();
			} catch (Exception e) {
				logger.error("Failed to close channel.", e);
			}
		}
	}


}
