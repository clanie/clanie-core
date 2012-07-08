/**
 * Copyright (C) 2008, 2009 Claus Nielsen, cn@cn-consult.dk
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/** Collection Factory.
 * <p>
 * When you need to instantiate a class from the Java Collections Framework,
 * use these methods to avoid that stupid always-repeat-yourself syntax
 * normally required when instantiating generic classes.
 * </p><p>
 * Taking advantage of the fact that the type inference Java lacks for
 * constructor invocation <b>is</b> provided for generic method invocations,
 * these factory methods lets you write code such as<br/>
 * <code>Map&lt;String, Object&gt; map = newHashMap();</code> rather than<br/>
 * <code>Map&lt;String, Object&gt; map = new HashMap&lt;String, Object&gt;();<br/>
 * and even better<br/>
 * <code>Map&lt;String, Map&lt;String, List&lt;Object&gt;&gt;&gt; = newHashMap();</code>
 * rather than<br/>
 * Map&lt;String, Map&lt;String, List&lt;Object&gt;&gt;&gt; = new HashMap&lt;String, Map&lt;String, List&lt;Object&gt;&gt;&gt;(); 
 * </p>
 * 
 * @author Claus Nielsen
 */
public class CollectionFactory {

	public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int capacity) {
		return new ArrayBlockingQueue<E>(capacity);
	}

	public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int capacity, boolean fair) {
		return new ArrayBlockingQueue<E>(capacity, fair);
	}

	public static <E extends Delayed> DelayQueue<E> newDelayQueue() {
		return new DelayQueue<E>();
	}
	public static <E extends Delayed> DelayQueue<E> newDelayQueue(Collection<? extends E> collection) {
		return new DelayQueue<E>(collection);
	}

	public static <E> ArrayDeque<E> newArrayDeque() {
		return new ArrayDeque<E>();
	}

	public static <E> ArrayDeque<E> newArrayDeque(Collection<? extends E> collection) {
		return new ArrayDeque<E>(collection);
	}

	public static <E> ArrayDeque<E> newArrayDeque(int initialCapacity) {
		return new ArrayDeque<E>(initialCapacity);
	}

	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}

	public static <E> ArrayList<E> newArrayList(Collection<? extends E> collection) {
		return new ArrayList<E>(collection);
	}

	public static <E> ArrayList<E> newArrayList(int initialCapacity) {
		return new ArrayList<E>(initialCapacity);
	}

	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<K, V>();
	}

	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int initialCapacity) {
		return new ConcurrentHashMap<K, V>(initialCapacity);
	}

	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor) {
		return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor);
	}

	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
		return new ConcurrentHashMap<K, V>(map);
	}

	public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue() {
		return new ConcurrentLinkedQueue<E>();
	}

	public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue(Collection<? extends E> collection) {
		return new ConcurrentLinkedQueue<E>(collection);
	}

	public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap() {
		return new ConcurrentSkipListMap<K, V>();
	}

	public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(Comparator<? super K> comparator) {
		return new ConcurrentSkipListMap<K, V>(comparator);
	}

	public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(Map<? extends K, ? extends V> map) {
		return new ConcurrentSkipListMap<K, V>(map);
	}

	public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(SortedMap<K, ? extends V> sortedMap) {
		return new ConcurrentSkipListMap<K, V>(sortedMap);
	}

	public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet() {
		return new ConcurrentSkipListSet<E>();
	}

	public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(Collection<? extends E> collection) {
		return new ConcurrentSkipListSet<E>(collection);
	}

	public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(Comparator<? super E> comparator) {
		return new ConcurrentSkipListSet<E>(comparator);
	}

	public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(SortedSet<E> set) {
		return new ConcurrentSkipListSet<E>(set);
	}

	public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
		return new CopyOnWriteArrayList<E>();
	}

	public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Collection<? extends E> collection) {
		return new CopyOnWriteArrayList<E>(collection);
	}

	public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(E[] toCopyIn) {
		return new CopyOnWriteArrayList<E>(toCopyIn);
	}

	public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
		return new CopyOnWriteArraySet<E>();
	}

	public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Collection<? extends E> collection) {
		return new CopyOnWriteArraySet<E>(collection);
	}

	public static <E extends Enum<E>> EnumSet<E> newEnumSet(Class<E> elementType) {
		return EnumSet.noneOf(elementType);
	}

	public static <E extends Enum<E>> EnumSet<E> newEnumSet(Collection<E> collection) {
		return EnumSet.copyOf(collection);
	}

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public static <K, V> HashMap<K, V> newHashMap(int initialCapacity) {
		return new HashMap<K, V>(initialCapacity);
	}

	public static <K, V> HashMap<K, V> newHashMap(int initialCapacity, float loadFactor) {
		return new HashMap<K, V>(initialCapacity, loadFactor);
	}

	public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
		return new HashMap<K, V>(map);
	}

	public static <E> HashSet<E> newHashSet() {
		return new HashSet<E>();
	}

	public static <E> HashSet<E> newHashSet(Collection<? extends E> collection) {
		return new HashSet<E>(collection);
	}

	public static <E> HashSet<E> newHashSet(int initialCapacity) {
		return new HashSet<E>(initialCapacity);
	}

	public static <E> HashSet<E> newHashSet(int initialCapacity, float loadFactor) {
		return new HashSet<E>(initialCapacity, loadFactor);
	}

	public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque() {
		return new LinkedBlockingDeque<E>();
	}

	public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(Collection<? extends E> collection) {
		return new LinkedBlockingDeque<E>(collection);
	}

	public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(int capacity) {
		return new LinkedBlockingDeque<E>(capacity);
	}

	public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
		return new LinkedBlockingQueue<E>();
	}

	public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(Collection<? extends E> collection) {
		return new LinkedBlockingQueue<E>(collection);
	}

	public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(int capacity) {
		return new LinkedBlockingQueue<E>(capacity);
	}

	public static <E> LinkedList<E> newLinkedList() {
		return new LinkedList<E>();
	}

	public static <E> LinkedList<E> newLinkedList(Collection<? extends E> collection) {
		return new LinkedList<E>(collection);
	}

	public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue() {
		return new PriorityBlockingQueue<E>();
	}

	public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(Collection<? extends E> collection) {
		return new PriorityBlockingQueue<E>(collection);
	}

	public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(int initialCapacity) {
		return new PriorityBlockingQueue<E>(initialCapacity);
	}

	public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(int initialCapacity, Comparator<? super E> comparator) {
		return new PriorityBlockingQueue<E>(initialCapacity, comparator);
	}

	public static <E> PriorityQueue<E> newPriorityQueue() {
		return new PriorityQueue<E>();
	}

	public static <E> PriorityQueue<E> newPriorityQueue(Collection<? extends E> collection) {
		return new PriorityQueue<E>(collection);
	}

	public static <E> PriorityQueue<E> newPriorityQueue(int initialCapacity) {
		return new PriorityQueue<E>(initialCapacity);
	}

	public static <E> PriorityQueue<E> newPriorityQueue(int initialCapacity, Comparator<? super E> comparator) {
		return new PriorityQueue<E>(initialCapacity, comparator);
	}

	public static <E> PriorityQueue<E> newPriorityQueue(PriorityQueue<? extends E> priorityQueue) {
		return new PriorityQueue<E>(priorityQueue);
	}

	public static <E> PriorityQueue<E> newPriorityQueue(SortedSet<? extends E> sortedSet) {
		return new PriorityQueue<E>(sortedSet);
	}

	public static <E> SynchronousQueue<E> newSynchronousQueue() {
		return new SynchronousQueue<E>();
	}

	public static <E> SynchronousQueue<E> newSynchronousQueue(boolean fair) {
		return new SynchronousQueue<E>(fair);
	}

	public static <K, V> TreeMap<K, V> newTreeMap() {
		return new TreeMap<K, V>();
	}

	public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
		return new TreeMap<K, V>(comparator);
	}

	public static <K, V> TreeMap<K, V> newTreeMap(Map<? extends K, ? extends V> map) {
		return new TreeMap<K, V>(map);
	}

	public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
		return new TreeMap<K, V>(sortedMap);
	}

	public static <E> TreeSet<E> newTreeSet() {
		return new TreeSet<E>();
	}

	public static <E> TreeSet<E> newTreeSet(Collection<? extends E> collection) {
		return new TreeSet<E>(collection);
	}

	public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
		return new TreeSet<E>(comparator);
	}

	public static <E> TreeSet<E> newTreeSet(SortedSet<? extends E> sortedSet) {
		return new TreeSet<E>(sortedSet);
	}

}
