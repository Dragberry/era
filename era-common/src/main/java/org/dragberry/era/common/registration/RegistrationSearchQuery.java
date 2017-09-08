package org.dragberry.era.common.registration;

import java.io.Serializable;

public class RegistrationSearchQuery implements Serializable {
	
	private static final long serialVersionUID = -1241781657346724365L;

	private Long customerId;
	
	private String name;
	
	private Long periodId;
	
	private Long specialtyId;
	
	private Character studyType;
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public Long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(Long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public Character getStudyType() {
		return studyType;
	}

	public void setStudyType(Character studyType) {
		this.studyType = studyType;
	}
	
}
