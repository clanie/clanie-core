/**
 * Copyright (C) 2009-2024, Claus Nielsen, clausn999@gmail.com
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

import static dk.clanie.core.collections.NumberMap.newBigDecimalMap;
import static dk.clanie.core.collections.NumberMap.newBigIntegerMap;
import static dk.clanie.core.collections.NumberMap.newByteMap;
import static dk.clanie.core.collections.NumberMap.newDoubleMap;
import static dk.clanie.core.collections.NumberMap.newFloatMap;
import static dk.clanie.core.collections.NumberMap.newIntegerMap;
import static dk.clanie.core.collections.NumberMap.newLongMap;
import static dk.clanie.core.collections.NumberMap.newShortMap;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

/**
 * Test NumberMap.
 * 
 * @author Claus Nielsen
 */
public class NumberMapTest {

	@Test
	public void testBigDecimal() {
		NumberMap<String, BigDecimal> map = newBigDecimalMap();
		map.add("x", BigDecimal.valueOf(1l));
		map.add("y", BigDecimal.valueOf(2l));
		map.add("x", BigDecimal.valueOf(3l));
		assertThat(map.get("x")).isEqualTo(BigDecimal.valueOf(4l));
		assertThat(map.get("y")).isEqualTo(BigDecimal.valueOf(2l));
		map.sub("z", BigDecimal.valueOf(5l));
		map.sub("z", BigDecimal.valueOf(1l));
		assertThat(map.get("z")).isEqualTo(BigDecimal.valueOf(-6l));
	}

	@Test
	public void testBigInteger() {
		NumberMap<String, BigInteger> map = newBigIntegerMap();
		map.add("x", BigInteger.valueOf(1l));
		map.add("y", BigInteger.valueOf(2l));
		map.add("x", BigInteger.valueOf(3l));
		assertThat(map.get("x")).isEqualTo(BigInteger.valueOf(4l));
		assertThat(map.get("y")).isEqualTo(BigInteger.valueOf(2l));
		map.sub("z", BigInteger.valueOf(5l));
		map.sub("z", BigInteger.valueOf(1l));
		assertThat(map.get("z")).isEqualTo(BigInteger.valueOf(-6l));
	}

	@Test
	public void testByte() {
		NumberMap<String, Byte> map = newByteMap();
		map.add("x", (byte)1);
		map.add("y", (byte)2);
		map.add("x", Byte.valueOf((byte)3));
		assertThat(map.get("x")).isEqualTo((byte)4);
		assertThat(map.get("y")).isEqualTo((byte)2);
		map.sub("z", (byte)5);
		map.sub("z", (byte)1);
		assertThat(map.get("z")).isEqualTo((byte)-6);
	}

	@Test
	public void testDouble() {
		NumberMap<String, Double> map = newDoubleMap();
		map.add("x", 1.0);
		map.add("y", 2.0);
		map.add("x", 3d);
		assertThat(map.get("x")).isEqualTo(4d);
		assertThat(map.get("y")).isEqualTo(2d);
		map.sub("z", 5.0);
		map.sub("z", 1.0);
		assertThat(map.get("z")).isEqualTo(-6.0);
	}

	@Test
	public void testFloat() {
		NumberMap<String, Float> map = newFloatMap();
		map.add("x", 1.0f);
		map.add("y", 2.0f);
		map.add("x", 3f);
		assertThat(map.get("x")).isEqualTo(4f);
		assertThat(map.get("y")).isEqualTo(2f);
		map.sub("z", 5f);
		map.sub("z", 1f);
		assertThat(map.get("z")).isEqualTo(-6f);
	}

	@Test
	public void testInteger() {
		NumberMap<String, Integer> map = newIntegerMap();
		map.add("x", 1);
		map.add("y", 2);
		map.add("x", Integer.valueOf(3));
		assertThat(map.get("x")).isEqualTo(4);
		assertThat(map.get("y")).isEqualTo(2);
		map.sub("z", 5);
		map.sub("z", 1);
		assertThat(map.get("z")).isEqualTo(-6);
	}

	@Test
	public void testLong() {
		NumberMap<String, Long> map = newLongMap();
		map.add("x", 999999999991l);
		map.add("y", 999999999992l);
		map.add("x", Long.valueOf(999999999993l));
		assertThat(map.get("x")).isEqualTo(1999999999984l);
		assertThat(map.get("y")).isEqualTo(999999999992l);
		map.sub("z", 5l);
		map.sub("z", 1l);
		assertThat(map.get("z")).isEqualTo(-6l);
	}

	@Test
	public void testShort() {
		NumberMap<String, Short> map = newShortMap();
		map.add("x", (short)1);
		map.add("y", (short)2);
		map.add("x", Short.valueOf((short)3));
		assertThat(map.get("x")).isEqualTo((short)4);
		assertThat(map.get("y")).isEqualTo((short)2);
		map.sub("z", (short)5);
		map.sub("z", (short)1);
		assertThat(map.get("z")).isEqualTo((short)-6);
	}

}
