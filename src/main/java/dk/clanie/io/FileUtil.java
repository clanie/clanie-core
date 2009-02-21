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

import static dk.clanie.util.CollectionFactory.newArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.List;

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

	static final String FAILED_TO_CLOSE_CHANNEL = "Failed to close channel.";

	/**
	 * Creates a copy of a file.
	 * 
	 * Shorthand for <code>copyFile(from, to, false)</code>, <code>false</code>
	 * meaning don't overwrite existing file.
	 * 
	 * @param from
	 *            - source File
	 * @param to
	 *            - destination File
	 * 
	 * @throws RuntimeIOException
	 */
	public static void copyFile(File from, File to) throws RuntimeIOException {
		copyFile(from, to, false);
	}

	/**
	 * Creates a copy of a file.
	 * 
	 * If the destination is a directory the file is copied to that directory
	 * with the same name as the source file.
	 * 
	 * @param from
	 *            - source File
	 * @param to
	 *            - destination File
	 * @param overwrite
	 *            - allow overwriting existing file
	 * 
	 * @throws RuntimeIOException
	 */
	public static void copyFile(File from, File to, boolean overwrite)
			throws RuntimeIOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			if (!from.exists())
				throw new RuntimeIOException.FileNotFound(from);
			if (to.isDirectory()) {
				to = new File(to.getPath() + File.separator + from.getName());
			}
			if (to.exists()) {
				if (!overwrite)
					throw new RuntimeIOException.FileAlreadyExists(to);
			} else if (!to.createNewFile())
				throw new RuntimeIOException.FailedToCreate(to);

			inputChannel = new FileInputStream(from).getChannel();
			outputChannel = new FileOutputStream(to).getChannel();
			long size = inputChannel.size();
			long copied = 0L;
			while (copied < size)
				copied += outputChannel.transferFrom(inputChannel, copied, size
						- copied);
		} catch (java.io.IOException e) {
			throw new RuntimeIOException(e.getMessage(), e);
		} finally {
			closeChannels(inputChannel, outputChannel);
		}
	}

	/**
	 * Closes nio Channels.
	 * <p>
	 * Suppresses errors - if an Exception occurs it will be logged, but
	 * otherwise silently ignored.
	 * </p>
	 * <p>
	 * Nulls in the channels argument are ignored.
	 * </p>
	 * 
	 * @param channels
	 *            - AbstractInterruptibleChannels to close.
	 */
	public static void closeChannels(AbstractInterruptibleChannel... channels) {
		for (AbstractInterruptibleChannel channel : channels) {
			if (channel == null)
				continue;
			try {
				channel.close();
			} catch (Exception e) {
				logger.error(FAILED_TO_CLOSE_CHANNEL, e);
			}
		}
	}

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
	 * Creates a List of all the files in a directory and it's sub-directories.
	 * 
	 * @param dir
	 *            the directory to scan.
	 * @return List of all Files in the directory and it's sub-directories.
	 */
	public static List<File> listFilesRecursively(File dir) {
		List<File> fileList = newArrayList();
		addFilesRecursively(dir, fileList);
		return fileList;
	}

	/**
	 * Adds all files in a directory and it's sub-directories to the supplied
	 * List.
	 * 
	 * @param dir
	 *            the directory to scan.
	 * @param fileList
	 *            all files in the specified directory and it's sub-directories.
	 */
	public static void addFilesRecursively(File dir, List<File> fileList) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				addFilesRecursively(file, fileList);
			else {
				fileList.add(file);
			}
		}
	}

}
