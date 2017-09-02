package org.dragberry.era.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class IssueTO implements Serializable {

	private static final long serialVersionUID = -3093436226923145667L;

	private final String errorCode;
	
	private final List<Object> params;

	public IssueTO(String errorCode, Object... params) {
		this.errorCode = errorCode;
		this.params = Arrays.asList(params);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public List<Object> getParams() {
		return params;
	}

}
