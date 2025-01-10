package dk.clanie.core.util;

import static dk.clanie.core.Utils.ge;
import static dk.clanie.core.Utils.gt;
import static dk.clanie.core.Utils.le;
import static dk.clanie.core.Utils.lt;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BooleanUtilsTest {


	@Test
	void testGt() {
		assertThat(gt(2, 3)).as("gt(2, 3").isFalse();
		assertThat(gt(3, 2)).as("gt(3, 2").isTrue();
		assertThat(gt(3, 3)).as("gt(3, 3").isFalse();
		assertThat(gt(2, null)).as("gt(2, null").isTrue();
		assertThat(gt(null, 4)).as("gt(null, 4").isFalse();
		assertThat(gt(null, null)).as("gt(null, null").isFalse();
	}


	@Test
	void testGe() {
		assertThat(ge(2, 3)).as("ge(2, 3").isFalse();
		assertThat(ge(3, 2)).as("ge(3, 2").isTrue();
		assertThat(ge(3, 3)).as("ge(3, 3").isTrue();
		assertThat(ge(2, null)).as("ge(2, null").isTrue();
		assertThat(ge(null, 4)).as("ge(null, 4").isFalse();
		assertThat(ge(null, null)).as("ge(null, null").isTrue();
	}


	@Test
	void testLt() {
		assertThat(lt(2, 3)).as("lt(2, 3").isTrue();
		assertThat(lt(3, 2)).as("lt(3, 2").isFalse();
		assertThat(lt(3, 3)).as("lt(3, 3").isFalse();
		assertThat(lt(2, null)).as("lt(2, null").isFalse();
		assertThat(lt(null, 4)).as("lt(null, 4").isTrue();
		assertThat(lt(null, null)).as("lt(null, null").isFalse();
	}


	@Test
	void testLe() {
		assertThat(le(2, 3)).as("le(2, 3").isTrue();
		assertThat(le(3, 2)).as("le(3, 2").isFalse();
		assertThat(le(3, 3)).as("le(3, 3").isTrue();
		assertThat(le(2, null)).as("le(2, null").isFalse();
		assertThat(le(null, 4)).as("le(null, 4").isTrue();
		assertThat(le(null, null)).as("le(null, null").isTrue();
	}


}
