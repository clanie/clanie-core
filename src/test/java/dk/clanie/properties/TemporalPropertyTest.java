/**
 * Copyright (C) 2010, Claus Nielsen, cn@cn-consult.dk
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

import static dk.clanie.collections.CollectionFactory.newArrayList;
import static dk.clanie.util.Tuple.newTuple;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Locale;

import org.joda.time.Instant;
import org.junit.BeforeClass;
import org.junit.Test;

import dk.clanie.util.Tuple.Pair;


public class TemporalPropertyTest {


	@BeforeClass
	public static void init() {
		Locale.setDefault(new Locale("da"));
	}


	private TemporalProperty<String> temporalPropertyWithThreeValues() {
		TemporalProperty<String> prop = new TemporalProperty<String>();
		setThreeValues(prop);
		return prop;
	}


	private void setThreeValues(TemporalProperty<String> prop) {
		prop.set(new Instant("2010-01-05"), "one");
		prop.set(new Instant("2010-01-10"), "two");
		prop.set(new Instant("2010-01-15"), "three");
	}


	@Test
	public void testValuesVaryingOverTime() {
		TemporalProperty<String> prop = temporalPropertyWithThreeValues();
		assertThat(prop.get(new Instant("2010-01-04")), is(nullValue()));
		assertThat(prop.get(new Instant("2010-01-08")), is("one"));
		assertThat(prop.get(new Instant("2010-01-12")), is("two"));
		assertThat(prop.get(new Instant("2010-01-28")), is("three"));
		assertThat(prop.get(), is("three"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testNotification() {
		final List<Pair<String, String>> changes = newArrayList();
		TemporalProperty<String> prop = new TemporalProperty<String>();
		prop.addChangeListener(new PropertyChangeListener<String>() {
			@Override
			public void propertyChanged(String oldValue, String newValue) {
				changes.add(newTuple(oldValue, newValue));
			}
		});
		setThreeValues(prop);
		assertThat(changes, hasItems(newTuple((String)null, "one"), newTuple("one", "two"), newTuple("two", "three")));
	}

}
