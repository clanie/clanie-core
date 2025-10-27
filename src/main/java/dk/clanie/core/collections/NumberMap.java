/**
 * Copyright (C) 2009-2025, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.core.collections;

import static dk.clanie.core.Utils.opt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * Number Map.
 * <p>
 * An <code>HashMap</code> which stores <code>Numbers</code> and lets you add to (and
 * subtract from) the <code>Numbers</code> stored in it without retrieving them first.
 * </p><p>
 * This means that you can replace code like this:</p><pre>if (map.containsKey(key)) {
 *     Integer sum = map.get(key);
 *     sum = sum.add(value);
 *     map.put(key, sum);
 *} else {
 *     map.put(value);
 *}</pre><p>with:</p><pre>map.add(key, value);</pre>
 *
 * @author Claus Nielsen
 */
@SuppressWarnings("serial")
public abstract class NumberMap<K, E extends Number> extends HashMap<K, E> {


	/**
	 * Creates a NumberMap for instances of a concrete subclass of Number.
	 * 
	 * @param <K> key type
	 * @param <E> element type
	 * @param elementType
	 * @return NumberMap&lt;K, E&gt;
	 */
	@SuppressWarnings("unchecked")
	public static <K, E extends Number> NumberMap<K, E> newNumberMap(Class<E> elementType) {
		if (elementType == BigDecimal.class) return (NumberMap<K, E>) newBigDecimalMap();
		if (elementType == BigInteger.class) return (NumberMap<K, E>) newBigIntegerMap();
		if (elementType == Byte.class) return (NumberMap<K, E>) newByteMap();
		if (elementType == Long.class) return (NumberMap<K, E>) newLongMap();
		if (elementType == Double.class) return (NumberMap<K, E>) newDoubleMap();
		if (elementType == Float.class) return (NumberMap<K, E>) newFloatMap();
		if (elementType == Integer.class) return (NumberMap<K, E>) newIntegerMap();
		if (elementType == Short.class) return (NumberMap<K, E>) newShortMap();
		else throw new UnsupportedOperationException();
	}


	/**
	 * Creates a NumberMap for BigDecimals.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K, BigDecimal&gt;
	 */
	public static <K> NumberMap<K, BigDecimal> newBigDecimalMap() {
		return new NumberMap<K, BigDecimal>() {
			@Override
			public void add(K key, BigDecimal addend) {
				put(key, opt(get(key)).orElse(BigDecimal.ZERO).add(addend));
			}
			@Override
			public void sub(K key, BigDecimal subtrahend) {
				put(key, opt(get(key)).orElse(BigDecimal.ZERO).subtract(subtrahend));
			}
		};
	}


	/**
	 * Creates a NumberMap for BigIntegers.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K, BigInteger&gt;
	 */
	public static <K> NumberMap<K, BigInteger> newBigIntegerMap() {
		return new NumberMap<K, BigInteger>() {
			@Override
			public void add(K key, BigInteger addend) {
				put(key, opt(get(key)).orElse(BigInteger.ZERO).add(addend));
			}
			@Override
			public void sub(K key, BigInteger subtrahend) {
				put(key, opt(get(key)).orElse(BigInteger.ZERO).subtract(subtrahend));
			}
		};
	}


	/**
	 * Creates a NumberMap for Bytes.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K, Byte&gt;
	 */
	public static <K> NumberMap<K, Byte> newByteMap() {
		return new NumberMap<K, Byte>() {
			@Override
			public void add(K key, Byte addend) {
				put(key, (byte)(opt(get(key)).orElse((byte)0) + addend));
			}
			@Override
			public void sub(K key, Byte subtrahend) {
				put(key, (byte)(opt(get(key)).orElse((byte)0) - subtrahend));
			}
		};
	}


	/**
	 * Creates a NumberMap for Doubles.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K, Double&gt;
	 */
	public static <K> NumberMap<K, Double> newDoubleMap() {
		return new NumberMap<K, Double>() {
			@Override
			public void add(K key, Double addend) {
				put(key, opt(get(key)).orElse(0d) + addend);
			}
			@Override
			public void sub(K key, Double subtrahend) {
				put(key, opt(get(key)).orElse(0d) - subtrahend);
			}
		};
	}


	/**
	 * Creates a NumberMap for Floats.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K, Float&gt;
	 */
	public static <K> NumberMap<K, Float> newFloatMap() {
		return new NumberMap<K, Float>() {
			@Override
			public void add(K key, Float addend) {
				put(key, opt(get(key)).orElse(0f) + addend);
			}
			@Override
			public void sub(K key, Float subtrahend) {
				put(key, opt(get(key)).orElse(0f) - subtrahend);
			}
		};
	}


	/**
	 * Creates a NumberMap for Integers.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K, Integer&gt;
	 */
	public static <K> NumberMap<K, Integer> newIntegerMap() {
		return new NumberMap<K, Integer>() {
			@Override
			public void add(K key, Integer addend) {
				put(key, opt(get(key)).orElse(0) + addend);
			}
			@Override
			public void sub(K key, Integer subtrahend) {
				put(key, opt(get(key)).orElse(0) - subtrahend);
			}
		};
	}


	/**
	 * Creates a NumberMap for Longs.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K&gt;
	 */
	public static <K> NumberMap<K, Long> newLongMap() {
		return new NumberMap<K, Long>() {
			@Override
			public void add(K key, Long addend) {
				put(key, opt(get(key)).orElse(0L) + addend);
			}
			@Override
			public void sub(K key, Long subtrahend) {
				put(key, opt(get(key)).orElse(0L) - subtrahend);
			}
		};
	}


	/**
	 * Creates a NumberMap for Shorts.
	 * 
	 * @param <K>
	 * @return NumberMap&lt;K&gt;
	 */
	public static <K> NumberMap<K, Short> newShortMap() {
		return new NumberMap<K, Short>() {
			@Override
			public void add(K key, Short addend) {
				put(key, (short) (opt(get(key)).orElse((short) 0) + addend));
			}
			@Override
			public void sub(K key, Short subtrahend) {
				put(key, (short) (opt(get(key)).orElse((short) 0) - subtrahend));
			}
		};
	}


	/**
	 * Add to the Number specified.
	 *
	 * If the Map dosn't contain an entry with the specified key it is created.
	 *
	 * @param key
	 * @param addend
	 */
	public abstract void add(K key, E addend);


	/**
	 * Subtract to the Number specified.
	 *
	 * If the Map dosn't contain an entry with the specified key it is created.
	 *
	 * @param key
	 * @param addend
	 */
	public abstract void sub(K key, E addend);


}
