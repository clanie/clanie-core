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
import lombok.Data;

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
			return new JsonService();
		}

	}


	@Autowired
	private JsonService json;


	@Data
	private static class KeyAndValue {
		private String k;
		private String v;
	}


	@Test
	public void testLoad_class() {
		KeyAndValue dto = json.load("dk/clanie/core/util/jsonServiceTest/keyValuePair.json", KeyAndValue.class);
		assertThat(dto.getK()).isEqualTo("key");
		assertThat(dto.getV()).isEqualTo("value");
	}


	@Test
	public void testLoad_array() {
		KeyAndValue[] dtos = json.load("dk/clanie/core/util/jsonServiceTest/keyValuePairs.json", KeyAndValue[].class);
		assertThat(dtos[0].getK()).isEqualTo("key");
		assertThat(dtos[0].getV()).isEqualTo("value");
		assertThat(dtos[1].getK()).isEqualTo("key2");
		assertThat(dtos[1].getV()).isEqualTo("value2");
		assertThat(dtos.length).isEqualTo(2);
	}


	@Test
	public void testLoad_list() {
		List<KeyAndValue> dtos = json.load("dk/clanie/core/util/jsonServiceTest/keyValuePairs.json", new TypeReference<List<KeyAndValue>>() {});
		assertThat(dtos.get(0).getK()).isEqualTo("key");
		assertThat(dtos.get(0).getV()).isEqualTo("value");
		assertThat(dtos.get(1).getK()).isEqualTo("key2");
		assertThat(dtos.get(1).getV()).isEqualTo("value2");
		assertThat(dtos.size()).isEqualTo(2);
	}


	@Test
	public void testLoad_genericType() {
		KeyValuePair<String, String> keyValuePair = json.load("dk/clanie/core/util/jsonServiceTest/keyValuePair.json", new TypeReference<KeyValuePair<String, String>>() {});
		assertThat(keyValuePair.getK()).isEqualTo("key");
		assertThat(keyValuePair.getV()).isEqualTo("value");
	}


}
