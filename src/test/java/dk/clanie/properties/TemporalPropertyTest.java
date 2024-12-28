/**
 * Copyright (C) 2010, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.properties;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dk.clanie.core.collections.Tuple;
import dk.clanie.core.collections.Tuple.Pair;


public class TemporalPropertyTest {


	@BeforeAll
	public static void init() {
		Locale.setDefault(Locale.of("da"));
	}


	private TemporalProperty<String> temporalPropertyWithThreeValues() {
		TemporalProperty<String> prop = new TemporalProperty<String>();
		setThreeValues(prop);
		return prop;
	}


	private void setThreeValues(TemporalProperty<String> prop) {
		prop.set(Instant.parse("2010-01-05T00:00:00Z"), "one");
		prop.set(Instant.parse("2010-01-10T00:00:00Z"), "two");
		prop.set(Instant.parse("2010-01-15T00:00:00Z"), "three");
	}


	@Test
	public void testValuesVaryingOverTime() {
		TemporalProperty<String> prop = temporalPropertyWithThreeValues();
		assertThat(prop.get(Instant.parse("2010-01-04T00:00:00Z"))).isNull();;
		assertThat(prop.get(Instant.parse("2010-01-08T00:00:00Z"))).isEqualTo("one");
		assertThat(prop.get(Instant.parse("2010-01-12T00:00:00Z"))).isEqualTo("two");
		assertThat(prop.get(Instant.parse("2010-01-28T00:00:00Z"))).isEqualTo("three");
		assertThat(prop.get()).isEqualTo("three");
	}


	@Test
	public void testNotification() {
		final List<Pair<String, String>> changes = new ArrayList<>();
		TemporalProperty<String> prop = new TemporalProperty<String>();
		prop.addChangeListener(new PropertyChangeListener<String>() {
			@Override
			public void propertyChanged(String oldValue, String newValue) {
				changes.add(Tuple.of(oldValue, newValue));
			}
		});
		setThreeValues(prop);
		assertThat(changes).containsExactly(
				Tuple.of((String)null, "one"),
				Tuple.of("one", "two"),
				Tuple.of("two", "three"));
	}


}
