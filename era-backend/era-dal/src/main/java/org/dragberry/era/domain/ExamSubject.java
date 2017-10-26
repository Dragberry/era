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
@Table(name = "EXAM_SUBJECT")
@NamedQueries({
	@NamedQuery(
			name = ExamSubject.FIND_BY_TITLE,
			query = "select s from Subject s where s.title = :title"),
	@NamedQuery(
			name = ExamSubject.FIND_BY_CODE,
			query = "select s from Subject s where s.code = :code")
})
@TableGenerator(
		name = "EXAM_SUBJECT_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "EXAM_SUBJECT_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class ExamSubject extends BaseEntity {

	private static final long serialVersionUID = -6350070942122947614L;
	
	public static final String FIND_BY_TITLE = "ExamSubject.FindByTitle";
	
	public static final String FIND_BY_CODE = "ExamSubject.FindByCode";
	
	@Id
	@Column(name = "EXAM_SUBJECT_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EXAM_SUBJECT_GEN")
	private Long entityKey;
	
	@Column(name = "CODE")
	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
