package org.dragberry.era.common.registration;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime registrationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String speciality) {
		this.specialty = speciality;
	}

	public Double getAttestateAvg() {
		return attestateAvg;
	}

	public void setAttestateAvg(Double attestateAvg) {
		this.attestateAvg = attestateAvg;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Character getFundsSource() {
		return fundsSource;
	}

	public void setFundsSource(Character fundsSource) {
		this.fundsSource = fundsSource;
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

}