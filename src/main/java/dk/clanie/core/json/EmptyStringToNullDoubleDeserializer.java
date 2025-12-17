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
package dk.clanie.core.json;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Deserializer that converts empty strings to null for Double fields.
 *
 * Used for fields which can be either a number or an empty string.
 */
public class EmptyStringToNullDoubleDeserializer extends ValueDeserializer<Double> {

	@Override
	public Double deserialize(JsonParser p, DeserializationContext ctxt) {
		JsonToken token = p.currentToken();
		
		// Handle null token
		if (token == JsonToken.VALUE_NULL) {
			return null;
		}
		
		// Handle numeric token
		if (token == JsonToken.VALUE_NUMBER_FLOAT || token == JsonToken.VALUE_NUMBER_INT) {
			return p.getDoubleValue();
		}
		
		// Handle string token
		if (token == JsonToken.VALUE_STRING) {
			String value = p.getString();
			if (value == null || value.trim().isEmpty()) {
				return null;
			}
			return Double.parseDouble(value);
		}
		
		// Unexpected token type
		return (Double) ctxt.handleUnexpectedToken(Double.class, p);
	}

}
