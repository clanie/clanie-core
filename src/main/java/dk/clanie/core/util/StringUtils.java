package dk.clanie.core.util;

import static dk.clanie.core.Utils.opt;
import static dk.clanie.core.Utils.stream;
import static java.util.stream.Collectors.joining;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class StringUtils {


	/**
	 * Null-safe toString.
	 */
	public static String asString(@Nullable Object object) {
		return object == null ? null : object.toString();
	}


	/**
	 * Null-safe String to UUID conversion.
	 */
	public static UUID asUuid(String s) {
		return opt(s).map(UUID::fromString).orElse(null);
	}


	/**
	 * Generates a comma separated list of given values converted to strings.
	 * 
	 * No escaping is performed.
	 */
	public static @NonNull String csv(@Nullable Iterable<?> iterable) {
		return stream(iterable)
				.map(StringUtils::asString)
				.collect(joining(","));
	}


}
