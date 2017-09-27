package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "EDUCATION_INSTITUTION_BASE")
@NamedQueries({
	@NamedQuery(
			name = EducationInstitutionBase.FIND_BY_NAME_AND_COUNTRY,
			query = "select eib from EducationInstitutionBase eib where eib.country = :country and eib.name like :name")
})
@TableGenerator(
		name = "EINSTITUTION_BASE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "EINSTITUTION_BASE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class EducationInstitutionBase extends BaseEntity {

	private static final long serialVersionUID = 4453873327596382695L;
	
	public static final String FIND_BY_NAME_AND_COUNTRY = "EducationInstitutionBase.FindByNameAndCountry";

	@Id
	@Column(name = "EDUCATION_INSTITUTION_BASE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EINSTITUTION_BASE_GEN")
	private Long entityKey;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
