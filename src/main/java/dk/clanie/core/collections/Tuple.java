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

/**
 * Tuple.
 * 
 * Provides static factory methods to instantiate Tuples of different sizes.
 * <p>
 * Tuples can be used for ordering - for example as keys in collections. When
 * using Tuples for such purposes instantiate them like this:
 * </p><p>
 * <code>Tuple myTuple = newTuple("one", 2, "three");</code>
 * </p><p>
 * This way you will <b>not</b> have access to individual elements of the Tuple.
 * If you need that you will have to use a more specific subclass like this:
 * </p><p>
 * <code>Triple<String, Integer, String> myTuple = newTuple("one", 2, "three");</code>
 * </p>
 * @author Claus Nielsen
 */
public abstract class Tuple implements Comparable<Tuple> {


	/** Tuple with one element. */
	public static class Singleton<T> extends Tuple {
		private T element;

		public Singleton(T element) {
			this.element = element;
		}

		public T get1st() {
			return element;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(getClass() == o.getClass())) return false;
			return elementsEquals(element, ((Singleton) o).element);
		}

		@Override
		public int hashCode() {
			return element == null ? 0 : element.hashCode();
		}

		@Override
		protected StringBuilder appendItems(StringBuilder builder) {
			return builder.append("[").append(element).append("]");
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected int compareItems(Tuple other) {
			if (!(other instanceof Singleton)) return 1; // Other is shorter
			return ((Comparable) element).compareTo(((Singleton) other).element);
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

		@SuppressWarnings("rawtypes")
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
			return super.appendItems(builder).append("[").append(e2).append("]");
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected int compareItems(Tuple other) {
			int result = super.compareItems(other);
			if (result != 0) return result;
			if (!(other instanceof Pair)) return 1; // Other is shorter
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

		@SuppressWarnings("rawtypes")
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
			return super.appendItems(builder).append("[").append(e3).append("]");
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected int compareItems(Tuple other) {
			int result = super.compareItems(other);
			if (result != 0) return result;
			if (!(other instanceof Triple)) return 1; // Other is shorter
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

		@SuppressWarnings("rawtypes")
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
			return super.appendItems(builder).append("[").append(e4).append("]");
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected int compareItems(Tuple other) {
			int result = super.compareItems(other);
			if (result != 0) return result;
			if (!(other instanceof Quadruple)) return 1; // Other is shorter
			return ((Comparable) e4).compareTo(((Quadruple) other).e4);
		}

	}


	/** Tuple with five elements. */
	public static class Quintuple<T1, T2, T3, T4, T5> extends Quadruple<T1, T2, T3, T4> {
		private T5 e5;

		public Quintuple(T1 e1, T2 e2, T3 e3, T4 e4, T5 e5) {
			super(e1, e2, e3, e4);
			this.e5 = e5;
		}

		public T5 get5th() {
			return e5;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			return super.equals(o) && elementsEquals(e5, ((Quintuple) o).e5);
		}

		@Override
		public int hashCode() {
			return super.hashCode() * 37 + (e5 == null ? 0 : e5.hashCode());
		}

		@Override
		protected StringBuilder appendItems(StringBuilder builder) {
			return super.appendItems(builder).append("[").append(e5).append("]");
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected int compareItems(Tuple other) {
			int result = super.compareItems(other);
			if (result != 0) return result;
			if (!(other instanceof Quintuple)) return 1; // Other is shorter
			return ((Comparable) e5).compareTo(((Quintuple) other).e5);
		}

	}


	/** Tuple with six elements. */
	public static class Sextuple<T1, T2, T3, T4, T5, T6> extends Quintuple<T1, T2, T3, T4, T5> {
		private T6 e6;

		public Sextuple(T1 e1, T2 e2, T3 e3, T4 e4, T5 e5, T6 e6) {
			super(e1, e2, e3, e4, e5);
			this.e6 = e6;
		}

		public T6 get6th() {
			return e6;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			return super.equals(o) && elementsEquals(e6, ((Sextuple) o).e6);
		}

		@Override
		public int hashCode() {
			return super.hashCode() * 37 + (e6 == null ? 0 : e6.hashCode());
		}

		@Override
		protected StringBuilder appendItems(StringBuilder builder) {
			return super.appendItems(builder).append("[").append(e6).append("]");
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected int compareItems(Tuple other) {
			int result = super.compareItems(other);
			if (result != 0) return result;
			if (!(other instanceof Sextuple)) return 1; // Other is shorter
			return ((Comparable) e6).compareTo(((Sextuple) other).e6);
		}

	}


	/**
	 * Returns a String representation of the Tuple.
	 *
	 * The whole Tuple is enclosed in square brackets, and so is
	 * each element in it.
	 *
	 * Tuple.newTuple("one", 2).toString() will return "[[one][2]]".
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return appendItems(new StringBuilder("[")).append("]").toString();
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
	protected abstract StringBuilder appendItems(StringBuilder builder);


	/**
	 * Compares this Tuple with another Tuple for order.
	 * 
	 * Tuples are compared by comparing each of their elements.
	 * 
	 * Tuples with more elements are considered greater than Tuples with 
	 * fewer, but otherwise equal, elements.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * @return A negative integer, zero or a positive integer as this Tuple is
	 *         less than, equal to or greater than the specified Tuple.
	 * 
	 * @throws ClassCastException
	 *             if the items in this and the other Tuple aren't compatible,
	 *             if they don't implement {@link Comparable}.
	 */
	@Override
	public int compareTo(Tuple other) {
		int temp = compareItems(other);
		if (temp != 0) return temp;
		// All compared elements were equal.
		// If 'other' is of another class than 'this', it must have
		// more elements, and therefore it is considered greatest;
		// otherwise the tuples are equal.
		return getClass() == other.getClass() ? 0 : -1;
	}


	/**
	 * compareTo() helper.
	 * <p>
	 * Subclasses should override this to call <code>super.compareItems</code>
	 * and, if the result of that is zero, then compare "their own" item with
	 * the corresponding item in the supplied Tuple.
	 * </p>
	 */
	protected abstract int compareItems(Tuple other);


	/**
	 * Helper-method to check if two elements are equal.
	 * 
	 * @param e1
	 * @param e2
	 * @return true if both e1 and e2 are null or if e1.equals(e2).
	 */
	protected static boolean elementsEquals(Object e1, Object e2) {
		return (e1 == null && e2 == null) || e1.equals(e2);
	}


	/**
	 * Create new Singleton (Tuple with one element).
	 * 
	 * @param <T>
	 * @param element
	 * @return Singleton
	 */
	public static <T> Singleton<T> of(T element) {
		return new Singleton<T>(element);
	}


	/**
	 * Create new Pair (Tuple with two elements).
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param e1
	 * @param e2
	 * @return Pair
	 */
	public static <T1, T2> Pair<T1, T2> of(T1 e1, T2 e2) {
		return new Pair<T1, T2>(e1, e2);
	}


	/**
	 * Create new Triple (Tuple with three elements).
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param e1
	 * @param e2
	 * @param e3
	 * @return Triple
	 */
	public static <T1, T2, T3> Triple<T1, T2, T3> of(T1 e1, T2 e2, T3 e3) {
		return new Triple<T1, T2, T3>(e1, e2, e3);
	}


	/**
	 * Create new Quadruple (Tuple with four elements).
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param e1
	 * @param e2
	 * @param e3
	 * @param e4
	 * @return Quadruple
	 */
	public static <T1, T2, T3, T4> Quadruple<T1, T2, T3, T4> of(
			T1 e1, T2 e2, T3 e3, T4 e4) {
		return new Quadruple<T1, T2, T3, T4>(e1, e2, e3, e4);
	}


	/**
	 * Create new Quintuple (Tuple with five elements).
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param e1
	 * @param e2
	 * @param e3
	 * @param e4
	 * @param e5
	 * @return Quintuple
	 */
	public static <T1, T2, T3, T4, T5> Quintuple<T1, T2, T3, T4, T5> of(
			T1 e1, T2 e2, T3 e3, T4 e4, T5 e5) {
		return new Quintuple<T1, T2, T3, T4, T5>(e1, e2, e3, e4, e5);
	}


	/**
	 * Create new Sextuple (Tuple with six elements).
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param e1
	 * @param e2
	 * @param e3
	 * @param e4
	 * @param e5
	 * @param e6
	 * @return Sextuple
	 */
	public static <T1, T2, T3, T4, T5, T6> Sextuple<T1, T2, T3, T4, T5, T6> of(
			T1 e1, T2 e2, T3 e3, T4 e4, T5 e5, T6 e6) {
		return new Sextuple<T1, T2, T3, T4, T5, T6>(e1, e2, e3, e4, e5, e6);
	}


}
