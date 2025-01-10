/**
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

import static dk.clanie.core.Constants.UTC;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.lang.Nullable;

public class DateTimeUtils {


	/**
	 * Checks if given {code instant} is before the start of current day in the default time-zone.
	 */
	public static boolean beforeToday(Instant instant) {
		return beforeToday(ZoneId.systemDefault(), instant);
	}


	/**
	 * Checks if given {code instant} is before the start of current day in the given given time-zone.
	 * 
	 * Null is considered less than any non-null Instant.
	 */
	public static boolean beforeToday(ZoneId zoneId, @Nullable Instant instant) {
		return LocalDate.now(zoneId).atStartOfDay(UTC).toInstant().isAfter(instant);
	}


	/**
	 * Returns the earlier of the two given instants.
	 * 
	 * Null is considered less than any non-null Instant.
	 */
	public static @Nullable Instant min(@Nullable Instant a, @Nullable Instant b) {
		if (a == null || b == null) return null;
		return a.isBefore(b) ? a : b;
	}


	/**
	 * Returns the later of the two given instants.
	 * 
	 * Null is considered less than any non-null Instant.
	 */
	public static @Nullable Instant max(@Nullable Instant a, @Nullable Instant b) {
		if (a == null) return b;
		if (b == null) return a;
		return a.isBefore(b) ? b : a;
	}


}
