package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.SubjectDao;
import org.dragberry.era.domain.Subject;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDaoImpl extends AbstractDao<Subject> implements SubjectDao {

	public SubjectDaoImpl() {
		super(Subject.class);
	}

}
