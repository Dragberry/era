package org.dragberry.era.common.registration;

import java.io.Serializable;

public class RegistrationPeriodTO implements Serializable {

	private static final long serialVersionUID = 9168697782853035403L;

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
