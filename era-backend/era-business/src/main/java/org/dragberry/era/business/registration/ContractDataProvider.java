package org.dragberry.era.business.registration;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dragberry.era.business.reporting.DataProvider;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.domain.Registration;

public class ContractDataProvider implements DataProvider {
	
	private static final Logger LOG = LogManager.getLogger(ContractDataProvider.class);

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
		String result = get(param);
		return result != null ? result : StringUtils.EMPTY;
	}
	
	private String get(String param) {
		switch (param) {
			case "lastName":
				return registration.getEnrollee().getLastName();
			case "firstName":
				return registration.getEnrollee().getFirstName();
			case "middleName":
				return registration.getEnrollee().getMiddleName();
			case "specialty.title":
				return registration.getSpecialty().getTitle();
			case "specialty.qualification":
				return registration.getSpecialty().getQualification();
			case "educationForm":
				return registration.getEducationForm().name();
			case "e.zipCode":
				return registration.getEnrollee().getAddress().getZipCode();
			case "e.city":
				return registration.getEnrollee().getAddress().getCity();
			case "e.address":
				return registration.getEnrollee().getAddress().getStreet();
			case "document.documentId":
				return registration.getEnrollee().getDocument().getDocumentId();
			case "document.id":
				return registration.getEnrollee().getDocument().getId();
			case "document.issueDate":
				return registration.getEnrollee().getDocument().getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			case "documnet.issuedBy":
				return registration.getEnrollee().getDocument().getIssuedBy();
			default:
				LOG.warn(MessageFormat.format("The parameter {0} does not exist for this report", param));
				return StringUtils.EMPTY;
		}
	}
	
	@Override
	public Pattern getPropertyPattern() {
		return PATTERN;
	}
}