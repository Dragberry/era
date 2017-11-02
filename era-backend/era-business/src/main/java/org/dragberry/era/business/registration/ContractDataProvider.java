package org.dragberry.era.business.registration;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dragberry.era.business.reporting.DataProvider;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.domain.EducationForm;
import org.dragberry.era.domain.Registration;

public class ContractDataProvider implements DataProvider {
	
	private static final String SEPARATOR = ", ";

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
				return registration.getEducationForm() == EducationForm.EXTRAMURAL ?
						"заочной" : "дневной";
			case "e.zipCode":
				return registration.getEnrollee().getAddress().getZipCode();
			case "e.city":
				return registration.getEnrollee().getAddress().getCity();
			case "e.address":
				return Stream.of(
						registration.getEnrollee().getAddress().getStreet(),
						house(registration.getEnrollee().getAddress().getHouse()),
						housing(registration.getEnrollee().getAddress().getHousing()),
						flat(registration.getEnrollee().getAddress().getFlat())).filter(StringUtils::isNotBlank).collect(Collectors.joining(SEPARATOR));
			case "document.documentId":
				return registration.getEnrollee().getDocument().getDocumentId();
			case "document.id":
				return registration.getEnrollee().getDocument().getId();
			case "document.issueDate":
				return registration.getEnrollee().getDocument().getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			case "documnet.issuedBy":
				return registration.getEnrollee().getDocument().getIssuedBy();
			case "p.firstName":
				return registration.getPayer().getFirstName();
			case "p.lastName":
				return registration.getPayer().getLastName();
			case "p.middleName":
				return registration.getPayer().getMiddleName();
			case "p.zipCode":
				return registration.getPayer().getAddress().getZipCode();
			case "p.city":
				return registration.getPayer().getAddress().getCity();
			case "p.address":
				return Stream.of(
						registration.getPayer().getAddress().getStreet(),
						house(registration.getPayer().getAddress().getHouse()),
						housing(registration.getPayer().getAddress().getHousing()),
						flat(registration.getPayer().getAddress().getFlat())).filter(StringUtils::isNotBlank).collect(Collectors.joining(SEPARATOR));
			case "p.doc.documentId":
				return registration.getPayer().getDocument().getDocumentId();
			case "p.doc.id":
				return registration.getPayer().getDocument().getId();
			case "p.doc.issueDate":
				return registration.getPayer().getDocument().getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			case "p.doc.issuedBy":
				return registration.getPayer().getDocument().getIssuedBy();
			case "whoIsPayer":
				return registration.isEnrolleeAsPayer() ? "Учащийся" : "Плательщик";
			default:
				LOG.warn(MessageFormat.format("The parameter {0} does not exist for this report", param));
				return StringUtils.EMPTY;
		}
	}
	
	private String house(String house) {
		return StringUtils.isNotBlank(house) ? "д." + house : null;
	}
	
	private String housing(String housing) {
		return StringUtils.isNotBlank(housing) ? "к." + housing : null;
	}
	
	private String flat(String flat) {
		return StringUtils.isNotBlank(flat) ? "кв." + flat : null;
	}

	@Override
	public Pattern getPropertyPattern() {
		return PATTERN;
	}
}