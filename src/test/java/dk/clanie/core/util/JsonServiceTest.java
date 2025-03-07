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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dk.clanie.core.collections.KeyValuePair;

@SpringJUnitConfig(JsonServiceTest.Config.class)
public class JsonServiceTest {

	@Configuration
	static class Config {

		@Bean
		ObjectMapper objectMapper() {
			return new ObjectMapper();
		}

		@Bean
		JsonService json() {
			return new JsonService(objectMapper());
		}

	}


	@Autowired
	private JsonService json;


	private record KeyAndValue (String k, String v) {}


	@Test
	public void testLoad_class() {
		KeyAndValue dto = json.load("dk/clanie/core/util/JsonServiceTest/keyValuePair.json", KeyAndValue.class);
		assertThat(dto.k()).isEqualTo("key");
		assertThat(dto.v()).isEqualTo("value");
	}


	@Test
	public void testLoad_array() {
		KeyAndValue[] dtos = json.load("dk/clanie/core/util/JsonServiceTest/keyValuePairs.json", KeyAndValue[].class);
		assertThat(dtos[0].k()).isEqualTo("key");
		assertThat(dtos[0].v()).isEqualTo("value");
		assertThat(dtos[1].k()).isEqualTo("key2");
		assertThat(dtos[1].v()).isEqualTo("value2");
		assertThat(dtos.length).isEqualTo(2);
	}


	@Test
	public void testLoad_list() {
		List<KeyAndValue> dtos = json.load("dk/clanie/core/util/JsonServiceTest/keyValuePairs.json", new TypeReference<List<KeyAndValue>>() {});
		assertThat(dtos.get(0).k()).isEqualTo("key");
		assertThat(dtos.get(0).v()).isEqualTo("value");
		assertThat(dtos.get(1).k()).isEqualTo("key2");
		assertThat(dtos.get(1).v()).isEqualTo("value2");
		assertThat(dtos.size()).isEqualTo(2);
	}


	@Test
	public void testLoad_genericType() {
		KeyValuePair<String, String> keyValuePair = json.load("dk/clanie/core/util/JsonServiceTest/keyValuePair.json", new TypeReference<KeyValuePair<String, String>>() {});
		assertThat(keyValuePair.k()).isEqualTo("key");
		assertThat(keyValuePair.v()).isEqualTo("value");
	}


}
