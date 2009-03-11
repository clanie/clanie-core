/**
 * Copyright (C) 2009, Claus Nielsen, cn@cn-consult.dk
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
package dk.clanie.collections;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * Number Map.
 * <p>
 * An <code>HashMap</code> which stores <code>Numbers</code> and lets you add to (and
 * subtract from) the <code>Numbers</code> stored in it without retrieving them first.
 * </p><p>
 * This means that you can replace code like this:</p><pre>if (sums.containsKey(key)) {
 *     Integer sum = sums.get(key);
 *     sum = sum.add(value);
 *     sums.put(key, sum);
 *} else {
 *     sums.put(value);
 *}</pre><p>with:</p><pre>sums.add(key, value);</pre>
 *
 * @author Claus Nielsen
 */
@SuppressWarnings("serial")
public class NumberMap<K, E extends Number> extends HashMap<K, E> {

	/**
	 * Factory method for creating new SumMap instances.
	 * 
	 * @param <K>
	 * @param <E>
	 * @return SumMap<K, E>
	 */
	public static <K, E extends Number> NumberMap<K, E> newSumMap() {
		return new NumberMap<K, E>();
	}

	/**
	 *  Default constructor.
	 */
	public NumberMap() {
		super();
	}

	/**
	 * Add to the Number specified.
	 *
	 * If the Map dosn't contain an entry with the specified key it is created.
	 *
	 * @param key
	 * @param addend
	 */
	public void add(K key, E addend) {
		if (!containsKey(key)) {
			put(key, addend);
		} else {
			if (addend instanceof BigDecimal) addBigDecimal(key, (BigDecimal)addend);
			else if (addend instanceof BigInteger) addBigInteger(key, (BigInteger)addend);
			else if (addend instanceof Byte) addByte(key, (Byte)addend);
			else if (addend instanceof Double) addDouble(key, (Double)addend);
			else if (addend instanceof Float) addFloat(key, (Float)addend);
			else if (addend instanceof Integer) addInteger(key, (Integer)addend);
			else if (addend instanceof Long) addLong(key, (Long)addend);
			else if (addend instanceof Short) addShort(key, (Short)addend);
			else throw new UnsupportedOperationException();
		}
	}

	/**
	 * Subtract to the Number specified.
	 *
	 * If the Map dosn't contain an entry with the specified key it is created.
	 *
	 * @param key
	 * @param addend
	 */
	public void sub(K key, E addend) {
		if (addend instanceof BigDecimal) subBigDecimal(key, (BigDecimal)addend);
		else if (addend instanceof BigInteger) subBigInteger(key, (BigInteger)addend);
		else if (addend instanceof Byte) subByte(key, (Byte)addend);
		else if (addend instanceof Double) subDouble(key, (Double)addend);
		else if (addend instanceof Float) subFloat(key, (Float)addend);
		else if (addend instanceof Integer) subInteger(key, (Integer)addend);
		else if (addend instanceof Long) subLong(key, (Long)addend);
		else if (addend instanceof Short) subShort(key, (Short)addend);
		else throw new UnsupportedOperationException();
	}

	
	// Add-methods --------------------------------------------------------------

	@SuppressWarnings("unchecked")
	private void addBigDecimal(K key, BigDecimal addend) {
		put(key, (E) addend.add((BigDecimal) get(key)));
	}

	@SuppressWarnings("unchecked")
	private void addBigInteger(K key, BigInteger addend) {
		put(key, (E) addend.add((BigInteger) get(key)));
	}

	@SuppressWarnings("unchecked")
	private void addByte(K key, byte addend) {
		put(key, (E) new Byte((byte) (addend + get(key).byteValue())));
	}

	@SuppressWarnings("unchecked")
	private void addDouble(K key, double addend) {
		put(key, (E) new Double(addend + get(key).doubleValue()));
	}

	@SuppressWarnings("unchecked")
	private void addFloat(K key, float addend) {
		put(key, (E) new Float(addend + get(key).floatValue()));
	}

	@SuppressWarnings("unchecked")
	private void addInteger(K key, int addend) {
		put(key, (E) new Integer(addend + get(key).intValue()));
	}

	@SuppressWarnings("unchecked")
	private void addLong(K key, long addend) {
		put(key, (E) new Long(addend + get(key).longValue()));
	}

	@SuppressWarnings("unchecked")
	private void addShort(K key, short addend) {
		put(key, (E) new Short((short) (addend + get(key).shortValue())));
	}

	
	// Substract-methods --------------------------------------------------------

	@SuppressWarnings("unchecked")
	private void subBigDecimal(K key, BigDecimal subtrahend) {
		put(key, (E) ((BigDecimal) (containsKey(key) ? get(key) : BigDecimal.ZERO)).subtract(subtrahend));
	}

	@SuppressWarnings("unchecked")
	private void subBigInteger(K key, BigInteger subtrahend) {
		put(key, (E) ((BigInteger) (containsKey(key) ? get(key) : BigInteger.ZERO)).subtract(subtrahend));
	}

	@SuppressWarnings("unchecked")
	private void subByte(K key, byte subtrahend) {
		put(key, (E) new Byte((byte) ((containsKey(key) ? get(key).byteValue() : (byte)0) - subtrahend)));
	}

	@SuppressWarnings("unchecked")
	private void subDouble(K key, double subtrahend) {
		put(key, (E) new Double((containsKey(key) ? get(key).doubleValue() : 0d) - subtrahend));
	}

	@SuppressWarnings("unchecked")
	private void subFloat(K key, float subtrahend) {
		put(key, (E) new Float((containsKey(key) ? get(key).floatValue() : 0f) - subtrahend));
	}

	@SuppressWarnings("unchecked")
	private void subInteger(K key, int subtrahend) {
		put(key, (E) new Integer((containsKey(key) ? get(key).intValue() : 0) - subtrahend));
	}

	@SuppressWarnings("unchecked")
	private void subLong(K key, long subtrahend) {
		put(key, (E) new Long((containsKey(key) ? get(key).longValue() : 0l) - subtrahend));
	}

	@SuppressWarnings("unchecked")
	private void subShort(K key, short subtrahend) {
		put(key, (E) new Short((short) ((containsKey(key) ? get(key).shortValue() : (short)0) - subtrahend)));
	}

}
