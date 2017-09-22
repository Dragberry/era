package org.dragberry.era.common.registration;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RegisteredSpecialtyTO implements Serializable {

	private static final long serialVersionUID = 8050985848644030995L;
	
	private Long id;
	
	private String specialty;
	
	private String shortName;
	
	private Boolean separateByEducationBase;
	
	private Set<String> educationBases = new HashSet<>();
	
	private Boolean separateByEducationForm;
	
	private Set<Character> educationForms = new HashSet<>();
	
	private Boolean separateByFundsSource;
	
	private Set<Character> fundsSources = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Boolean getSeparateByEducationBase() {
		return separateByEducationBase;
	}

	public void setSeparateByEducationBase(Boolean separateByEducationBase) {
		this.separateByEducationBase = separateByEducationBase;
	}

	public Set<String> getEducationBases() {
		return educationBases;
	}

	public void setEducationBases(Set<String> educationBases) {
		this.educationBases = educationBases;
	}

	public Boolean getSeparateByEducationForm() {
		return separateByEducationForm;
	}

	public void setSeparateByEducationForm(Boolean separateByEducationForm) {
		this.separateByEducationForm = separateByEducationForm;
	}

	public Set<Character> getEducationForms() {
		return educationForms;
	}

	public void setEducationForms(Set<Character> educationForms) {
		this.educationForms = educationForms;
	}

	public Boolean getSeparateByFundsSource() {
		return separateByFundsSource;
	}

	public void setSeparateByFundsSource(Boolean separateByFundsSource) {
		this.separateByFundsSource = separateByFundsSource;
	}

	public Set<Character> getFundsSources() {
		return fundsSources;
	}

	public void setFundsSources(Set<Character> fundsSources) {
		this.fundsSources = fundsSources;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
