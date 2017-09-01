package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.domain.Registration;

public interface RegistrationDao extends DataAccessObject<Registration, Long> {

	List<Registration> searchList(RegistrationSearchQuery query);
}
