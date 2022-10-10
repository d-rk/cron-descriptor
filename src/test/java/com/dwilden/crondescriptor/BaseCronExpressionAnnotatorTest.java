package com.dwilden.crondescriptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BaseCronExpressionAnnotatorTest {

	@Test
	public void testStartsWithWhitespace() {
		assertThat(BaseCronExpressionAnnotator.isCronExpression(" 0 0 12 * * ?")).isFalse();
	}

	@Test
	public void cronWithTooMuchWhitespaceFails() {
		assertThat(BaseCronExpressionAnnotator.isCronExpression("0 1  * * ?")).isFalse();
	}

	@Test
	public void testEndsWithWhitespace() {
		assertThat(BaseCronExpressionAnnotator.isCronExpression("0 0 12 * * ? ")).isFalse();
	}

	@Test
	public void testValidCronExpression() {
		assertThat(BaseCronExpressionAnnotator.isCronExpression("0 0 12 * * ?")).isTrue();
	}

	@Test
	public void testInvalidCharacterInCron() {
		assertThat(BaseCronExpressionAnnotator.isCronExpression("0 0 % * * ?")).isFalse();
	}

	@Test
	public void testCronExpressionTooShort() {
		assertThat(BaseCronExpressionAnnotator.isCronExpression("0 0 12 *")).isFalse();
	}

	@Test
	public void testCronExpressionTooLong() {
		assertThat(BaseCronExpressionAnnotator.isCronExpression("0 0 12 * * * * *")).isFalse();
	}
}
