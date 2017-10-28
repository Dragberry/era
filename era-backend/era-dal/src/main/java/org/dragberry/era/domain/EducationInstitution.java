package org.dragberry.era.domain;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.FundsSourceConverter;

@Entity
@Table(name = "EDUCATION_INSTITUTION")
@NamedQueries({
	@NamedQuery(
			name = EducationInstitution.FIND_BY_CUSTOMER,
			query = "select c.institution from Customer c where c.entityKey = :customerKey"
			)
})
@TableGenerator(
		name = "EINSTITUTION_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "EINSTITUTION_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class EducationInstitution extends BaseEntity {

	private static final long serialVersionUID = 4453873327596382695L;
	
	public static final String FIND_BY_CUSTOMER = "EducationInstitution.FindByCustomer";

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
	
	@OneToMany
	@JoinTable(name="REGITRATION_CONTRACTS",
		joinColumns=@JoinColumn(name="EDUCATION_INSTITUTION_KEY"),
		inverseJoinColumns=@JoinColumn(name="REPORT_TEMPLATE_KEY"))
	@MapKeyColumn(name="FUNDS_SOURCE")
	@Convert(converter = FundsSourceConverter.class)
	private Map<FundsSource, ReportTemplate> registrationContracts;
	
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

	public Map<FundsSource, ReportTemplate> getRegistrationContracts() {
		return registrationContracts;
	}

	public void setRegistrationContracts(Map<FundsSource, ReportTemplate> registrationContracts) {
		this.registrationContracts = registrationContracts;
	}
	
}
