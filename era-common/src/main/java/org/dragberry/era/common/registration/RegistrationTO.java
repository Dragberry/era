package org.dragberry.era.common.registration;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class RegistrationTO implements Serializable {
	
	private static final long serialVersionUID = 1742267786414238594L;

	private Long id;
	
	private Long registrationId;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private Character status;

	private String specialty;
	
	private Double attestateAvg;
	
	private Character fundsSource;
	
	private Character educationForm;
	
	private String educationBase;
	
	private List<String> prerogatives = new ArrayList<>();
	
	private List<String> outOfCompetitions = new ArrayList<>();
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Double getAttestateAvg() {
		return attestateAvg;
	}

	public void setAttestateAvg(Double attestateAvg) {
		this.attestateAvg = attestateAvg;
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