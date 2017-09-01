package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "EDUCATION_INSTITUTION")
@TableGenerator(
		name = "EINSTITUTION_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "EINSTITUTION_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class EducationInstitution extends AbstractEntity {

	private static final long serialVersionUID = 4453873327596382695L;

	@Id
	@Column(name = "EDUCATION_INSTITUTION_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EINSTITUTION_GEN")
	private Long entityKey;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SHORT_NAME")
	private String shortName;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "CITY")
	private String city;
	
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
