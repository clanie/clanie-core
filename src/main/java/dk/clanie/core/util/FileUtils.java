/**
 * Copyright (C) 2008, Claus Nielsen, clausn999@gmail.com
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dk.clanie.exception.RuntimeIOException;
import lombok.extern.slf4j.Slf4j;

/**
 * File Utility Methods.
 * 
 * @author Claus Nielsen
 */
@Slf4j
public class FileUtils {

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
	@SuppressWarnings("resource")
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
				log.error(FAILED_TO_CLOSE_CHANNEL, e);
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
	 * Creates a List of all the files in a directory and it's subdirectories.
	 * 
	 * @param dir
	 *            the directory to scan.
	 * @return List of all Files in the directory and it's subdirectories.
	 */
	public static List<File> listFilesRecursively(File dir) {
		List<File> fileList = new ArrayList<>();
		addFilesRecursively(dir, fileList);
		return fileList;
	}

	
	/**
	 * Adds all files in a directory and it's subdirectories to the supplied Collection.
	 * 
	 * @param dir
	 *            the directory to scan.
	 * @param fileCollection
	 *            the Collection to update.
	 */
	public static void addFilesRecursively(File dir, Collection<File> fileCollection) {
		log.debug("Adding files from {}", dir.getPath());
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				addFilesRecursively(file, fileCollection);
			else {
				fileCollection.add(file);
			}
		}
	}


}
