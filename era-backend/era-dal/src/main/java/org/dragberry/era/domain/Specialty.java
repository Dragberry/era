package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "SPECIALTY")
@TableGenerator(
		name = "SPECIALTY_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "SPECIALTY_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Specialty extends BaseEntity {

	private static final long serialVersionUID = 6491824262933007676L;
	
	@Id
	@Column(name = "SPECIALTY_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SPECIALTY_GEN")
	private Long entityKey;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "SHORT_NAME")
	private String shortName;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "QUALIFICATION")
	private String qualification;
	
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
