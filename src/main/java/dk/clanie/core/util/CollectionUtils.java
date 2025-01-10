package dk.clanie.core.util;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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


	/**
	 * Gets an element from given {@code iterable} matching given {@code predicate},
	 * or {@code null} if no matching element is found. 
	 */
	public static @Nullable <T> T anyMatching(@Nullable Iterable<T> iterable, Predicate<T> predicate) {
		return stream(iterable).filter(predicate).findAny().orElse(null);
	}

	
	/**
	 * Null-safe conversion to List.
	 * 
	 * If given Collection is already a List it is just returned (not copied).
	 */
	public static @NonNull <T> List<T> asList(Iterable<T> iterable) {
		return switch (iterable) {
		case null -> emptyList();
		case List<T> list -> list;
		case Collection<T> collection -> List.copyOf(collection);
		default -> stream(iterable).collect(toList());
		};
	}


	/**
	 * Null-safe conversion to Set.
	 * 
	 * If given Collection is already a Set it is just returned (not copied).
	 */
	public static @NonNull <T> Set<T> asSet(Iterable<T> iterable) {
		return switch (iterable) {
		case null -> Set.of();
		case Set<T> set -> set;
		case Collection<T> collection -> Set.copyOf(collection);
		default -> stream(iterable).collect(toSet());
		};
	}


	/**
	 * Null safe conversion from Iterable to Map.
	 * 
	 * Never returns null. If the input collection is null an empty Map is returned..
	 */
	public static @NonNull <K, V> Map<K, V> asMap(@Nullable Iterable<V> iterable, Function<? super V, ? extends K> keyMapper) {
		return stream(iterable).collect(toMap(keyMapper, v->v));
	}


	/**
	 * Null safe conversion from Iterable to Map.
	 *
	 * Never returns null. If the input collection is null an empty Map is returned.
	 */
	public static @NonNull <V, K, MV> Map<K, MV> asMap(@Nullable Iterable<V> iterable, Function<? super V, ? extends K> keyMapper, Function<? super V, ? extends MV> valueMapper) {
		return stream(iterable).collect(toMap(keyMapper, valueMapper));
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
