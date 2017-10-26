package org.dragberry.era.common.expression;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExpressionParser<T extends Serializable> {

	private static final String DELIMITER = "\\s|\\\"";
	private static final String QUOTE = "\"";
	private static final String SPACE = " ";
	
	private Function<String, T> dataProvider;
	
	public ExpressionParser(Function<String, T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public ExpressionResult<T> parse(String exp) {
		ExpressionResult<T> result = new ExpressionResult<>();
		Stack<List<T>> stack = new Stack<>();
		Arrays.stream(expressionToRPN(exp).split(DELIMITER)).filter(item -> !item.isEmpty()).forEach(item -> {
			Operator op = Operator.resolve(item);
			if (op == null) {
				List<T> list = new LinkedList<>();
				list.add(dataProvider.apply(item));
				stack.push(list);
			} else {
				if (op == Operator.OR) {
					List<T> l1 = stack.pop();
					stack.peek().addAll(l1);
				}
			}
		});
		result.setList(new ArrayList<>(stack));
		return result;
	}
	
	/**
	 * To Reverse Polish notation
	 */
	public String expressionToRPN(String exp) {
		StringBuilder result = new StringBuilder();
		Stack<Operator> operators = new Stack<>();
		Arrays.stream(exp.split(DELIMITER)).filter(s -> !s.isEmpty()).forEach(s -> {
			Operator op = Operator.resolve(s);
			if (op == null) {
				if (result.length() != 0) {
					result.append(SPACE);
				}
				result.append(QUOTE).append(s).append(QUOTE);
			} else {
				while (!operators.isEmpty() && op.priority > operators.peek().priority) {
					result.append(SPACE).append(operators.pop().value);
				}
				operators.push(op);
			}
		});
		while (!operators.empty()) {
			result.append(SPACE).append(operators.pop().value);
		}
		System.out.println(result.toString());
		return result.toString();
	}
	
	/**
	 * From Reverse Polish notation
	 */
	public String expressionFromRPN(String rpn) {
		Stack<List<String>> stack = new Stack<>();
		Arrays.stream(rpn.split(DELIMITER)).filter(item -> !item.isEmpty()).forEach(item -> {
			Operator op = Operator.resolve(item);
			if (op == null) {
				List<String> list = new LinkedList<>();
				list.add(item);
				stack.push(list);
			} else {
				if (op == Operator.OR) {
					List<String> l1 = stack.pop();
					stack.peek().addAll(l1);
				}
			}
		});
		StringBuilder sb = new StringBuilder();
		stack.forEach(item -> {
			if (sb.length() != 0) {
				sb.append(SPACE);
			}
			Operator op = item.size() == 1 ? Operator.AND : Operator.OR;
			
			if (item.size() == 1) {
				op = Operator.AND;
			} 
		});
		return null;
	}

}
