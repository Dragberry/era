package org.dragberry.era.dao.impl;


import org.dragberry.era.dao.SpecialityDao;
import org.dragberry.era.domain.Speciality;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialityDaoImpl extends AbstractDao<Speciality> implements SpecialityDao {

	public SpecialityDaoImpl() {
		super(Speciality.class);
	}
	
}
