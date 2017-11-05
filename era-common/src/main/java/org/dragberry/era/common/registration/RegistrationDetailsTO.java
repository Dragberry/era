package org.dragberry.era.common.registration;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.dragberry.era.common.certificate.CertificateTO;
import org.dragberry.era.common.certificate.ExamSubjectCRUDTO;
import org.dragberry.era.common.certificate.SubjectMarkCRUDTO;
import org.dragberry.era.common.person.PersonCRUDTO;
import org.dragberry.era.common.specialty.SpecialtyTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class RegistrationDetailsTO implements Serializable {

	private static final long serialVersionUID = 5031693673010499164L;
	
	private Long id;
	
	private Long registrationId;
	
	private Character status;
	
	private String note;
	
	private PersonCRUDTO enrollee;
	
	private PersonCRUDTO payer;
	
	private Boolean enrolleeAsPayer;
	
	private Character fundsSource;
	
	private Character educationForm;
	
	private String educationBase;
	
	private String educationInstitution;
	
	private SpecialtyTO specialty;

	private List<String> prerogatives;
	
	private List<String> outOfCompetitions;
	
	private CertificateTO certificate;
	
	private List<SubjectMarkCRUDTO<ExamSubjectCRUDTO>>  examSubjectMarks;
	
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

	public PersonCRUDTO getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(PersonCRUDTO enrollee) {
		this.enrollee = enrollee;
	}

	public CertificateTO getCertificate() {
		return certificate;
	}

	public void setCertificate(CertificateTO certificate) {
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

	public String getEducationInstitution() {
		return educationInstitution;
	}

	public void setEducationInstitution(String educationInstitution) {
		this.educationInstitution = educationInstitution;
	}

	public SpecialtyTO getSpecialty() {
		return specialty;
	}

	public void setSpecialty(SpecialtyTO specialty) {
		this.specialty = specialty;
	}

	public List<String> getPrerogatives() {
		return prerogatives;
	}

	public void setPrerogatives(List<String> prerogatives) {
		this.prerogatives = prerogatives;
	}

	public List<String> getOutOfCompetitions() {
		return outOfCompetitions;
	}

	public void setOutOfCompetitions(List<String> outOfCompetitions) {
		this.outOfCompetitions = outOfCompetitions;
	}

	public List<SubjectMarkCRUDTO<ExamSubjectCRUDTO>>  getExamSubjectMarks() {
		return examSubjectMarks;
	}

	public void setExamSubjectMarks(List<SubjectMarkCRUDTO<ExamSubjectCRUDTO>> examSubjectMarks) {
		this.examSubjectMarks = examSubjectMarks;
	}

	public Boolean getEnrolleeAsPayer() {
		return enrolleeAsPayer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
