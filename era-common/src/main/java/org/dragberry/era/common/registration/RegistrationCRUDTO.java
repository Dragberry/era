package org.dragberry.era.common.registration;

import org.dragberry.era.common.AbstractCRUDTO;
import org.dragberry.era.common.certificate.CertificateCRUDTO;
import org.dragberry.era.common.person.PersonCRUDTO;

public class RegistrationCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = 5031693673010499164L;
	
	private Long periodId;
	
	private PersonCRUDTO enrollee;
	
	private Character fundsSource;
	
	private Character educationForm;
	
	private Long educationInstitutionId;
	
	private Long specialtyId;

	private CertificateCRUDTO certificate;

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public PersonCRUDTO getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(PersonCRUDTO enrollee) {
		this.enrollee = enrollee;
	}

	public Long getEducationInstitutionId() {
		return educationInstitutionId;
	}

	public void setEducationInstitutionId(Long educationInstitutionId) {
		this.educationInstitutionId = educationInstitutionId;
	}

	public Long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(Long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public CertificateCRUDTO getCertificate() {
		return certificate;
	}

	public void setCertificate(CertificateCRUDTO certificate) {
		this.certificate = certificate;
	}

	public Character getFundsSource() {
		return fundsSource;
	}

	public void setFundsSource(Character fundsSource) {
		this.fundsSource = fundsSource;
	}

	public Character getEducationForm() {
		return educationForm;
	}

	public void setEducationForm(Character educationForm) {
		this.educationForm = educationForm;
	}

}
