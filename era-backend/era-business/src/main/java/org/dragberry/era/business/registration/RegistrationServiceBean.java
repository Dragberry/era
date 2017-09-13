package org.dragberry.era.business.registration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.business.validation.ValidationService;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.person.AddressTO;
import org.dragberry.era.common.person.ContactDetailsTO;
import org.dragberry.era.common.person.DocumentTO;
import org.dragberry.era.common.person.PersonCRUDTO;
import org.dragberry.era.common.registration.RegistrationCRUDTO;
import org.dragberry.era.common.registration.RegistrationPeriodTO;
import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.common.registration.RegistrationTO;
import org.dragberry.era.dao.CertificateDao;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.dao.PersonDao;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.dao.SpecialtyDao;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.Registration.EducationForm;
import org.dragberry.era.domain.Registration.FundsSource;
import org.dragberry.era.domain.RegistrationPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceBean implements RegistrationService {

	@Autowired
	private CertificateDao certificateDao;
	@Autowired
	private EducationInstitutionDao educationInstitutionDao;
	@Autowired
	private PersonDao personDao;
	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	@Autowired
	private SpecialtyDao specialtyDao;
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Autowired
	private ValidationService<Registration> validationService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RegistrationTO> getRegistrationList(RegistrationSearchQuery query) {
		return registrationDao.searchList(query).stream().map(entity -> {
			RegistrationTO to = new RegistrationTO();
			to.setFirstName(entity.getEnrollee().getFirstName());
			to.setLastName(entity.getEnrollee().getLastName());
			to.setMiddleName(entity.getEnrollee().getMiddleName());
			to.setId(entity.getEntityKey());
			to.setRegistrationDate(entity.getRegistrationDate());
			to.setFundsSource(entity.getFundsSource().value);
			to.setSpecialty(entity.getSpecialty().getTitle());
			to.setAttestateAvg(entity.getCertificate() != null 
					? certificateDao.getAverageMark(entity.getCertificate().getEntityKey()) : 0);
			return to;
		}).collect(Collectors.toList());
	}
	
	@Override
	public RegistrationPeriodTO getActiveRegistrationPeriod(Long customerKey) {
		RegistrationPeriod period = registrationPeriodDao.findActivePeriodForCustomer(customerKey);
		if (period != null) {
			RegistrationPeriodTO to = new RegistrationPeriodTO();
			to.setId(period.getEntityKey());
			to.setTitle(period.getTitle());
			return to;
		}
		return null;
	}

	@Override
	@Transactional
	public ResultTO<RegistrationCRUDTO> createRegistration(RegistrationCRUDTO registrationCRUD) {
		Registration registration = new Registration();
		PersonCRUDTO enrolleeCRUD = registrationCRUD.getEnrollee();
		if (enrolleeCRUD != null) {
			Person enrollee = new Person();
			registration.setEnrollee(enrollee);
			enrollee.setFirstName(enrolleeCRUD.getFirstName());
			enrollee.setLastName(enrolleeCRUD.getLastName());
			enrollee.setMiddleName(enrolleeCRUD.getMiddleName());
			enrollee.setBirthdate(enrolleeCRUD.getBirthdate());
			AddressTO addressTO = enrolleeCRUD.getAddress();
			if (addressTO != null) {
				Address address = new Address();
				enrollee.setAddress(address);
				address.setCountry(addressTO.getCountry());
				address.setCity(addressTO.getCity());
				address.setStreet(addressTO.getStreet());
				address.setHouse(addressTO.getHouse());
				address.setHousing(addressTO.getHousing());
				address.setFlat(addressTO.getFlat());
				address.setZipCode(addressTO.getZipCode());
			}
			DocumentTO documentTO = enrolleeCRUD.getDocument();
			if (documentTO != null) {
				Document document = new Document();
				enrollee.setDocument(document);
				try {
					document.setType(Document.Type.valueOf(documentTO.getType()));
				} catch (Exception exc) {
					document.setType(null);
				}
				document.setId(documentTO.getId());
				document.setDocumentId(documentTO.getDocumentId());
				document.setIssueDate(documentTO.getIssueDate());
				document.setIssuedBy(documentTO.getIssuedBy());
			}
			ContactDetailsTO contactDetails = enrolleeCRUD.getContactDetails();
			if (contactDetails != null) {
				enrollee.setPhone(contactDetails.getPhone());
				enrollee.setEmail(contactDetails.getEmail());
			}
		}
		if (registrationCRUD.getEducationInstitutionId() != null) {
			registration.setInstitution(educationInstitutionDao.findOne(registrationCRUD.getEducationInstitutionId()));
		}
		if (registrationCRUD.getSpecialtyId() != null) {
			registration.setSpecialty(specialtyDao.findOne(registrationCRUD.getSpecialtyId()));
		}
		try {
			registration.setFundsSource(FundsSource.valueOf(registrationCRUD.getFundsSource()));
		} catch (Exception exc) {
			registration.setFundsSource(null);
		}
		try {
			registration.setEducationForm(EducationForm.valueOf(registrationCRUD.getEducationForm()));
		} catch (Exception exc) {
			registration.setEducationForm(null);
		}
		if (registrationCRUD.getUserAccountId() != null) {
			registration.setRegisteredBy(userAccountDao.findOne(registrationCRUD.getUserAccountId()));
		}
		if (registrationCRUD.getPeriodId() != null) {
			registration.setRegistrationPeriod(registrationPeriodDao.findOne(registrationCRUD.getPeriodId()));
		}
		registration.setRegistrationDate(LocalDateTime.now());
		
		List<IssueTO> issues = validationService.validate(registration);
		if (issues.isEmpty()) {
			personDao.create(registration.getEnrollee());
			enrolleeCRUD.setId(registration.getEnrollee().getEntityKey());
			registrationDao.create(registration);
			registrationCRUD.setId(registration.getEntityKey());
		}
		return Results.create(registrationCRUD, issues);
	}
}
