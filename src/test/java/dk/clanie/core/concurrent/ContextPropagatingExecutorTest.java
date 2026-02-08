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
package dk.clanie.core.concurrent;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

class ContextPropagatingExecutorTest {

	@Test
	void testMdcPropagation() throws Exception {
		// Given
		Executor executor = new ContextPropagatingExecutor(new MdcContextPropagator());
		MDC.put("testKey", "testValue");

		// When
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			return MDC.get("testKey");
		}, executor);

		// Then
		String result = future.get();
		assertThat(result).isEqualTo("testValue");

		// Cleanup
		MDC.clear();
	}


	@Test
	void testExecutorWithoutContext() throws Exception {
		// Given
		Executor executor = new ContextPropagatingExecutor(new MdcContextPropagator());
		MDC.clear();

		// When
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			// Should not throw exception even without context
		}, executor);

		// Then
		future.get(); // Should complete without exception
	}


}
