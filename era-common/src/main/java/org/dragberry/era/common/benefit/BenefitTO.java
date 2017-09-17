package org.dragberry.era.common.benefit;

import java.io.Serializable;

public class BenefitTO implements Serializable {

	private static final long serialVersionUID = -1555109931650459428L;

	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
