package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "REGISTERED_ENTITY")
@TableGenerator(
		name = "REGISTERED_ENTITY_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "REGISTERED_ENTITY_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class RegisteredSpecialty extends BaseEntity {

	private static final long serialVersionUID = 8673081078597821442L;

	@Id
	@Column(name = "REGISTERED_ENTITY_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REGISTERED_ENTITY_GEN")
	private Long entityKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALTY_KEY", referencedColumnName = "SPECIALTY_KEY")
	private Specialty specialty;
	
	@Column(name ="EDUCATION_BASE")
	private EducationBase educationBase;
	
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

	public EducationBase getEducationBase() {
		return educationBase;
	}

	public void setEducationBase(EducationBase educationBase) {
		this.educationBase = educationBase;
	}

	
}
