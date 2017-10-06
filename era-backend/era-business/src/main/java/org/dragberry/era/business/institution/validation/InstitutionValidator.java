package org.dragberry.era.business.institution.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.CountryService;
import org.dragberry.era.business.registration.validation.RegistrationCommon;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.dao.EducationInstitutionBaseDao;
import org.dragberry.era.domain.EducationInstitutionBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstitutionValidator implements Validator<EducationInstitutionBase> {

	@Autowired
	private EducationInstitutionBaseDao eibDao;
	
	@Autowired
	private CountryService countryService;
	
	private interface Errors extends RegistrationCommon {
		String PREFIX = "validation.education-institution.";
		String NAME_IS_EMPTY =  PREFIX + "name-is-empty";
		String NAME_IS_TOO_SHORT = PREFIX + "name-is-too-short";
		String NAME_IS_TOO_LONG = PREFIX + "name-is-too-long";
		String COUNTRY_IS_EMPTY = PREFIX + "country-is-empty";
		String COUNTRY_IS_INVALID = PREFIX + "country-is-invalid";
		String INSTITUTION_ALREADY_EXISTS = PREFIX + "institution-already-exists";
	}
	
	private interface FieldID {
		String NAME = "eiName";
		String COUNTRY = "eiCountry";
	}
	
	@Override
	public List<IssueTO> validate(EducationInstitutionBase entity) {
		List<IssueTO> issues = new ArrayList<>();
		// Name
		if (StringUtils.isBlank(entity.getName())) {
			issues.add(Issues.error(Errors.NAME_IS_EMPTY, FieldID.NAME));
		} else {
			issues.addAll(validateFieldLength(entity.getName(), 
					3, Errors.NAME_IS_TOO_SHORT, 128, Errors.NAME_IS_TOO_LONG, FieldID.NAME));
		}
		// Country
		if (StringUtils.isBlank(entity.getCountry())) {
			issues.add(Issues.warning(Errors.COUNTRY_IS_EMPTY, FieldID.COUNTRY));
		} else {
			if (!countryService.getCountryCodes().contains(entity.getCountry())) {
				issues.add(Issues.error(Errors.COUNTRY_IS_INVALID, FieldID.COUNTRY));
			}
		}
		// Common
		if (issues.isEmpty()) {
			if (eibDao.findByNameAndCountry(entity.getName(), entity.getCountry()) != null) {
				issues.add(Issues.error(Errors.INSTITUTION_ALREADY_EXISTS));
			}
		}
		return issues;
	}

}
