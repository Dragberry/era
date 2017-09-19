package org.dragberry.era.common.registration;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.dragberry.era.common.institution.EducationInstitutionTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class RegistrationPeriodTO implements Serializable {

	private static final long serialVersionUID = 9168697782853035403L;

	private Long id;
	
	private String title;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateFrom;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateTo;
	
	private Character status;
	
	private EducationInstitutionTO educationInstitution;
	
	private List<RegisteredSpecialtyTO> specialties = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public List<RegisteredSpecialtyTO> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<RegisteredSpecialtyTO> specialties) {
		this.specialties = specialties;
	}

	public EducationInstitutionTO getEducationInstitution() {
		return educationInstitution;
	}

	public void setEducationInstitution(EducationInstitutionTO educationInstitution) {
		this.educationInstitution = educationInstitution;
	}
	
}
