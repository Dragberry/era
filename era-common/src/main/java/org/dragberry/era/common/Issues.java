package org.dragberry.era.common;

public final class Issues {
	
	private Issues() {}
	
	public static IssueTO create(String errorCode, Object... params) {
		return new IssueTO(errorCode, params);
	}

}
