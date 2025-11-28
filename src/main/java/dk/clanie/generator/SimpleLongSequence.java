/*
 * Copyright (C) 2007, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * SimpleLongSequence.
 * 
 * Simple implementation of the {@link LongSequence} interface.
 * This implementation is threadsafe.
 * 
 * @author Claus Nielsen
 */
public class SimpleLongSequence implements LongSequence {

	private final AtomicLong nextNumber;

	/**
	 * Constructor.
	 */
	public SimpleLongSequence() {
		this(1l);
	}

	/**
	 * Constructor.
	 * 
	 * @param next the next value to be returned
	 */
	public SimpleLongSequence(long next) {
		this.nextNumber = new AtomicLong(next);
	}

	public long next() {
		return nextNumber.getAndIncrement();
	}

}
