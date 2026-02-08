/*
 * Copyright (C) 2026, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.core.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite implementation of {@link ContextPropagator} that delegates to multiple propagators.
 * <p>
 * This allows propagating multiple types of context (MDC, Security, etc.) in a single operation.
 * Propagators are executed in the order they are provided.
 */
public class CompositeContextPropagator implements ContextPropagator {

	private final List<ContextPropagator> propagators;


	/**
	 * Creates a composite propagator with the given list of propagators.
	 * 
	 * @param propagators the list of propagators to delegate to
	 */
	public CompositeContextPropagator(List<ContextPropagator> propagators) {
		this.propagators = new ArrayList<>(propagators);
	}


	@Override
	public Object capture() {
		// Capture context from all propagators
		List<Object> contexts = new ArrayList<>(propagators.size());
		for (ContextPropagator propagator : propagators) {
			contexts.add(propagator.capture());
		}
		return contexts;
	}


	@Override
	@SuppressWarnings("unchecked")
	public void set(Object context) {
		if (context instanceof List) {
			List<Object> contexts = (List<Object>) context;
			for (int i = 0; i < propagators.size() && i < contexts.size(); i++) {
				propagators.get(i).set(contexts.get(i));
			}
		}
	}


	@Override
	public void clear() {
		for (ContextPropagator propagator : propagators) {
			propagator.clear();
		}
	}

}
