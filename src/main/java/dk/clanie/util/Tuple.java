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

/**
 * Tuple.
 * 
 * Provides static factory methods to instantiate Tuples of different sizes.
 * 
 * Tuples can be used for ordering - for example as keys in collections. When
 * using Tuples for such purposes instantiate them like this:
 * 
 * <code>Tuple myTuple = newTuple("one", "two", "three");</code>
 * 
 * This way you will <b>not</b> have access to individual elements of the Tuple.
 * If you need that you will have to use a more specific subclass like this:
 * 
 * <code>Triple<String, Integer, String> myTuple = newTuple("one", 2, "three");</code>
 * 
 * @author Claus Nielsen
 */
public class Tuple implements Comparable<Tuple> {

	/** Tuple with one element. */
	public static class Singleton<T> extends Tuple {
		private T element;

		public Singleton(T element) {
			this.element = element;
		}

		public T get1st() {
			return element;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (!(getClass() == o.getClass()))
				return false;
			return elementsEquals(element, ((Singleton) o).element);
		}

		@Override
		public int hashCode() {
			return element == null ? 0 : element.hashCode();
		}

		@Override
		protected StringBuilder appendItems(StringBuilder builder) {
			return super.appendItems(builder).append("[").append(element)
					.append("]");
		}

		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(Tuple other) {
			int result = super.compareTo(other);
			if (result != 0)
				return result;
			return ((Comparable) element)
					.compareTo(((Singleton) other).element);
		}
	}

	/** Tuple with two elements. */
	public static class Pair<T1, T2> extends Singleton<T1> {
		private T2 e2;

		public Pair(T1 e1, T2 e2) {
			super(e1);
			this.e2 = e2;
		}

		public T2 get2nd() {
			return e2;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			return super.equals(o) && elementsEquals(e2, ((Pair) o).e2);
		}

		@Override
		public int hashCode() {
			return super.hashCode() * 37 + (e2 == null ? 0 : e2.hashCode());
		}

		@Override
		protected StringBuilder appendItems(StringBuilder builder) {
			return super.appendItems(builder).append("[").append(e2)
					.append("]");
		}

		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(Tuple other) {
			int result = super.compareTo(other);
			if (result != 0)
				return result;
			return ((Comparable) e2).compareTo(((Pair) other).e2);
		}
	}

	/** Tuple with three elements. */
	public static class Triple<T1, T2, T3> extends Pair<T1, T2> {
		private T3 e3;

		public Triple(T1 e1, T2 e2, T3 e3) {
			super(e1, e2);
			this.e3 = e3;
		}

		public T3 get3rd() {
			return e3;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			return super.equals(o) && elementsEquals(e3, ((Triple) o).e3);
		}

		@Override
		public int hashCode() {
			return super.hashCode() * 37 + (e3 == null ? 0 : e3.hashCode());
		}

		@Override
		protected StringBuilder appendItems(StringBuilder builder) {
			return super.appendItems(builder).append("[").append(e3)
					.append("]");
		}

		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(Tuple other) {
			int result = super.compareTo(other);
			if (result != 0)
				return result;
			return ((Comparable) e3).compareTo(((Triple) other).e3);
		}
	}

	/** Tuple with four elements. */
	public static class Quadruple<T1, T2, T3, T4> extends Triple<T1, T2, T3> {
		private T4 e4;

		public Quadruple(T1 e1, T2 e2, T3 e3, T4 e4) {
			super(e1, e2, e3);
			this.e4 = e4;
		}

		public T4 get4th() {
			return e4;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			return super.equals(o) && elementsEquals(e4, ((Quadruple) o).e4);
		}

		@Override
		public int hashCode() {
			return super.hashCode() * 37 + (e4 == null ? 0 : e4.hashCode());
		}

		@Override
		protected StringBuilder appendItems(StringBuilder builder) {
			return super.appendItems(builder).append("[").append(e4)
					.append("]");
		}

		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(Tuple other) {
			int result = super.compareTo(other);
			if (result != 0)
				return result;
			return ((Comparable) e4).compareTo(((Quadruple) other).e4);
		}
	}

	/**
	 * toString helper.
	 * 
	 * Each subclass should override this to call <code>super.appendItems</code>,
	 * and the append "their own" item in square brackets.
	 * 
	 * @param builder
	 * @return StringBuilder
	 */
	protected StringBuilder appendItems(StringBuilder builder) {
		return builder;
	}

	@Override
	public String toString() {
		return appendItems(new StringBuilder("[")).append("]").toString();
	}

	/**
	 * Compares this Tuple with another Tuple for order.
	 * 
	 * This is not as type-safe as I would like, because the method accepts any
	 * Tuple but it will only work for Tuples with the same number and types of
	 * elements. Anything else will cause a ClassCastException.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * @return A negative integer, zero or a positive integer as this Tuple is
	 *         less than, equal to or greater than the specified Tuple.
	 * 
	 * @throws ClassCastException
	 *             if the items in this and the other Tuple aren't compatible,
	 *             if they don't implement {@link Comparable}, or if the tuples
	 *             are of different size.
	 */
	@Override
	public int compareTo(Tuple o) {
		if (getClass() != o.getClass())
			throw new java.lang.ClassCastException(
					"Cannot compare Tuples of different length.");
		return 0;
	}

	/**
	 * Helper-method to check if two elements are equal.
	 * 
	 * @param e1
	 * @param e2
	 * @return true if both e1 and e2 are null or if e1.equals(e2).
	 */
	protected static boolean elementsEquals(Object e1, Object e2) {
		if (e1 == null)
			return e2 == null;
		return e1.equals(e2);
	}

	public static <T> Singleton<T> newTuple(T element) {
		return new Singleton<T>(element);
	}

	public static <T1, T2> Pair<T1, T2> newTuple(T1 e1, T2 e2) {
		return new Pair<T1, T2>(e1, e2);
	}

	public static <T1, T2, T3> Triple<T1, T2, T3> newTuple(T1 e1, T2 e2, T3 e3) {
		return new Triple<T1, T2, T3>(e1, e2, e3);
	}

	public static <T1, T2, T3, T4> Quadruple<T1, T2, T3, T4> newTuple(T1 e1,
			T2 e2, T3 e3, T4 e4) {
		return new Quadruple<T1, T2, T3, T4>(e1, e2, e3, e4);
	}

}
