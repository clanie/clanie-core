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
package dk.clanie.aop.interceptor;

import java.util.LinkedList;
import java.util.concurrent.CancellationException;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import dk.clanie.exception.WrappedException;

public class ExceptionWrapperInterceptorTest extends
		AbstractDependencyInjectionSpringContextTests {

	/* Injected properties. */
	protected ExceptionWrapperInterceptor advice;
	protected ExceptionWrapperInterceptorTestTargetInterface target;

	/**
	 * Constructor.
	 */
	public ExceptionWrapperInterceptorTest() {
		super();
		setPopulateProtectedVariables(true);
	}

	@Override
	protected String[] getConfigLocations() {
		String contextLocation = getClass().getName().replace('.', '/')
				+ "Context.xml";
		return new String[] { contextLocation };
	}

	@Override
	protected void prepareTestInstance() throws Exception {
		super.prepareTestInstance();
		LinkedList<Class<? extends Throwable>> accepted = new LinkedList<Class<? extends Throwable>>();
		accepted.add(IllegalStateException.class);
		advice.setAccepted(accepted);
	}

	public void testPassedThroughException() {
		try {
			target.expectedError();
			fail("Expected IllegalStateException");
		} catch (IllegalStateException ise) {
			// OK
		}
	}

	public void testPassedThroughExceptionSubclass() {
		try {
			target.expectedErrorSubclass();
			fail("Expected CancellationException");
		} catch (CancellationException ce) {
			// OK
		}
	}

	public void testWrappedException() {
		try {
			target.unexpectedError();
			fail("Expected WrappedException");
		} catch (WrappedException we) {
			// OK
		}
	}

}
