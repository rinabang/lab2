package lab2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class NumExpressionTest {

	@Test
	void testSolution() throws Exception {
		String testExp = "((1+(3*5))/4-1/2)";
		NumExpression exp = new NumExpression(testExp);
		double expected = exp.solution();
		double actual = 3.5;
		Assert.assertEquals(expected, actual, 0);
	}

}
