package org.dragberry.era.common.registration;

import java.io.Serializable;

public class RegistrationSearchQuery implements Serializable {
	
	private static final long serialVersionUID = -1241781657346724365L;

	private Long customerId;
	
	private Long periodId;
	
	private Long educationInstitutionId;
	
	private Long specialtyId;
	
	private Long registrationId;
	
	private String name;
	
	private Character educationForm;
	
	private String educationBase;
	
	private Character fundsSource;
	
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

	public Character getFundsSource() {
		return fundsSource;
	}

	public void setFundsSource(Character fundsSource) {
		this.fundsSource = fundsSource;
	}

	public Long getEducationInstitutionId() {
		return educationInstitutionId;
	}

	public void setEducationInstitutionId(Long educationInstitutionId) {
		this.educationInstitutionId = educationInstitutionId;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public Character getEducationForm() {
		return educationForm;
	}

	public void setEducationForm(Character educationForm) {
		this.educationForm = educationForm;
	}

	public String getEducationBase() {
		return educationBase;
	}

	public void setEducationBase(String educationBase) {
		this.educationBase = educationBase;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
