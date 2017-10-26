package org.dragberry.era.common.certificate;

import java.io.Serializable;

public class ExamSubjectCRUDTO implements Serializable {

	private static final long serialVersionUID = -9215033804347687926L;

	private Long id;
	
	private String title;
	
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
