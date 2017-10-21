package org.dragberry.era.business.registration.validation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.CountryService;
import org.dragberry.era.business.validation.AbstractValidator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAddressValidator extends AbstractValidator<Registration> implements RegistrationValidationHelper {

	@Autowired
	private CountryService countryService;
	
	private class Errors {
		final String addressIsEmpty = errorCode("details-is-empty");
		final String countryIsEmpty = errorCode("country-is-empty");
		final String countryIsInvalid = errorCode("country-is-invalid");
		final String cityIsEmpty = errorCode("city-is-empty");
		final String cityIsTooLong = errorCode("city-is-too-long");
		final String streetIsEmpty = errorCode("street-is-empty");
		final String streetIsTooLong = errorCode("street-is-too-long");
		final String houseIsEmpty = errorCode("house-is-empty");
		final String houseIsTooLong = errorCode("house-is-too-long");
		final String housingIsTooLong = errorCode("housing-is-too-long");
		final String flatIsTooLong = errorCode("flat-is-too-long");
		final String zipCodeIsTooLong = errorCode("zip-code-is-too-long");
	}
	
	private class FieldID {
		final String country = fieldId("Country");
		final String city = fieldId("City");
		final String street = fieldId("Street");
		final String house = fieldId("House");
		final String housing = fieldId("Housing");
		final String flat = fieldId("Flat");
		final String zipCode = fieldId("ZipCode");
	}
	
	protected final Errors errors = new Errors();
	protected final FieldID fields = new FieldID();
	
	protected abstract Person getPerson(Registration registration);
	
	protected abstract Address getAddress(Registration registration);
	
	@Override
	protected boolean shouldValidate(Registration entity) {
		return getPerson(entity) != null;
	}
	
	@Override
	protected List<IssueTO> validateEntity(Registration entity) {
		Address address = getAddress(entity);
		if (address == null) {
			return issues(Issues.warning(errors.addressIsEmpty));
		}
		List<IssueTO> issues = new ArrayList<>();
		// Country
		if (address.getCountry() == null) {
			issues.add(Issues.warning(errors.countryIsEmpty, fields.country));
		} else {
			if (!countryService.getCountryCodes().contains(address.getCountry())) {
				issues.add(Issues.error(errors.countryIsInvalid, fields.country));
			}
		}
		// City
		if (StringUtils.isBlank(address.getCity())) {
			issues.add(Issues.warning(errors.cityIsEmpty, fields.city));
		} else {
			issues.addAll(validateFieldMaxLength(address.getCity(), 32, errors.cityIsTooLong, fields.city));
		}
		// Street
		if (StringUtils.isBlank(address.getStreet())) {
			issues.add(Issues.warning(errors.streetIsEmpty, fields.street));
		} else {
			issues.addAll(validateFieldMaxLength(address.getStreet(), 32, errors.streetIsTooLong, fields.street));
		}
		// House
		if (StringUtils.isBlank(address.getStreet())) {
			issues.add(Issues.warning(errors.houseIsEmpty, fields.house));
		} else {
			issues.addAll(validateFieldMaxLength(address.getHouse(), 8, errors.houseIsTooLong, fields.house));
		}
		// Housing
		issues.addAll(validateFieldMaxLength(address.getHousing(), 8, errors.housingIsTooLong, fields.housing));
		// Flat
		issues.addAll(validateFieldMaxLength(address.getFlat(), 8, errors.flatIsTooLong, fields.flat));
		// Zip code
		issues.addAll(validateFieldMaxLength(address.getZipCode(), 10, errors.zipCodeIsTooLong, fields.zipCode));
		return issues;
	}

}
