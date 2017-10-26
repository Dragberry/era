package org.dragberry.era.common.expression;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpressionParserTest {

	private ExpressionParser parser = new ExpressionParser();
	
	@Test
	public void testExpressionToRPN_01() {
		String exp = "\"P1\" and \"L1\" or \"L2\" or \"L3\" and \"P2\" and \"P3\" or \"P4\"";
		System.out.println(parser.parse(exp));
	}

}
