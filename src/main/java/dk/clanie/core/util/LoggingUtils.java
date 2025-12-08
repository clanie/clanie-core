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
package dk.clanie.core.util;

import java.util.function.Supplier;

import org.slf4j.Logger;

public class LoggingUtils {


	/**
	 * Logs a trace message with parameters supplied by the given supplier.
	 * 
	 * The supplier is only invoked if trace logging is enabled.
	 *
	 * @param logger         the logger to use
	 * @param format         the message format
	 * @param paramsSupplier the supplier of parameters
	 */
	public static void trace(Logger logger, String format, Supplier<Object[]> paramsSupplier) {
		if (logger.isTraceEnabled()) {
			logger.trace(format, paramsSupplier.get());
		}
	}



	/**
	 * Logs a debug message with parameters supplied by the given supplier.
	 * 
	 * The supplier is only invoked if debug logging is enabled.
	 * 
	 * @param logger         the logger to use
	 * @param format         the message format
	 * @param paramsSupplier the supplier of parameters
	 */
	public static void debug(Logger logger, String format, Supplier<Object[]> paramsSupplier) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, paramsSupplier.get());
		}
	}


	/**
	 * Logs an info message with parameters supplied by the given supplier.
	 * 
	 * The supplier is only invoked if info logging is enabled.
	 * 
	 * @param logger         the logger to use
	 * @param format         the message format
	 * @param paramsSupplier the supplier of parameters
	 */
	public static void info(Logger logger, String format, Supplier<Object[]> paramsSupplier) {
		if (logger.isInfoEnabled()) {
			logger.info(format, paramsSupplier.get());
		}
	}


}