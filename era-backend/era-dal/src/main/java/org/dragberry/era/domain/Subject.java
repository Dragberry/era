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
@Table(name = "SUBJECT")
@NamedQueries({
	@NamedQuery(
			name = Subject.GET_LAST_ORDER,
			query = "select max(s.order) from Subject s"),
	@NamedQuery(
			name = Subject.FIND_BY_TITLE,
			query = "select s from Subject s where s.title = :title")
})
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
	
	public static final String GET_LAST_ORDER = "Subject.GetLastOrder";

	public static final String FIND_BY_TITLE = "Subject.FindByTitle";
	
	@Id
	@Column(name = "SUBJECT_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SUBJECT_GEN")
	private Long entityKey;
	
	@Column(name = "CODE")
	private String code;

	@Column(name = "title")
	private String title;
	
	@Column(name = "BASE")
	private Boolean base;
	
	@Column(name = "ORDER_VALUE")
	private Integer order;
	
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

	public Boolean isBase() {
		return base;
	}

	public void setBase(Boolean base) {
		this.base = base;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
