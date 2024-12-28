/**
 * Copyright (C) 2007-2024, Claus Nielsen, clausn999@gmail.com
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

import static dk.clanie.core.collections.Tuple.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dk.clanie.core.collections.Tuple.Pair;
import dk.clanie.core.collections.Tuple.Quadruple;
import dk.clanie.core.collections.Tuple.Quintuple;
import dk.clanie.core.collections.Tuple.Sextuple;
import dk.clanie.core.collections.Tuple.Singleton;
import dk.clanie.core.collections.Tuple.Triple;

/**
 * Test Tuple.
 * 
 * @author Claus Nielsen
 */
public class TupleTest {


	@Test
	public void testInstantiation() {
		Tuple single = Tuple.of("one");
		assertThat(single).isInstanceOf(Singleton.class);
		assertThat(single).isNotInstanceOf(Pair.class);
		Tuple pair = Tuple.of("one", "two");
		assertThat(pair).isInstanceOf(Pair.class);
		assertThat(pair).isNotInstanceOf(Triple.class);
		Tuple triple = Tuple.of("one", "two", "three");
		assertThat(triple).isInstanceOf(Triple.class );
		assertThat(triple).isNotInstanceOf(Quadruple.class);
		Tuple quadruple = Tuple.of("one", "two", "three", "four");
		assertThat(quadruple).isInstanceOf(Quadruple.class);
		Tuple quintuple = Tuple.of(1, 2, 3, 4, 5);
		assertThat(quintuple).isInstanceOf(Quintuple.class);
		Tuple sextuple = Tuple.of(1, 2, 3, 4, 5, 6);
		assertThat(sextuple).isInstanceOf(Sextuple.class);
	}


	/**
	 * Test that equals() works.
	 * 
	 * By using the Tuple type with the most elements all the equals methods are
	 * exercised because for example <code>Quadruple.equals()</code> is defined
	 * as <code>Triple.equals()</code> plus a little extra.
	 */
	@Test
	public void testEquals() {
		Tuple t1 = of(1, 2, 3, 4, 5, 6);
		Tuple t2 = of(1, 2, 3, 4, 5, 6);
		assertThat(t1).as("equals() should identify these as being equal:").isEqualTo(t2);
		Tuple t3 = of(6,5, 4, 3, 2, 1);
		assertThat(t1).as("equals() should identify these as NOT being equal:").isNotEqualTo(t3);
		Tuple triple = of(1, 2, 3);
		assertThat(t1).as("equals() should identify these as NOT being equal:").isNotEqualTo(triple);
		assertThat(triple).as("equals() should identify these as NOT being equal:").isNotEqualTo(t1);
	}


	/**
	 * Test that toString() works.
	 * 
	 * By using the Tuple type with the most elements all the
	 * <code>toString</code> methods are exercised because for example
	 * <code>Quadruple.toString()</code> is defined as
	 * <code>Triple.toString()</code> plus a little extra.
	 */
	@Test
	public void testToString() {
		Tuple t1 = of("one", "two", "three", 4, 5, 6);
		assertThat(t1.toString()).isEqualTo("[[one][two][three][4][5][6]]");
	}


	/**
	 * Test that compareTo() works.
	 * 
	 * By using the Tuple type with the most elements all the
	 * <code>compareTo</code> methods are exercised because for example
	 * <code>Quadruple.compareTo()</code> is defined as
	 * <code>Triple.compareTo()</code> plus a little extra.
	 */
	@Test
	public void testComapareTo() {
		Tuple t1 = of(1, 2, 3, 4, 5, 1);
		Tuple t2 = of(1, 2, 3, 4, 5, 2);
		Tuple t2b = of(1, 2, 3, 4, 5, 2);
		assertThat(t1.compareTo(t2)).isEqualTo(-1);
		assertThat(t2.compareTo(t1)).isEqualTo(1);
		assertThat(t2.compareTo(t2b)).isEqualTo(0);
		assertThat(t2b.compareTo(t2)).isEqualTo(0);
		// Test comparing tuples of different size
		// When common elements are equal the longer tuple is greatest
		Tuple shorter = of(1, 2, 3, 4, 5);
		assertThat(t1.compareTo(shorter)).isEqualTo(1);
		assertThat(shorter.compareTo(t1)).isEqualTo(-1);

		Tuple shorterButGreater = of(1, 3, 2);
		assertThat(t1.compareTo(shorterButGreater)).isEqualTo(-1);
		assertThat(shorterButGreater.compareTo(t1)).isEqualTo(1);
	}


	/**
	 * Test compareTo() on not-comparable items.
	 */
	@Test
	public void testComapareTo_incomparable() {
		Tuple t1 = Tuple.of(new Object());
		Tuple t2 = Tuple.of(new Object());
		assertThrows(ClassCastException.class, () -> t1.compareTo(t2));
	}


	/**
	 * Test compareTo() on incompatible items.
	 */
	@Test
	public void testComapareTo_incompatible() {
		Tuple t1 = Tuple.of(1);
		Tuple t2 = Tuple.of("one");
		assertThrows(ClassCastException.class, () -> t1.compareTo(t2));
	}


}
