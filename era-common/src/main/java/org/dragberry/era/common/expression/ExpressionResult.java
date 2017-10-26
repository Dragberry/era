package org.dragberry.era.common.expression;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpressionResult<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -6323063971115399513L;

	private List<List<T>> list = new ArrayList<>();

	public List<List<T>> getList() {
		return list;
	}

	public void setList(List<List<T>> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Expression=[");
		list.forEach(item -> {
			sb.append("\n\t").append(item.toString());
		});
		sb.append("\n]");
		return sb.toString();
	}
}
