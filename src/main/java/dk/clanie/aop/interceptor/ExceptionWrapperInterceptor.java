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

import java.util.Collection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import dk.clanie.exception.WrappedException;

/**
 * Exception Wrapper Interceptor.
 * <p>
 * A filter which wraps unexpected exceptions to ensure that only specific
 * exceptions are passed on.
 * 
 * @author Claus Nielsen
 */
public class ExceptionWrapperInterceptor implements MethodInterceptor {

    Collection<Class<? extends Throwable>> accepted;

    /**
     * Inject Accepted Exceptions.
     * 
     * @param accepted
     */
    public void setAccepted(Collection<Class<? extends Throwable>> accepted) {
        this.accepted = accepted;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Object returnValue = invocation.proceed();
            return returnValue;
        } catch (Throwable ex) {
            for (Class<? extends Throwable> acceptedClass : accepted) {
                if (acceptedClass.isInstance(ex))
                    throw ex;
            }
            throw new WrappedException(ex);
        }
    }

}