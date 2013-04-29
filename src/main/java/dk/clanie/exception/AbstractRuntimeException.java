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
package dk.clanie.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Common functionality for all RuntimeExceptions.
 * 
 * @author Claus Nielsen
 */
@SuppressWarnings("serial")
public abstract class AbstractRuntimeException extends RuntimeException {

	public AbstractRuntimeException() {
		super();
	}

	public AbstractRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbstractRuntimeException(String message) {
		super(message);
	}

	public AbstractRuntimeException(Throwable cause) {
		super(cause);
	}

	public AbstractRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
