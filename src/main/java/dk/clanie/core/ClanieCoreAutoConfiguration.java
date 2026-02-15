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
package dk.clanie.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import dk.clanie.core.concurrent.CompositeContextPropagator;
import dk.clanie.core.concurrent.ContextPropagatingExecutor;
import dk.clanie.core.concurrent.ContextPropagator;
import dk.clanie.core.concurrent.LocaleContextPropagator;
import dk.clanie.core.concurrent.MdcContextPropagator;
import dk.clanie.core.concurrent.SpringSecurityContextPropagator;
import dk.clanie.core.util.JsonService;
import dk.clanie.core.util.LocaleProvider;
import tools.jackson.databind.ObjectMapper;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for clanie-core.
 */
@AutoConfiguration
public class ClanieCoreAutoConfiguration {


	@Bean
	@Lazy
	@ConditionalOnMissingBean
	JsonService jsonService(ObjectMapper objectMapper) {
		return new JsonService(objectMapper);
	}


	/**
	 * Creates an MDC context propagator.
	 * <p>
	 * This bean is always created to ensure MDC (logging context) is propagated.
	 */
	@Bean
	@ConditionalOnMissingBean(MdcContextPropagator.class)
	MdcContextPropagator mdcContextPropagator() {
		return new MdcContextPropagator();
	}


	/**
	 * Creates a Spring Security context propagator when Spring Security is available.
	 * <p>
	 * This bean is only created when Spring Security's SecurityContextHolder class
	 * is on the classpath.
	 */
	@Bean
	@ConditionalOnClass(name = "org.springframework.security.core.context.SecurityContextHolder")
	@ConditionalOnMissingBean(SpringSecurityContextPropagator.class)
	SpringSecurityContextPropagator springSecurityContextPropagator() {
		return new SpringSecurityContextPropagator();
	}


	/**
	 * Creates an Locale context propagator.
	 * <p>
	 * This bean is always created to ensure Locale information is propagated.
	 */
	@Bean
	@ConditionalOnMissingBean(LocaleContextPropagator.class)
	LocaleContextPropagator localeContextPropagator(Optional<LocaleProvider> localeProvider) {
		return new LocaleContextPropagator(localeProvider);
	}


	/**
	 * Creates a composite context propagator that combines all available propagators.
	 * <p>
	 * This will include:
	 * <ul>
	 *   <li>MDC propagation - always included</li>
	 *   <li>Spring Security propagation - if Spring Security is on the classpath</li>
	 *   <li>Any custom {@link ContextPropagator} beans registered in the application</li>
	 * </ul>
	 */
	@Bean
	@ConditionalOnMissingBean(CompositeContextPropagator.class)
	CompositeContextPropagator compositeContextPropagator(
			MdcContextPropagator mdcContextPropagator,
			List<ContextPropagator> additionalPropagators) {

		// Build list with MDC first, then any additional propagators (like Spring Security)
		List<ContextPropagator> allPropagators = new ArrayList<>();
		allPropagators.add(mdcContextPropagator);

		// Add other propagators (e.g., SpringSecurityContextPropagator if present)
		for (ContextPropagator propagator : additionalPropagators) {
			if (!(propagator instanceof MdcContextPropagator)) {
				allPropagators.add(propagator);
			}
		}

		return new CompositeContextPropagator(allPropagators);
	}


	/**
	 * Creates a context propagating executor using virtual threads.
	 * <p>
	 * This executor propagates all available context types via the composite propagator,
	 * which may include:
	 * <ul>
	 *   <li>SLF4J MDC (Mapped Diagnostic Context) - always available</li>
	 *   <li>Spring Security Context - if Spring Security is on the classpath</li>
	 *   <li>Any additional custom context propagators registered as beans</li>
	 * </ul>
	 */
	@Bean
	@ConditionalOnMissingBean
	ContextPropagatingExecutor contextPropagatingExecutor(CompositeContextPropagator compositeContextPropagator) {
		return new ContextPropagatingExecutor(compositeContextPropagator);
	}


}