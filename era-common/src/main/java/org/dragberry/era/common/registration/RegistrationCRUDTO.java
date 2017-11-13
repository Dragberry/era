package org.dragberry.era.common.registration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.dragberry.era.common.AbstractCRUDTO;
import org.dragberry.era.common.benefit.BenefitTO;
import org.dragberry.era.common.certificate.CertificateCRUDTO;
import org.dragberry.era.common.certificate.ExamSubjectCRUDTO;
import org.dragberry.era.common.certificate.SubjectMarkCRUDTO;
import org.dragberry.era.common.institution.EducationInstitutionTO;
import org.dragberry.era.common.person.PersonCRUDTO;
import org.dragberry.era.common.specialty.SpecialtyTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class RegistrationCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = 5031693673010499164L;
	
	private Long registrationId;
	
	private Long periodId;
	
	private String note;
	
	private PersonCRUDTO enrollee;
	
	private PersonCRUDTO payer;
	
	private Boolean enrolleeAsPayer;
	
	private Character fundsSource;
	
	private Character educationForm;
	
	private String educationBase;
	
	private EducationInstitutionTO educationInstitution;
	
	private SpecialtyTO specialty;

	private CertificateCRUDTO certificate;
	
	private List<BenefitTO> prerogatives = new ArrayList<>();
	
	private List<BenefitTO> outOfCompetitions = new ArrayList<>();
	
	private List<SubjectMarkCRUDTO<ExamSubjectCRUDTO>> examSubjectMarks = new ArrayList<>();

	private Character status;
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime registrationDate;
	
	private String registeredBy;
	
	private Long registeredById;
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime verificationDate;
	
	private String verifiedBy;
	
	private Long verifiedById;
	
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

	public EducationInstitutionTO getEducationInstitution() {
		return educationInstitution;
	}

	public void setEducationInstitution(EducationInstitutionTO educationInstitution) {
		this.educationInstitution = educationInstitution;
	}

	public SpecialtyTO getSpecialty() {
		return specialty;
	}

	public void setSpecialty(SpecialtyTO specialty) {
		this.specialty = specialty;
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

	public String getEducationBase() {
		return educationBase;
	}

	public void setEducationBase(String educationBase) {
		this.educationBase = educationBase;
	}

	public List<BenefitTO> getPrerogatives() {
		return prerogatives;
	}

	public void setPrerogatives(List<BenefitTO> prerogatives) {
		this.prerogatives = prerogatives;
	}

	public List<BenefitTO> getOutOfCompetitions() {
		return outOfCompetitions;
	}

	public void setOutOfCompetitions(List<BenefitTO> outOfCompetitions) {
		this.outOfCompetitions = outOfCompetitions;
	}

	public PersonCRUDTO getPayer() {
		return payer;
	}

	public void setPayer(PersonCRUDTO payer) {
		this.payer = payer;
	}

	public Boolean isEnrolleeAsPayer() {
		return enrolleeAsPayer;
	}

	public void setEnrolleeAsPayer(Boolean enrolleeAsPayer) {
		this.enrolleeAsPayer = enrolleeAsPayer;
	}

	public List<SubjectMarkCRUDTO<ExamSubjectCRUDTO>> getExamSubjectMarks() {
		return examSubjectMarks;
	}

	public void setExamSubjectMarks(List<SubjectMarkCRUDTO<ExamSubjectCRUDTO>> examSubjectMarks) {
		this.examSubjectMarks = examSubjectMarks;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(String registeredBy) {
		this.registeredBy = registeredBy;
	}

	public Long getRegisteredById() {
		return registeredById;
	}

	public void setRegisteredById(Long registeredById) {
		this.registeredById = registeredById;
	}

	public LocalDateTime getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(LocalDateTime verificationDate) {
		this.verificationDate = verificationDate;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Long getVerifiedById() {
		return verifiedById;
	}

	public void setVerifiedById(Long verifiedById) {
		this.verifiedById = verifiedById;
	}

}
