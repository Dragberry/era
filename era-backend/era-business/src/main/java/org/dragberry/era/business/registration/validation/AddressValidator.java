package org.dragberry.era.business.registration.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.CountryService;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Registration;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressValidator implements Validator<Registration> {

	@Autowired
	private CountryService countryService;
	
	private interface Errors extends RegistrationCommon {
		String ADDRESS_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address.details-is-empty");
		String COUNTRY_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address.country-is-empty");
		String COUNTRY_IS_INVALID = RegistrationCommon.errorCode("enrollee.address.country-is-invalid");
		String CITY_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address.city-is-empty");
		String CITY_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.address.city-is-too-long");
		String STREET_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address.street-is-empty");
		String STREET_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.address.street-is-too-long");
		String HOUSE_IS_EMPTY = RegistrationCommon.errorCode("enrollee.address.house-is-empty");
		String HOUSE_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.address.house-is-too-long");
		String HOUSING_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.address.housing-is-too-long");
		String FLAT_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.address.flat-is-too-long");
		String ZIP_CODE_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.address.zip-code-is-too-long");
	}
	
	private interface FieldID {
		String COUNTRY = "aCountry";
		String CITY = "aCity";
		String STREET = "aStreet";
		String HOUSE = "aHouse";
		String HOUSING = "aHousing";
		String FLAT = "aFlat";
		String ZIP_CODE = "aZipCode";
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		if (entity.getEnrollee() == null) {
			return Collections.emptyList();
		}
		Address address = entity.getEnrollee().getAddress();
		if (address == null) {
			return issues(Issues.create(Errors.ADDRESS_IS_EMPTY));
		}
		List<IssueTO> issues = new ArrayList<>();
		// Country
		if (address.getCountry() == null) {
			issues.add(Issues.create(Errors.COUNTRY_IS_EMPTY, FieldID.COUNTRY));
		} else {
			if (!countryService.getCountryCodes().contains(address.getCountry())) {
				issues.add(Issues.create(Errors.COUNTRY_IS_INVALID, FieldID.COUNTRY));
			}
		}
		// City
		if (StringUtils.isBlank(address.getCity())) {
			issues.add(Issues.create(Errors.CITY_IS_EMPTY, FieldID.CITY));
		} else {
			issues.addAll(validateFieldMaxLength(address.getCity(), 32, Errors.CITY_IS_TOO_LONG, FieldID.CITY));
		}
		// Street
		if (StringUtils.isBlank(address.getStreet())) {
			issues.add(Issues.create(Errors.STREET_IS_EMPTY, FieldID.STREET));
		} else {
			issues.addAll(validateFieldMaxLength(address.getStreet(), 32, Errors.STREET_IS_TOO_LONG, FieldID.STREET));
		}
		// House
		if (StringUtils.isBlank(address.getStreet())) {
			issues.add(Issues.create(Errors.HOUSE_IS_EMPTY, FieldID.HOUSE));
		} else {
			issues.addAll(validateFieldMaxLength(address.getHouse(), 8, Errors.HOUSE_IS_TOO_LONG, FieldID.HOUSE));
		}
		// Housing
		issues.addAll(validateFieldMaxLength(address.getHousing(), 8, Errors.HOUSING_IS_TOO_LONG, FieldID.HOUSING));
		// Flat
		issues.addAll(validateFieldMaxLength(address.getFlat(), 8, Errors.FLAT_IS_TOO_LONG, FieldID.FLAT));
		// Zip code
		issues.addAll(validateFieldMaxLength(address.getZipCode(), 10, Errors.ZIP_CODE_IS_TOO_LONG, FieldID.ZIP_CODE));
		return issues;
	}

}
