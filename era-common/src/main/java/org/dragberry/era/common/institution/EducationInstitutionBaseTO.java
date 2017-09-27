package org.dragberry.era.common.institution;

import java.io.Serializable;

public class EducationInstitutionBaseTO implements Serializable {

	private static final long serialVersionUID = 4569063216799661828L;
	
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
