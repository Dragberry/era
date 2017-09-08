package org.dragberry.era.dao.impl;


import org.dragberry.era.dao.SpecialtyDao;
import org.dragberry.era.domain.Specialty;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialtyDaoImpl extends AbstractDao<Specialty> implements SpecialtyDao {

	public SpecialtyDaoImpl() {
		super(Specialty.class);
	}
	
}
