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

import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

import java.util.concurrent.Executor;

/**
 * An executor that propagates context (MDC, Spring Security Context, etc.) to virtual threads.
 * <p>
 * This executor wraps a virtual thread executor and ensures that various types of context
 * are propagated via the {@link ContextPropagator} strategy.
 * <p>
 * The executor uses virtual threads from {@link java.util.concurrent.Executors#newVirtualThreadPerTaskExecutor()},
 * making it ideal for I/O-bound operations like database queries and HTTP calls.
 */
public class ContextPropagatingExecutor implements Executor {

	private final Executor virtualThreadExecutor;
	private final ContextPropagator contextPropagator;


	/**
	 * Creates a new context propagating executor using virtual threads.
	 * 
	 * @param contextPropagator strategy for propagating context
	 */
	public ContextPropagatingExecutor(ContextPropagator contextPropagator) {
		this.virtualThreadExecutor = newVirtualThreadPerTaskExecutor();
		this.contextPropagator = contextPropagator;
	}


	@Override
	public void execute(Runnable command) {
		// Capture context from the current thread
		Object context = contextPropagator.capture();

		// Execute with context propagation
		virtualThreadExecutor.execute(() -> {
			try {
				// Set context in the virtual thread
				if (context != null) {
					contextPropagator.set(context);
				}

				// Execute the task
				command.run();
			} finally {
				// Clean up context after execution
				contextPropagator.clear();
			}
		});
	}

}