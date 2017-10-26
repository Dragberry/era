package org.dragberry.era.common.expression;

public enum Operator {

	AND("and", 1), OR("or", 0);
	
	public final int priority;
	public final  String value;
	
	private Operator(String value, int priority) {
		this.value = value;
		this.priority = priority;
	}
	
	public static Operator resolve(String value) {
		for (Operator op : values()) {
			if (op.value.equals(value)) {
				return op;
			}
		}
		return null;
	}
	
}
