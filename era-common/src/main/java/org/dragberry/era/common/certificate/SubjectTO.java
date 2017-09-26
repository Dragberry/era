package org.dragberry.era.common.certificate;

import java.io.Serializable;

public class SubjectTO implements Serializable {

	private static final long serialVersionUID = -5242840692086448629L;

	private Long id;
	
	private String title;
	
	private Boolean base;

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

	public Boolean isBase() {
		return base;
	}

	public void setBase(Boolean base) {
		this.base = base;
	}
	
}
