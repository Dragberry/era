package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "SUBJECT")
@TableGenerator(
		name = "SUBJECT_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "SUBJECT_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Subject extends BaseEntity {

	private static final long serialVersionUID = -6350070942122947614L;
	
	@Id
	@Column(name = "SUBJECT_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SUBJECT_GEN")
	private Long entityKey;

	@Column(name = "title")
	private String title;
	
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
	
}
