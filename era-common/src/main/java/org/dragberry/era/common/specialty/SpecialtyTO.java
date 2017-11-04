package org.dragberry.era.common.specialty;

import java.io.Serializable;

public class SpecialtyTO implements Serializable {

	private static final long serialVersionUID = 5214824342074675557L;
	
	private Long id;
	
	private String code;
	
	private String name;
	
	private String qualification;

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

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
