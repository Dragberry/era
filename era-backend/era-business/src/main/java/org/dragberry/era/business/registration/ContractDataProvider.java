package org.dragberry.era.business.registration;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.reporting.DataProvider;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.domain.Registration;

public class ContractDataProvider implements DataProvider {

	final static Pattern PATTERN = Pattern.compile("\\$\\{(.*?)\\}");
	
	private Registration registration;
	
	public ContractDataProvider(RegistrationDao registrationDao, Long registrationKey) {
		registration = registrationDao.findOne(registrationKey);
	}
	
	@Override
	public String getData(String param) {
		if (registration == null) {
			return StringUtils.EMPTY;
		}
		switch (param) {
			case "lastName":
				return registration.getEnrollee().getLastName();
			case "firstName":
				return registration.getEnrollee().getFirstName();
			case "middleName":
				return registration.getEnrollee().getMiddleName();
			default:
				throw new IllegalArgumentException("No such parameter for this tempalte: " + param);	
		}
	}
	
	@Override
	public Pattern getPropertyPattern() {
		return PATTERN;
	}
}