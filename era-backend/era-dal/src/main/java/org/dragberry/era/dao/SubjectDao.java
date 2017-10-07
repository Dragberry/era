package org.dragberry.era.dao;

import org.dragberry.era.domain.Subject;

public interface SubjectDao extends DataAccessObject<Subject, Long> {

	Integer getLastOrder();

	Subject findByTitle(String title);

}
