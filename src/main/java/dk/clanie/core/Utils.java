/**
 * Copyright (C) 2024, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.core;

import java.io.File;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import dk.clanie.core.util.BooleanUtils;
import dk.clanie.core.util.CollectionUtils;
import dk.clanie.core.util.FileUtils;
import dk.clanie.core.util.MiscUtils;
import dk.clanie.exception.RuntimeIOException;

/**
 * Utility methods.
 * 
 * This is just an aggregation of all the utility methods in other
 * classes in this package.
 * 
 * In Eclipse it makes it easy to get content-assist working for all
 * the utility methods by just adding this class under Java | Editor
 * | Content Assist | Favorites in Settings.
 */
public final class Utils {


	// ***** BooleanUtils methods *****

	public static boolean eq(Object o1, Object o2) {
		return BooleanUtils.eq(o1, o2);
	}


	public static boolean ne(Object o1, Object o2) {
		return BooleanUtils.ne(o1, o2);
	}


	// ***** CollectionUtils methods *****

	public static  @NonNull <T> Stream<T> stream(@Nullable Iterable<T> iterable) {
		return CollectionUtils.stream(iterable);
	}


	// ***** FileUtils methods *****

	
	/**
	 * Creates a copy of a file.
	 * 
	 * Shorthand for <code>copyFile(from, to, false)</code>, <code>false</code>
	 * meaning don't overwrite existing file.
	 * 
	 * @param from source File
	 * @param to destination File
	 * 
	 * @throws RuntimeIOException
	 */
	public static void copyFile(File from, File to) throws RuntimeIOException {
		FileUtils.copyFile(from, to, false);
	}


	/**
	 * Creates a copy of a file.
	 * 
	 * If the destination is a directory the file is copied to that directory
	 * with the same name as the source file.
	 * 
	 * @param from source File
	 * @param to destination File
	 * @param overwrite allow overwriting existing file
	 * 
	 * @throws RuntimeIOException
	 */
	@SuppressWarnings("resource")
	public static void copyFile(File from, File to, boolean overwrite) throws RuntimeIOException {
		FileUtils.copyFile(from, to, overwrite);
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
		FileUtils.closeChannels(channels);
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
		FileUtils.emptyAndDelete(fileOrDir);
	}


	/**
	 * Creates a List of all the files in a directory and it's subdirectories.
	 * 
	 * @param dir
	 *            the directory to scan.
	 * @return List of all Files in the directory and it's subdirectories.
	 */
	public static List<File> listFilesRecursively(File dir) {
		return FileUtils.listFilesRecursively(dir);
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
		FileUtils.addFilesRecursively(dir, fileCollection);
	}


	// ***** MiscUtils methods *****

	/**
	 * Selects first non-null argument.
	 *
	 * @param <T>
	 * @param args
	 * @return the first argument which is not null or null if all arguments are null.
	 */
	@SafeVarargs
	public static <T> T firstNonNull(T... args) {
		return MiscUtils.firstNonNull(args);
	}

}
