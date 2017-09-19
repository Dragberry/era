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
@Table(name = "OUT_OF_COMPETITION")
@NamedQueries({
	@NamedQuery(
			name = OutOfCompetition.FIND_OUT_OF_COMPETITIONS_BY_KEYS,
			query = "select o from OutOfCompetition o where o.entityKey in :entityKeys")
})
@TableGenerator(
		name = "OUT_OF_COMPETITION_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "OUT_OF_COMPETITION_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class OutOfCompetition extends Benefit {

	private static final long serialVersionUID = 2424023309695254630L;
	
	public static final String FIND_OUT_OF_COMPETITIONS_BY_KEYS = "OutOfCompetition.FetchOutOfCompetitionsByKeys";
	
	@Id
	@Column(name = "OUT_OF_COMPETITION_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "OUT_OF_COMPETITION_GEN")
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
