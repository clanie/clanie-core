/**
 * Copyright (C) 2010-2024, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.properties;

import java.time.Instant;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Temporal Property.
 * 
 * Keeps track of the different values assigned to the property over time.
 * An implementation of the "Temporal Property" pattern described by
 * Martin Fowler (and others).
 * 
 * @author Claus Nielsen
 *
 * @param <T> the type of the property.
 */
public class TemporalProperty<T> implements ObservableProperty<T> {

	NavigableMap<Instant, T> values = new ConcurrentSkipListMap<>();
	Set<PropertyChangeListener<T>> listeners = Collections.newSetFromMap(new ConcurrentHashMap<PropertyChangeListener<T>, Boolean>());


	/* (non-Javadoc)
	 * @see dk.clanie.properties.Property#set(java.lang.Object)
	 */
	@Override
	public void set(T value) {
		set(Instant.now(), value);
	}


	/**
	 * Sets the value of the property at the specified time.
	 * 
	 * For internal and test use only.<p/>
	 * Values are assumed to be set in chronological order.
	 * 
	 * @param effectiveFrom
	 * @param value
	 */
	void set(Instant effectiveFrom, T value) {
		T oldValue = get();
		values.put(effectiveFrom, value);
		notifyListeners(oldValue, value);
	}


	/**
	 * Gets the value effective at the specified instant in time.
	 *
	 * Returns null if the property had no value at the specified time.
	 *
	 * @param effectivAt
	 * @return the value of the property at the specified time.
	 */
	public T get(Instant effectivAt) {
		Entry<Instant, T> entry = values.floorEntry(effectivAt);
		if (entry == null) return null;
		return entry.getValue();
	}


	/* (non-Javadoc)
	 * @see dk.clanie.properties.Property#get()
	 */
	@Override
	public T get() {
		return get(Instant.now());
	}


	/**
	 * Removes values no longer in effect at the specified point in time.
	 * 
	 * @param limit
	 */
	public void purge(Instant limit) {
		Instant firstKeeper = values.floorKey(limit);
		if (firstKeeper == null) return;
		Set<Instant> purgables = values.headMap(firstKeeper).keySet();
		if (purgables == null) return;
		for (Instant purgable : purgables) {
			values.remove(purgable);
		}
	}


	private void notifyListeners(T oldValue, T newValue) {
		for (PropertyChangeListener<T> pcl : listeners) {
			pcl.propertyChanged(oldValue, newValue);
		}
	}


	@Override
	public void addChangeListener(PropertyChangeListener<T> listener) {
		listeners.add(listener);
	}


	@Override
	public void removeChangeListener(PropertyChangeListener<T> listener) {
		listeners.remove(listener);		
	}


}
