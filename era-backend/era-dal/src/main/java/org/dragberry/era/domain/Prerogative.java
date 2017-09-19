package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "PREROGATIVE")
@TableGenerator(
		name = "PREROGATIVE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "PREROGATIVE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Prerogative extends Benefit {

	private static final long serialVersionUID = 2424023309695254630L;
	
	@Id
	@Column(name = "PREROGATIVE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PREROGATIVE_GEN")
	private Long entityKey;

	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}
}
