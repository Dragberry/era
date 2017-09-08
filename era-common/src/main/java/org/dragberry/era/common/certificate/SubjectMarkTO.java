package org.dragberry.era.common.certificate;

import java.io.Serializable;

public class SubjectMarkTO implements Serializable {

	private static final long serialVersionUID = -3082377271571049908L;

	private SubjectTO subject;
	
	private Integer mark;

	public SubjectTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectTO subject) {
		this.subject = subject;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}
	
	
}
