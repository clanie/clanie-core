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
package dk.clanie.core.util;

import static dk.clanie.core.Utils.opt;
import static dk.clanie.core.Utils.stream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

import java.util.UUID;
import java.util.function.Function;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class StringUtils {


	private StringUtils() {
		// Not meant to be instantiated
	}


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


	/**
	 * Generates a comma separated list of given values first mapped using given mapper and then converted to strings.
	 * 
	 * No escaping is performed.
	 */
	public static @NonNull <T> String csv(@Nullable Iterable<T> iterable, Function<? super T, ?> mapper) {
		return stream(iterable)
				.map(mapper)
				.map(StringUtils::asString)
				.collect(joining(","));
	}


	/**
	 * Truncates a string to a maximum number of bytes in UTF-8 encoding.
	 * 
	 * This method properly handles multi-byte UTF-8 characters and ensures that
	 * the returned string does not exceed the specified byte limit. If truncation
	 * would split a multi-byte character, the string is truncated at the boundary
	 * before that character. If the string is shorter than the byte limit, it is
	 * returned unchanged.
	 * 
	 * @param str the string to truncate (may be null)
	 * @param maxBytes the maximum number of bytes for the truncated string
	 * @return the truncated string, or null if the input is null
	 * @throws IllegalArgumentException if maxBytes is negative
	 */
	public static @Nullable String truncateToUtf8bytes(@Nullable String str, int maxBytes) {
		if (str == null) {
			return null;
		}
		
		if (maxBytes < 0) {
			throw new IllegalArgumentException("maxBytes must be non-negative");
		}
		
		if (maxBytes == 0) {
			return "";
		}
		
		byte[] bytes = str.getBytes(UTF_8);
		
		// If the string is already within the byte limit, return it as-is
		if (bytes.length <= maxBytes) {
			return str;
		}
		
		// Find the last position where we can safely truncate
		// Start from maxBytes and back up if we're in the middle of a multi-byte character
		int truncatePos = maxBytes;
		
		// Back up while we're looking at continuation bytes (pattern 10xxxxxx)
		while (truncatePos > 0 && (bytes[truncatePos - 1] & 0xC0) == 0x80) {
			truncatePos--;
		}
		
		// Now we're either at position 0, or at a position where bytes[truncatePos - 1]
		// is the first byte of a character. Check if that character is complete.
		if (truncatePos > 0) {
			byte leadByte = bytes[truncatePos - 1];
			int expectedSize = 1;
			
			// Determine the expected size of the character
			if ((leadByte & 0x80) == 0) {
				// Single byte: 0xxxxxxx
				expectedSize = 1;
			} else if ((leadByte & 0xE0) == 0xC0) {
				// Two bytes: 110xxxxx 10xxxxxx
				expectedSize = 2;
			} else if ((leadByte & 0xF0) == 0xE0) {
				// Three bytes: 1110xxxx 10xxxxxx 10xxxxxx
				expectedSize = 3;
			} else if ((leadByte & 0xF8) == 0xF0) {
				// Four bytes: 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
				expectedSize = 4;
			}
			
			// Check if we have all bytes needed for this character
			// The character starts at index (truncatePos - 1) and needs expectedSize bytes
			// So it ends at index (truncatePos - 1 + expectedSize - 1)
			// The next byte after the character would be at index (truncatePos - 1 + expectedSize)
			int charEndPos = truncatePos - 1 + expectedSize;
			
			if (charEndPos <= maxBytes) {
				// The character is complete and fits within maxBytes
				// Update truncatePos to include all bytes of the character
				truncatePos = charEndPos;
			} else {
				// The character is incomplete, back up to before it
				truncatePos--;
			}
		}
		
		return new String(bytes, 0, truncatePos, java.nio.charset.StandardCharsets.UTF_8);
	}

	
}
