package org.dragberry.era.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name = "BENEFIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NamedQueries({
	@NamedQuery(
			name = Benefit.FETCH_ACTIVE_BENEFITS,
			query = "select b from Benefit b where b.deleted = false"),
	@NamedQuery(
			name = Benefit.FIND_BENEFITS_BY_KEYS,
			query = "select b from Benefit b where TYPE(b) = :type and b.entityKey in :entityKeys")
})
@TableGenerator(
		name = "BENEFIT_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "BENEFIT_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public abstract class Benefit extends BaseEntity {

	private static final long serialVersionUID = 2424023309695254630L;
	
	public static final String FETCH_ACTIVE_BENEFITS = "Benefit.FetchActiveBenefits";
	public static final String FIND_BENEFITS_BY_KEYS = "Benefit.FetchBenefitsByKeys";
	
	@Id
	@Column(name = "BENEFIT_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BENEFIT_GEN")
	private Long entityKey;
	
	@Column(name = "PRIORITY")
	private Integer priority;
	
	@Column(name = "DELETED")
	private Boolean deleted;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
