/*
 * Copyright (C) 2025, Claus Nielsen, clausn999@gmail.com
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

import static dk.clanie.core.Utils.eq;
import static dk.clanie.core.util.SortDirection.ASC;
import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class CollectionUtils {


	private CollectionUtils() {
		// Not meant to be instantiated
	}


	/**
	 * Gets an element from given {@code iterable} matching given {@code predicate},
	 * or an empty Optional if no matching element is found. 
	 */
	public static <T> Optional<T> anyMatching(@Nullable Iterable<T> iterable, Predicate<T> predicate) {
		return stream(iterable).filter(predicate).findAny();
	}


	/**
	 * Checks if given {@code iterable} contains given {@code item}. 
	 */
	public static <T> boolean contains(@Nullable Iterable<T> iterable, T item) {
		return stream(iterable).anyMatch(x -> eq(x, item));
	}


	/**
	 * Checks if given List is sorted in given order.
	 */
	public static <T, E extends Comparable<? super E>> boolean isSorted(@Nullable List<T> list, Function<T, E> extractor, SortDirection direction) {
		if (list == null || list.size() < 2) return true;
		Comparator<E> comparator = (direction == ASC) ? naturalOrder() : reverseOrder();
		E previous = extractor.apply(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			E current = extractor.apply(list.get(i));
			// Use the comparator directly in the loop.
			if (comparator.compare(previous, current) > 0) return false;
			previous = current;
		}
		return true;
	}


	/**
	 * Null-safe conversion to List (unmodifiable).
	 * 
	 * If given Collection is already a List it is copied to ensure immutability.
	 */
	public static @NonNull <T> List<T> asList(Iterable<T> iterable) {
		return switch (iterable) {
		case null -> emptyList();
		case List<T> list -> List.copyOf(list);
		case Collection<T> collection -> List.copyOf(collection);
		default -> stream(iterable).toList();
		};
	}


	/**
	 * Null-safe conversion to Set (unmodifiable).
	 * 
	 * If given Collection is already a Set it is copied to ensure immutability.
	 */
	public static @NonNull <T> Set<T> asSet(Iterable<T> iterable) {
		return switch (iterable) {
		case null -> Set.of();
		case Set<T> set -> Set.copyOf(set);
		case Collection<T> collection -> Set.copyOf(collection);
		default -> Set.copyOf(stream(iterable).collect(toSet()));
		};
	}


	/**
	 * Null safe conversion from Iterable to Map (unmodifiable).
	 * 
	 * Never returns null. If the input collection is null an empty Map is returned.
	 */
	public static @NonNull <K, V> Map<K, V> asMap(@Nullable Iterable<V> iterable, Function<? super V, ? extends K> keyMapper) {
		return Map.copyOf(stream(iterable).collect(toMap(keyMapper, v->v)));
	}


	/**
	 * Null safe conversion from Iterable to Map (unmodifiable).
	 *
	 * Never returns null. If the input collection is null an empty Map is returned.
	 */
	public static @NonNull <V, K, MV> Map<K, MV> asMap(@Nullable Iterable<V> iterable, Function<? super V, ? extends K> keyMapper, Function<? super V, ? extends MV> valueMapper) {
		return Map.copyOf(stream(iterable).collect(toMap(keyMapper, valueMapper)));
	}


	/**
	 * Filters elements from given {@code iterable} and collects the result to a List (unmodifiable).
	 */
	public static @NonNull <T> List<T> filterList(@Nullable Iterable<T> iterable, Predicate<? super T> predicate) {
		return stream(iterable).filter(predicate).toList();
	}


	/**
	 * Filters elements from given {@code iterable} and collects the result to a Set (unmodifiable).
	 */
	public static @NonNull <T> Set<T> filterSet(@Nullable Iterable<T> iterable, Predicate<? super T> predicate) {
		return Set.copyOf(stream(iterable).filter(predicate).collect(toSet()));
	}


	/**
	 * Copies and transforms all elements from given {@code iterable} to a new List (unmodifiable) using given {@code mapper}. 
	 */
	public static @NonNull <T, R> List<R> mapList(@Nullable Iterable<T> iterable, Function<? super T, ? extends R> mapper) {
		return List.copyOf(stream(iterable).map(mapper).collect(toList()));
	}


	/**
	 * Copies and transforms all elements from given {@code iterable} to a new Set (unmodifiable) using given {@code mapper}. 
	 */
	public static @NonNull <T, R> Set<R> mapSet(@Nullable Iterable<T> iterable, Function<? super T, ? extends R> mapper) {
		return Set.copyOf(stream(iterable).map(mapper).collect(toSet()));
	}


	/**
	 * Returns a sequential {@link Stream} of the contents of {@code iterable}, delegating to
	 * {@link Collection#stream} if possible.
	 * 
	 * Never returns null. If given {@code iterable} is {@code null} an empty stream is returned.
	 */
	public static @NonNull <T> Stream<T> stream(@Nullable Iterable<T> iterable) {
		if (iterable == null) return Stream.empty();
		else if (iterable instanceof Collection<T> c) return c.stream();
		else return StreamSupport.stream(iterable.spliterator(), false);
	}


	/**
	 * Returns a sequential {@link Stream} of the contents of {@code array}.
	 * 
	 * Never returns null. If given {@code array} is {@code null} an empty stream is returned.
	 */
	public static @NonNull <T> Stream<T> stream(@Nullable T[] array) {
		if (array == null) return Stream.empty();
		else return Arrays.stream(array);
	}


	/**
	 * Returns a parallel {@link Stream} of the contents of {@code iterable}, delegating to
	 * {@link Collection#stream} if possible.
	 * 
	 * Never returns null. If given {@code array} is {@code null} an empty stream is returned.
	 */
	public static  @NonNull <T> Stream<T> parallelStream(@Nullable Iterable<T> iterable) {
		if (iterable == null) return Stream.empty();
		if (iterable instanceof Collection<T> c) return c.parallelStream();
		return StreamSupport.stream(iterable.spliterator(), true);
	}


	/**
	 * Returns a reverse-order {@link Stream} of the contents of {@code list}.
	 *
	 * Never returns null. If given {@code list} is {@code null} an empty stream is returned.
	 */
	public static @NonNull <T> Stream<T> reverseStream(@Nullable List<T> list) {
		if (list == null) return Stream.empty();
		ListIterator<T> listIterator = list.listIterator(list.size());
		Stream<T> reversedStream = StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(
						new Iterator<T>() {
							public boolean hasNext() {
								return listIterator.hasPrevious();
							}
							public T next() {
								return listIterator.previous();
							}
						},
						Spliterator.ORDERED), false);
		return reversedStream;
	}


}
