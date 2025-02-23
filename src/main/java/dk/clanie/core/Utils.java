/**
 * Copyright (C) 2024-2025, Claus Nielsen, clausn999@gmail.com
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
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import dk.clanie.core.util.BooleanUtils;
import dk.clanie.core.util.CollectionUtils;
import dk.clanie.core.util.DateTimeUtils;
import dk.clanie.core.util.FileUtils;
import dk.clanie.core.util.MiscUtils;
import dk.clanie.core.util.StringUtils;
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


	private Utils() {
		// Not meant to be instantiated.
	}


	// ***** BooleanUtils methods *****

	/**
	 * Statically importable alias for Objects.equals.
	 */
	public static boolean eq(Object o1, Object o2) {
		return BooleanUtils.eq(o1, o2);
	}


	/**
	 * Statically importable alias for !Objects.equals.
	 */
	public static boolean ne(Object o1, Object o2) {
		return BooleanUtils.ne(o1, o2);
	}


	/**
	 * Checks if a is greater than b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean gt(T a, T b) {
		return BooleanUtils.gt(a, b);
	}


	/**
	 * Checks if a is greater than or equal to b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean ge(T a, T b) {
		return BooleanUtils.ge(a, b);
	}


	/**
	 * Checks if a is less than b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean lt(T a, T b) {
		return BooleanUtils.lt(a, b);
	}


	/**
	 * Checks if a is less than or equal to b, considering null less than anything else.
	 */
	public static <T extends Comparable<T>> boolean le(T a, T b) {
		return BooleanUtils.le(a, b);
	}



	// ***** CollectionUtils methods *****


	/**
	 * Gets an element from given {@code iterable} matching given {@code predicate},
	 * or {@code null} if no matching element is found. 
	 */
	public static @Nullable <T> T anyMatching(@Nullable Iterable<T> iterable, Predicate<T> predicate) {
		return CollectionUtils.anyMatching(iterable, predicate);
	}


	/**
	 * Checks if given {@code iterable} contains given {@code item}. 
	 */
	public static <T> boolean contains(@Nullable Iterable<T> iterable, T item) {
		return CollectionUtils.contains(iterable, item);
	}


	/**
	 * Null-safe conversion to List.
	 * 
	 * If given Collection is already a List it is just returned (not copied).
	 */
	public static @NonNull <T> List<T> asList(Iterable<T> iterable) {
		return CollectionUtils.asList(iterable);
	}


	/**
	 * Null-safe conversion to Set.
	 * 
	 * If given Collection is already a Set it is just returned (not copied).
	 */
	public static @NonNull <T> Set<T> asSet(Iterable<T> iterable) {
		return CollectionUtils.asSet(iterable);
	}


	/**
	 * Null safe conversion from Iterable to Map.
	 * 
	 * Never returns null. If the input collection is null an empty Map is returned..
	 */
	public static @NonNull <K, V> Map<K, V> asMap(@Nullable Iterable<V> iterable, Function<? super V, ? extends K> keyMapper) {
		return CollectionUtils.asMap(iterable, keyMapper);
	}


	/**
	 * Null safe conversion from Iterable to Map.
	 *
	 * Never returns null. If the input collection is null an empty Map is returned.
	 */
	public static @NonNull <V, K, MV> Map<K, MV> asMap(@Nullable Iterable<V> iterable, Function<? super V, ? extends K> keyMapper, Function<? super V, ? extends MV> valueMapper) {
		return CollectionUtils.asMap(iterable, keyMapper, valueMapper);
	}


	/**
	 * Copies and transforms all elements from given {@code iterable} to a new List using given {@code mapper}. 
	 */
	public static <T, R> List<R> mapList(@Nullable Iterable<T> iterable, Function<? super T, ? extends R> mapper) {
		return CollectionUtils.mapList(iterable, mapper);
	}


	/**
	 * Copies and transforms all elements from given {@code iterable} to a new Set using given {@code mapper}. 
	 */
	public static <T, R> Set<R> mapSet(@Nullable Iterable<T> iterable, Function<? super T, ? extends R> mapper) {
		return CollectionUtils.mapSet(iterable, mapper);
	}


	/**
	 * Returns a sequential {@link Stream} of the contents of {@code iterable}, delegating to
	 * {@link Collection#stream} if possible.
	 * 
	 * Never returns null. If given {@code iterable} is {@code null} an empty stream is returned.
	 */
	public static @NonNull <T> Stream<T> stream(@Nullable Iterable<T> iterable) {
		return CollectionUtils.stream(iterable);
	}


	/**
	 * Returns a sequential {@link Stream} of the contents of {@code array}.
	 * 
	 * Never returns null. If given {@code array} is {@code null} an empty stream is returned.
	 */
	public static @NonNull <T> Stream<T> stream(@Nullable T[] array) {
		return CollectionUtils.stream(array);
	}


	/**
	 * Returns a parallel {@link Stream} of the contents of {@code iterable}, delegating to
	 * {@link Collection#stream} if possible.
	 * 
	 * Never returns null. If given {@code array} is {@code null} an empty stream is returned.
	 */
	public static  @NonNull <T> Stream<T> parallelStream(@Nullable Iterable<T> iterable) {
		return CollectionUtils.parallelStream(iterable);
	}


	/**
	 * Returns a reverse-order {@link Stream} of the contents of {@code list}.
	 *
	 * Never returns null. If given {@code list} is {@code null} an empty stream is returned.
	 */
	public static @NonNull <T> Stream<T> reverseStream(@Nullable List<T> list) {
		return CollectionUtils.reverseStream(list);
	}



	// ***** DateTimeUtils methods *****



	/**
	 * Checks if given {code instant} is before the start of current day in the default time-zone.
	 */
	public static boolean beforeToday(Instant instant) {
		return DateTimeUtils.beforeToday(instant);
	}


	/**
	 * Checks if given {code instant} is before the start of current day in the given given time-zone.
	 * 
	 * Null is considered less than any non-null Instant.
	 */
	public static boolean beforeToday(ZoneId zoneId, @Nullable Instant instant) {
		return DateTimeUtils.beforeToday(zoneId, instant);
	}


	/**
	 * Returns the earlier of the two given instants.
	 * 
	 * Null is considered less than any non-null Instant.
	 */
	public static @Nullable Instant min(@Nullable Instant a, @Nullable Instant b) {
		return DateTimeUtils.min(a, b);
	}


	/**
	 * Returns the later of the two given instants.
	 * 
	 * Null is considered less than any non-null Instant.
	 */
	public static @Nullable Instant max(@Nullable Instant a, @Nullable Instant b) {
		return DateTimeUtils.max(a, b);
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


	/**
	 * Reads a utf-8 text file on the class path into a list of lines.
	 */
	public static List<String> readTextResource(String resource) {
		return FileUtils.readTextResource(resource);
	}


	/**
	 * Reads a text file on the class path into a list of lines.
	 */
	public static List<String> readTextResource(String resource, Charset charset) {
		return FileUtils.readTextResource(resource);
	}



	// ***** MiscUtils methods *****


	/**
	 * Copies all matching properties from source to target object and returns the updated target object.
	 */
	public static <T> T copy(Object source, T target) {
		return MiscUtils.copy(source, target);
	}


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


	/**
	 * Returns an {@code Optional} describing the given value, if
	 * non-{@code null}, otherwise returns an empty {@code Optional}.
	 * 
	 * Short for {@link Optional#ofNullable(Object)}.
	 *
	 * @param value the possibly-{@code null} value to describe
	 * @param <T> the type of the value
	 * @return an {@code Optional} with a present value if the specified value
	 *         is non-{@code null}, otherwise an empty {@code Optional}
	 */
	public static @NonNull <T> Optional<T> opt(T value) {
		return MiscUtils.opt(value);
	}


	/**
	 * Executes given Runnable and logs the execution time in ms along with given {@code message}.
	 */
	public static <T> T logExecutionTime(String message, Logger log, Callable<T> callable) {
		return MiscUtils.logExecutionTime(message, log, callable);
	}


	/**
	 * Gets the stack trace of the given exception as a String.
	 */
	public static String stackTraceOf(Exception e) {
		return MiscUtils.stackTraceOf(e);
	}



	// ***** StringUtils methods *****


	/**
	 * Null-safe toString.
	 */
	public static String asString(@Nullable Object object) {
		return StringUtils.asString(object);
	}


	/**
	 * Null-safe String to UUID conversion.
	 */
	public static UUID asUuid(String s) {
		return StringUtils.asUuid(s);
	}


	/**
	 * Generates a comma separated list of given values converted to strings.
	 * 
	 * No escaping is performed.
	 */
	public static @NonNull String csv(@Nullable Iterable<?> iterable) {
		return StringUtils.csv(iterable);
	}


	/**
	 * Generates a comma separated list of given values first mapped using given mapper and then converted to strings.
	 * 
	 * No escaping is performed.
	 */
	public static @NonNull <T> String csv(@Nullable Iterable<T> iterable, Function<? super T, ?> mapper) {
		return StringUtils.csv(iterable, mapper);
	}


}
