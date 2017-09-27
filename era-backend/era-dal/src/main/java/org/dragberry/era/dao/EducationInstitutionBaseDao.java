package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.EducationInstitutionBase;

public interface EducationInstitutionBaseDao extends DataAccessObject<EducationInstitutionBase, Long> {

	List<EducationInstitutionBase> findByNameAndCountry(String name, String country, int count);
}
