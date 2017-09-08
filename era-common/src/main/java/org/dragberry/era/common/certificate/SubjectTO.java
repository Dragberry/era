package org.dragberry.era.common.certificate;

import java.io.Serializable;

public class SubjectTO implements Serializable {

	private static final long serialVersionUID = -5242840692086448629L;

	private Long id;
	
	private String title;

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

}
