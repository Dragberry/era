package org.dragberry.era.dao.impl;

import javax.persistence.NoResultException;

import org.dragberry.era.dao.ExamSubjectDao;
import org.dragberry.era.domain.ExamSubject;
import org.springframework.stereotype.Repository;

@Repository
public class ExamSubjectDaoImpl extends AbstractDao<ExamSubject> implements ExamSubjectDao {

	private final static String TITLE = "title";
	private final static String CODE = "code";
	
	public ExamSubjectDaoImpl() {
		super(ExamSubject.class);
	}

	@Override
	public ExamSubject findByTitle(String title) {
		try {
			return getEntityManager().createNamedQuery(ExamSubject.FIND_BY_TITLE, ExamSubject.class)
					.setParameter(TITLE, title)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ExamSubject findByCode(String code) {
		try {
			return getEntityManager().createNamedQuery(ExamSubject.FIND_BY_CODE, ExamSubject.class)
					.setParameter(CODE, code)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
