package org.dragberry.era.common.registration;

import org.dragberry.era.common.AbstractCRUDTO;
import org.dragberry.era.common.certificate.CertificateCRUDTO;
import org.dragberry.era.common.person.PersonCRUDTO;

public class RegistrationCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = 5031693673010499164L;
	
	private Long registrationPeriodId;
	
	private PersonCRUDTO enrollee;
	
	private Character studyForm;
	
	private Long educationInstitutionId;
	
	private Long specialtyId;

	private CertificateCRUDTO certificate;

	public Long getRegistrationPeriodId() {
		return registrationPeriodId;
	}

	public void setRegistrationPeriodId(Long registrationPeriodId) {
		this.registrationPeriodId = registrationPeriodId;
	}

	public PersonCRUDTO getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(PersonCRUDTO enrollee) {
		this.enrollee = enrollee;
	}

	public Character getStudyForm() {
		return studyForm;
	}

	public void setStudyForm(Character studyForm) {
		this.studyForm = studyForm;
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

}
