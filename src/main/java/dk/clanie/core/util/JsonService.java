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

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.diff.JsonDiff;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonService {


	private final ObjectMapper objectMapper;


	/**
	 * Converts given object to json string (for logging).
	 * 
	 * Never throws exceptions, but may return an error-message if serializing given object fails.
	 */
	public String forLog(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return ExceptionUtils.getStackTrace(e);
		}
	}


	/**
	 * Converts given object to json string.
	 * 
	 * @throws RuntimeException if serialization fails.
	 */
	public String string(Object obj) throws RuntimeException {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Deserializes given json string to an object of given class.
	 * 
	 * @throws RuntimeException if serialization fails.
	 */
	public <T> T parse(String json, Class<T> type) throws RuntimeException {
		try {
			return objectMapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Deserializes given json string to an object of given type.
	 * 
	 * @throws RuntimeException if serialization fails.
	 */
	public <T> T parse(String json, TypeReference<T> type) throws RuntimeException {
		try {
			return objectMapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Clones given source object by serializing it to json and back to given type.
	 */
	public <T> T clone(T source, Class<T> type) {
		try {
			JsonNode json = objectMapper.convertValue(source, JsonNode.class);
			return objectMapper.treeToValue(json, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Cloning failed.", e);
		}
	}


	/**
	 * Clones given source object by serializing it to json and back to given type.
	 */
	public <T> T clone(T source, TypeReference<T> type) {
		try {
			byte[] json = objectMapper.writeValueAsBytes(source);
			return objectMapper.readValue(json, type);
		} catch (IOException e) {
			throw new RuntimeException("Cloning failed.", e);
		}
	}


	/**
	 * Loads JSON object from given class path resource.
	 */
	public <T> T load(String path, Class<T> type) {
		try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(path).getInputStream(), UTF_8)) {
			return objectMapper.readValue(reader, type);
		} catch (IOException e) {
			throw new RuntimeException("Loading json from " + path + " failed.", e);
		}
	}


	/**
	 * Loads JSON object from given class path resource.
	 */
	public <T> T load(String path, TypeReference<T> type) {
		try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(path).getInputStream(), UTF_8)) {
			return objectMapper.readValue(reader, type);
		} catch (IOException e) {
			throw new RuntimeException("Loading json from " + path + " failed.", e);
		}
	}


	/**
	 * Loads JSON object from given class path resource.
	 */
	public JsonNode parse(String path) {
		try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(path).getInputStream(), UTF_8)) {
			return objectMapper.readTree(reader);
		} catch (IOException e) {
			throw new RuntimeException("Loading json from " + path + " failed.", e);
		}
	}


	public <T> T applyPatch(T target, JsonPatch patch, Class<T> type) {
		try {
			JsonNode json = objectMapper.convertValue(target, JsonNode.class);
			json = patch.apply(json);
			return objectMapper.treeToValue(json, type);
		} catch (JsonProcessingException | JsonPatchException e) {
			throw new RuntimeException("Applying patch '" + forLog(patch) +
					"' on " + type.getSimpleName() + ": '" + forLog(target) + "' failed.", e);
		}
	}


	public <T> JsonPatch diff(T before, T changed) {
		JsonNode jsonBefore = objectMapper.convertValue(before, JsonNode.class);
		JsonNode jsonChanged = objectMapper.convertValue(changed, JsonNode.class);
		return JsonDiff.asJsonPatch(jsonBefore, jsonChanged);
	}


}