package dk.clanie.core.util;

import static dk.clanie.core.Utils.stream;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class CollectionUtilsTest {


	@Test
	void testStreamIterable() {
		List<Integer> ints = List.of(1, 2, 3);
		assertThat(stream(ints).mapToInt(Integer::intValue).sum()).isEqualTo(6);
	}


	@Test
	void testStreamArray() {
		Integer[] ints = {1, 2, 3};
		assertThat(stream(ints).mapToInt(Integer::intValue).sum()).isEqualTo(6);
	}


}
