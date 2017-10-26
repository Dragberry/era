package org.dragberry.era.common.expression;

public class TestExpression {

	public static void main(String[] args) {
		String exp = "\"P1\" and \"P2\" and (\"L1\" or \"L2\")";
		ExpressionParser parser = new ExpressionParser();
		ExpressionResult<String> result = parser.parse(exp);
		
		System.out.println(result);
	}

}
