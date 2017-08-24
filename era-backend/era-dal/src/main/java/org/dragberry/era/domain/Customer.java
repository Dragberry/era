package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name = "CUSTOMER")
@TableGenerator(
		name = "CUSTOMER_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "CUSTOMER_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Customer extends AbstractEntity {
	
	private static final long serialVersionUID = -2951173904850762419L;
	
	@Id
	@Column(name = "CUSTOMER_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CUSTOMER_GEN")
	private Long entityKey;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;

	}

}
