package org.dragberry.era.domain;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.EducationBaseConverter;
import org.dragberry.era.domain.converter.EducationFormConverter;
import org.dragberry.era.domain.converter.FundsSourceConverter;

@Entity
@Table(name = "REGISTERED_SPECIALTY")
@TableGenerator(
		name = "REG_SPECIALTY_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "REG_SPECIALTY_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class RegisteredSpecialty extends BaseEntity {

	private static final long serialVersionUID = 8673081078597821442L;

	@Id
	@Column(name = "REGISTERED_SPECIALTY_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REG_SPECIALTY_GEN")
	private Long entityKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALTY_KEY", referencedColumnName = "SPECIALTY_KEY")
	private Specialty specialty;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRATION_PERIOD_KEY", referencedColumnName = "REGISTRATION_PERIOD_KEY")
	private RegistrationPeriod registrationPeriod;
	
	@Column(name = "SEPARATE_BY_BASE")
	private Boolean separateByEducationBase;
	
	@ElementCollection
	@CollectionTable(name = "REG_SPECIALTY_EDUCATION_BASE", joinColumns = @JoinColumn(name = "REGISTERED_SPECIALTY_KEY"))
	@Column(name = "EDUCATION_BASE")
	@Convert(converter = EducationBaseConverter.class)
	private Set<EducationBase> educationBases;
	
	@Column(name = "SEPARATE_BY_FORM")
	private Boolean separateByEducationForm;
	
	@ElementCollection
	@CollectionTable(name = "REG_SPECIALTY_EDUCATION_FORM", joinColumns = @JoinColumn(name = "REGISTERED_SPECIALTY_KEY"))
	@Column(name = "EDUCATION_FORM")
	@Convert(converter = EducationFormConverter.class)
	private Set<EducationForm> educationForms;
	
	@Column(name = "SEPARATE_BY_FUNDS_SOURCE")
	private Boolean separateByFundsSource;
	
	@ElementCollection
	@CollectionTable(name = "REG_SPECIALTY_FUNDS_SOURCE", joinColumns = @JoinColumn(name = "REGISTERED_SPECIALTY_KEY"))
	@Column(name = "FUNDS_SOURCE")
	@Convert(converter = FundsSourceConverter.class)
	private Set<FundsSource> fundsSources;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey= entityKey;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public Boolean getSeparateByEducationBase() {
		return separateByEducationBase;
	}

	public void setSeparateByEducationBase(Boolean separateByEducationBase) {
		this.separateByEducationBase = separateByEducationBase;
	}

	public Set<EducationBase> getEducationBases() {
		return educationBases;
	}

	public void setEducationBases(Set<EducationBase> educationBases) {
		this.educationBases = educationBases;
	}

	public Boolean getSeparateByEducationForm() {
		return separateByEducationForm;
	}

	public void setSeparateByEducationForm(Boolean separateByEducationForm) {
		this.separateByEducationForm = separateByEducationForm;
	}

	public Set<EducationForm> getEducationForms() {
		return educationForms;
	}

	public void setEducationForms(Set<EducationForm> educationForms) {
		this.educationForms = educationForms;
	}

	public Boolean getSeparateByFundsSource() {
		return separateByFundsSource;
	}

	public void setSeparateByFundsSource(Boolean separateByFundsSource) {
		this.separateByFundsSource = separateByFundsSource;
	}

	public Set<FundsSource> getFundsSources() {
		return fundsSources;
	}

	public void setFundsSources(Set<FundsSource> fundsSources) {
		this.fundsSources = fundsSources;
	}

	public RegistrationPeriod getRegistrationPeriod() {
		return registrationPeriod;
	}

	public void setRegistrationPeriod(RegistrationPeriod registrationPeriod) {
		this.registrationPeriod = registrationPeriod;
	}

}
