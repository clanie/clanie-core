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
