package org.dragberry.era.common.certificate;

import java.io.Serializable;

public class SubjectMarkCRUDTO<S extends Serializable> implements Serializable {

	private static final long serialVersionUID = -3082377271571049908L;

	private S subject;
	
	private Integer mark;

	public S getSubject() {
		return subject;
	}

	public void setSubject(S subject) {
		this.subject = subject;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}
	
	
}
