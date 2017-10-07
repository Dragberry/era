package org.dragberry.era.common.certificate;

import java.io.Serializable;

public class SubjectMarkCRUDTO implements Serializable {

	private static final long serialVersionUID = -3082377271571049908L;

	private SubjectCRUDTO subject;
	
	private Integer mark;

	public SubjectCRUDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectCRUDTO subject) {
		this.subject = subject;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}
	
	
}
