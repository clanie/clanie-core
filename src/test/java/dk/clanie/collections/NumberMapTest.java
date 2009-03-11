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

import static dk.clanie.collections.NumberMap.newSumMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * Test NumberMap.
 * 
 * @author Claus Nielsen
 */
public class NumberMapTest {

	@Test
	public void testBigDecimal() {
		NumberMap<String, BigDecimal> sumMap = newSumMap();
		sumMap.add("x", BigDecimal.valueOf(1l));
		sumMap.add("y", BigDecimal.valueOf(2l));
		sumMap.add("x", BigDecimal.valueOf(3l));
		assertThat(sumMap.get("x"), is(equalTo(BigDecimal.valueOf(4l))));
		assertThat(sumMap.get("y"), is(equalTo(BigDecimal.valueOf(2l))));
		sumMap.sub("z", BigDecimal.valueOf(5l));
		sumMap.sub("z", BigDecimal.valueOf(1l));
		assertThat(sumMap.get("z"), is(equalTo(BigDecimal.valueOf(-6l))));
	}

	@Test
	public void testBigInteger() {
		NumberMap<String, BigInteger> sumMap = newSumMap();
		sumMap.add("x", BigInteger.valueOf(1l));
		sumMap.add("y", BigInteger.valueOf(2l));
		sumMap.add("x", BigInteger.valueOf(3l));
		assertThat(sumMap.get("x"), is(equalTo(BigInteger.valueOf(4l))));
		assertThat(sumMap.get("y"), is(equalTo(BigInteger.valueOf(2l))));
		sumMap.sub("z", BigInteger.valueOf(5l));
		sumMap.sub("z", BigInteger.valueOf(1l));
		assertThat(sumMap.get("z"), is(equalTo(BigInteger.valueOf(-6l))));
	}

	@Test
	public void testByte() {
		NumberMap<String, Byte> sumMap = newSumMap();
		sumMap.add("x", (byte)1);
		sumMap.add("y", (byte)2);
		sumMap.add("x", new Byte((byte)3));
		assertThat(sumMap.get("x"), is((byte)4));
		assertThat(sumMap.get("y"), is((byte)2));
		sumMap.sub("z", (byte)5);
		sumMap.sub("z", (byte)1);
		assertThat(sumMap.get("z"), is((byte)-6));
	}

	@Test
	public void testDouble() {
		NumberMap<String, Double> sumMap = newSumMap();
		sumMap.add("x", 1.0);
		sumMap.add("y", 2.0);
		sumMap.add("x", 3d);
		assertThat(sumMap.get("x"), is(4d));
		assertThat(sumMap.get("y"), is(2d));
		sumMap.sub("z", 5.0);
		sumMap.sub("z", 1.0);
		assertThat(sumMap.get("z"), is(-6.0));
	}

	@Test
	public void testFloat() {
		NumberMap<String, Float> sumMap = newSumMap();
		sumMap.add("x", 1.0f);
		sumMap.add("y", 2.0f);
		sumMap.add("x", 3f);
		assertThat(sumMap.get("x"), is(4f));
		assertThat(sumMap.get("y"), is(2f));
		sumMap.sub("z", 5f);
		sumMap.sub("z", 1f);
		assertThat(sumMap.get("z"), is(-6f));
	}

	@Test
	public void testInteger() {
		NumberMap<String, Integer> sumMap = newSumMap();
		sumMap.add("x", 1);
		sumMap.add("y", 2);
		sumMap.add("x", Integer.valueOf(3));
		assertThat(sumMap.get("x"), is(4));
		assertThat(sumMap.get("y"), is(2));
		sumMap.sub("z", 5);
		sumMap.sub("z", 1);
		assertThat(sumMap.get("z"), is(-6));
	}

	@Test
	public void testLong() {
		NumberMap<String, Long> sumMap = newSumMap();
		sumMap.add("x", 999999999991l);
		sumMap.add("y", 999999999992l);
		sumMap.add("x", Long.valueOf(999999999993l));
		assertThat(sumMap.get("x"), is(1999999999984l));
		assertThat(sumMap.get("y"), is(999999999992l));
		sumMap.sub("z", 5l);
		sumMap.sub("z", 1l);
		assertThat(sumMap.get("z"), is(-6l));
	}

	@Test
	public void testShort() {
		NumberMap<String, Short> sumMap = newSumMap();
		sumMap.add("x", (short)1);
		sumMap.add("y", (short)2);
		sumMap.add("x", new Short((short)3));
		assertThat(sumMap.get("x"), is((short)4));
		assertThat(sumMap.get("y"), is((short)2));
		sumMap.sub("z", (short)5);
		sumMap.sub("z", (short)1);
		assertThat(sumMap.get("z"), is((short)-6));
	}

}
