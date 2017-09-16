package org.dragberry.era.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IssueTO implements Serializable {

	private static final long serialVersionUID = -3093436226923145667L;

	private final String errorCode;
	
	private final List<Object> params;
	
	private final String fieldId;

	public IssueTO(String errorCode, Object... params) {
		this(errorCode, null, params);
	}
	
	public IssueTO(String errorCode, String fieldId, Object... params) {
		this.fieldId = fieldId;
		this.errorCode = errorCode;
		this.params = Arrays.asList(params);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public List<Object> getParams() {
		return params;
	}
	
	public String getFieldId() {
		return fieldId;
	}
	
	@Override
	public String toString() {
		StringBuffer sb =  new StringBuffer("Issue: {errorCode: ").append(errorCode);
		if (params != null && params.size() > 0) {
			sb.append(", params: [");
			Iterator<Object> iter = params.iterator();
			while (iter.hasNext()) {
				sb.append(iter.next());
				if (iter.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append("]");
		}
		if (fieldId != null) {
			sb.append(", fieldId: ").append(fieldId);
		}
		return sb.append("}").toString();
	}

}
