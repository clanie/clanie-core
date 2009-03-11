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
 * Sum Map.
 * 
 * @author Claus Nielsen
 */
@SuppressWarnings("serial")
public class SumMap<K, E extends Number> extends HashMap<K, E> {

	// TODO Set initial value and let that control which data type is used rather than letting the argument to add() controle it
	public SumMap(E initialValue) {
		super();
	}

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

}
