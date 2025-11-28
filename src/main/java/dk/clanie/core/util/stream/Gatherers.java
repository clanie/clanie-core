/*
 * Copyright (C) 2025, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.core.util.stream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Gatherers {


	private Gatherers() {
		// This class is not intended to be instantiated
	}


	/**
	 * Returns a gatherer that merge the input streams, ordering outputs using the specified comparator.
	 * <p>
	 * Input streams must be sorted according to the comparator.
	 * <p>
	 * The gatherer is sequential and greedy.
	 *
	 * @param <T> the type of the elements
	 * @param comparator the comparator to use for sorting
	 * @return a gatherer that merge-sorts the input streams
	 */
	public static <T> Gatherer<Stream<T>, ?, T> mergeSorted(Comparator<? super T> comparator) {

		class MergeState {

			record HeapEntry<T>(T value, Iterator<T> iterator) {}

			private final PriorityQueue<HeapEntry<T>> heap = new PriorityQueue<>(Comparator.comparing(HeapEntry::value, comparator));

			/**
			 * Pushes 1st element from each stream onto the heap anong with an iterator for that stream.
			 */
			boolean integrate(Stream<T> stream, Gatherer.Downstream<? super T> downstream) {
				Iterator<T> iterator = stream.iterator();
				if (iterator.hasNext()) {
					heap.add(new HeapEntry<>(iterator.next(), iterator));
				}
				return true;
			}

			/**
			 * Repeatedly takes smallest element from the heap and pushes it downstream, and then
			 * adds the next element from the same stream to the heap.
			 */
			void finish(Gatherer.Downstream<? super T> downstream) {
				while (!heap.isEmpty() && !downstream.isRejecting()) {
					HeapEntry<T> entry = heap.poll();
					T value = entry.value;
					Iterator<T> iterator = entry.iterator;
					if (iterator.hasNext()) {
						heap.add(new HeapEntry<>(iterator.next(), iterator));
					}
					if (!downstream.push(value)) break;
				}
			}

		}

		return Gatherer.<Stream<T>, MergeState, T>ofSequential(
				MergeState::new, // Initializer
				Integrator.<MergeState, Stream<T>, T>ofGreedy(MergeState::integrate), // Integrator
				MergeState::finish); // Finisher

	}


	/**
	 * Returns a gatherer that groups the input elements according to the specified comparator.
	 * <p>
	 * Input streams must be sorted according to the comparator.
	 * <p>
	 * The gatherer is sequential and greedy.
	 * 
	 * @param <T> the type of the elements
	 * @param comparator
	 * @return a gatherer that groups the input elements according to the specified comparator
	 */
	public static <T> Gatherer<T, ?, List<T>> grouping(Comparator<? super T> comparator) {

		class Grouping {

			private List<T> group = new ArrayList<>();
			private boolean accepting = true;

			boolean integrate(T value, Gatherer.Downstream<? super List<T>> downstream) {
				if (group.isEmpty() || comparator.compare(group.get(0), value) == 0) {
					group.add(value);
				} else {
					accepting = downstream.push(group);
					group = new ArrayList<>();
					group.add(value);
				}
				return accepting;
			}

			void finish(Gatherer.Downstream<? super List<T>> downstream) {
				if (accepting && !group.isEmpty()) downstream.push(group);
			}

		}

		return Gatherer.<T, Grouping, List<T>>ofSequential(
				Grouping::new, // Initializer
				Integrator.<Grouping, T, List<T>>ofGreedy(Grouping::integrate), // Integrator
				Grouping::finish); // Finisher

	}


}
