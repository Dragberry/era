package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.SubjectDao;
import org.dragberry.era.domain.Subject;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDaoImpl extends AbstractDao<Subject> implements SubjectDao {

	private final static String TITLE = "title";
	
	public SubjectDaoImpl() {
		super(Subject.class);
	}
	
	@Override
	public Integer getLastOrder() {
		return getEntityManager().createNamedQuery(Subject.GET_LAST_ORDER, Integer.class).getSingleResult();
	}
	
	@Override
	public Subject findByTitle(String title) {
		return getEntityManager().createNamedQuery(Subject.FIND_BY_TITLE, Subject.class)
				.setParameter(TITLE, title)
				.getSingleResult();
	}

}
