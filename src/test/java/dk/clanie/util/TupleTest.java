/**
 * Copyright (C) 2007, Claus Nielsen, cn@cn-consult.dk
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
package dk.clanie.util;

import static dk.clanie.util.Tuple.newTuple;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dk.clanie.util.Tuple.Pair;
import dk.clanie.util.Tuple.Quadruple;
import dk.clanie.util.Tuple.Quintuple;
import dk.clanie.util.Tuple.Sextuple;
import dk.clanie.util.Tuple.Singleton;
import dk.clanie.util.Tuple.Triple;

/**
 * Test Tuple.
 * 
 * @author Claus Nielsen
 */
public class TupleTest {

	@Test
	public void testInstantiation() {
		Tuple single = newTuple("one");
		assertThat(single, instanceOf(Singleton.class));
		assertThat(single, not(instanceOf(Pair.class)));
		Tuple pair = newTuple("one", "two");
		assertThat(pair, instanceOf(Pair.class));
		assertThat(pair, not(instanceOf(Triple.class)));
		Tuple triple = newTuple("one", "two", "three");
		assertThat(triple, instanceOf(Triple.class ));
		assertThat(triple, not(instanceOf(Quadruple.class )));
		Tuple quadruple = newTuple("one", "two", "three", "four");
		assertThat(quadruple, is(instanceOf(Quadruple.class )));
		Tuple quintuple = newTuple(1, 2, 3, 4, 5);
		assertThat(quintuple, is(instanceOf(Quintuple.class )));
		Tuple sextuple = newTuple(1, 2, 3, 4, 5, 6);
		assertThat(sextuple, is(instanceOf(Sextuple.class )));
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
		Tuple t1 = newTuple(1, 2, 3, 4, 5, 6);
		Tuple t2 = newTuple(1, 2, 3, 4, 5, 6);
		assertThat("equals() should identify these as being equal:", t1, equalTo(t2));
		Tuple t3 = newTuple(6,5, 4, 3, 2, 1);
		assertThat("equals() should identify these as NOT being equal:", t1, not(equalTo(t3)));
		Tuple triple = newTuple(1, 2, 3);
		assertThat("equals() should identify these as NOT being equal:", t1, not(equalTo(triple)));
		assertThat("equals() should identify these as NOT being equal:", triple, not(equalTo(t1)));
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
		Tuple t1 = newTuple("one", "two", "three", 4, 5, 6);
		assertThat(t1.toString(), equalTo("[[one][two][three][4][5][6]]"));
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
		Tuple t1 = newTuple(1, 2, 3, 4, 5, 1);
		Tuple t2 = newTuple(1, 2, 3, 4, 5, 2);
		Tuple t2b = newTuple(1, 2, 3, 4, 5, 2);
		assertThat(t1.compareTo(t2), is(-1));
		assertThat(t2.compareTo(t1), is(1));
		assertThat(t2.compareTo(t2b), is(0));
		assertThat(t2b.compareTo(t2), is(0));
		// Test comparing tuples of different size
		// When common elements are equal the longer tuple is greatest
		Tuple shorter = newTuple(1, 2, 3, 4, 5);
		assertThat(t1.compareTo(shorter), is(1));
		assertThat(shorter.compareTo(t1), is(-1));

		Tuple shorterButGreater = newTuple(1, 3, 2);
		assertThat(t1.compareTo(shorterButGreater), is(-1));
		assertThat(shorterButGreater.compareTo(t1), is(1));
	}

	/**
	 * Test compareTo() on not-comparable items.
	 */
	@Test(expected=ClassCastException.class)
	public void testComapareTo_incomparable() {
		Tuple t1 = newTuple(new Object());
		Tuple t2 = newTuple(new Object());
		t1.compareTo(t2);
	}

	/**
	 * Test compareTo() on incompatible items.
	 */
	@Test(expected=ClassCastException.class)
	public void testComapareTo_incompatible() {
		Tuple t1 = newTuple(1);
		Tuple t2 = newTuple("one");
		t1.compareTo(t2);
	}

}
