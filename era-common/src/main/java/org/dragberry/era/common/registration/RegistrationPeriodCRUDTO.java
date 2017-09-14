package org.dragberry.era.common.registration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.dragberry.era.common.AbstractCRUDTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class RegistrationPeriodCRUDTO extends AbstractCRUDTO {

	private static final long serialVersionUID = -382829832026992157L;
	
	private String title;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateFrom;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateTo;
	
	private String status;
	
	private Long educationInstitutionId;
	
	private List<RegisteredSpecialtyCRUDTO> specialties = new ArrayList<>();

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEducationInstitutionId() {
		return educationInstitutionId;
	}

	public void setEducationInstitutionId(Long educationInstitutionId) {
		this.educationInstitutionId = educationInstitutionId;
	}

	public List<RegisteredSpecialtyCRUDTO> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<RegisteredSpecialtyCRUDTO> specialties) {
		this.specialties = specialties;
	}

}
