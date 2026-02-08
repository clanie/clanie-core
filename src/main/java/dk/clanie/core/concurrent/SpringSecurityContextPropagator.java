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

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security implementation of {@link ContextPropagator}.
 * <p>
 * This implementation captures and propagates the Spring Security context
 * to virtual threads, ensuring that authentication and authorization
 * information is available in asynchronous operations.
 */
public class SpringSecurityContextPropagator implements ContextPropagator {

	@Override
	public Object capture() {
		return SecurityContextHolder.getContext();
	}


	@Override
	public void set(Object context) {
		if (context instanceof SecurityContext securityContext) {
			SecurityContextHolder.setContext(securityContext);
		}
	}


	@Override
	public void clear() {
		SecurityContextHolder.clearContext();
	}

}
