package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.PersonDao;
import org.dragberry.era.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoImpl extends AbstractDao<Person> implements PersonDao {

	public PersonDaoImpl() {
		super(Person.class);
	}

}
