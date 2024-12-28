/**
 * Copyright (C) 2007-2024, Claus Nielsen, clausn999@gmail.com
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Wrapped Exception.
 * <p>
 * An exception which wraps another exception. It dosn't include the wrapped
 * exception, but just information about it.
 * <p>
 * The idea is to wrap unexpected runtime exceptions, before passing them on to
 * the next tier, which may not have the exception class available.
 * 
 * @author Claus Nielsen
 */
@SuppressWarnings("serial")
public class WrappedException extends AbstractRuntimeException {

    private String wrappedMessage;
    private String wrappedExceptionClassName;
    private String wrappedStackTrace;

    /** Constructor. */
    public WrappedException(String message, Throwable cause) {
        super(message);
        init(cause);
    }

    /** Constructor. */
    public WrappedException(Throwable cause) {
        super(cause.getClass().getName() + ": " + cause.getMessage());
        init(cause);
    }

    private void init(Throwable cause) {
        wrappedExceptionClassName = cause.getClass().getName();
        wrappedMessage = cause.getMessage();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cause.printStackTrace(new PrintStream(bos));
        wrappedStackTrace = bos.toString(); 
    }

    public String getWrappedMessage() {
        return wrappedMessage;
    }

    public String getWrappedExceptionClassName() {
        return wrappedExceptionClassName;
    }

    public String getWrappedStacktrace() {
        return wrappedStackTrace;
    }
}
