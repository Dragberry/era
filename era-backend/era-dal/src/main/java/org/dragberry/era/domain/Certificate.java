package org.dragberry.era.domain;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "CERTIFICATE")
@NamedQueries({
	@NamedQuery(
			name = Certificate.GET_AVERAGE_MARK_QUERY,
			query = "select avg(VALUE(c.marks)) from Certificate c where c.entityKey = :certificateKey")
})
@TableGenerator(
		name = "CERTIFICATE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "CERTIFICATE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Certificate extends BaseEntity {
	
	private static final long serialVersionUID = 8040613837195453060L;

	public static final String GET_AVERAGE_MARK_QUERY = "Certificate.GetAverageMarkQuery";
	
	@Id
	@Column(name = "CERTIFICATE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CERTIFICATE_GEN")
	private Long entityKey;
	
	@Column(name = "YEAR")
	private Integer year;
	
	@Column(name = "INSTITUTION")
	private String institution;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENROLLEE_KEY", referencedColumnName = "PERSON_KEY")
	private Person enrollee;
	
	@ElementCollection
	@CollectionTable(name = "CERTIFICATE_SUBJECT", joinColumns = @JoinColumn(name = "CERTIFICATE_KEY"))
	@MapKeyJoinColumn(name = "SUBJECT_KEY")
	@Column(name = "MARK")
	private Map<Subject, Integer> marks;

	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Map<Subject, Integer> getMarks() {
		return marks;
	}

	public void setMarks(Map<Subject, Integer> marks) {
		this.marks = marks;
	}

	public Person getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Person enrollee) {
		this.enrollee = enrollee;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
