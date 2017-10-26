package org.dragberry.era.dao;

import org.dragberry.era.domain.ExamSubject;

public interface ExamSubjectDao extends DataAccessObject<ExamSubject, Long> {

	ExamSubject findByTitle(String title);
	
	ExamSubject findByCode(String code);

}
