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

/**
 * Strategy interface for propagating context to virtual threads.
 * <p>
 * Implementations handle capturing, setting, and clearing various types of context
 * (e.g., security context, MDC, transaction context) in a way appropriate for each framework.
 */
public interface ContextPropagator {

	/**
	 * Captures the current context from the calling thread.
	 * 
	 * @return the captured context, or null if none exists
	 */
	Object capture();


	/**
	 * Sets the context in the current thread.
	 * 
	 * @param context the context to set (as returned by {@link #capture()})
	 */
	void set(Object context);


	/**
	 * Clears the context from the current thread.
	 */
	void clear();

}
