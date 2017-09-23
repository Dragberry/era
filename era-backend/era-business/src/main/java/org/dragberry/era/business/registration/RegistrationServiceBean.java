package org.dragberry.era.business.registration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.business.validation.ValidationService;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.certificate.CertificateCRUDTO;
import org.dragberry.era.common.certificate.SubjectMarkTO;
import org.dragberry.era.common.institution.EducationInstitutionTO;
import org.dragberry.era.common.person.AddressTO;
import org.dragberry.era.common.person.ContactDetailsTO;
import org.dragberry.era.common.person.DocumentTO;
import org.dragberry.era.common.person.PersonCRUDTO;
import org.dragberry.era.common.registration.RegisteredSpecialtyTO;
import org.dragberry.era.common.registration.RegistrationCRUDTO;
import org.dragberry.era.common.registration.RegistrationPeriodTO;
import org.dragberry.era.common.registration.RegistrationSearchQuery;
import org.dragberry.era.common.registration.RegistrationTO;
import org.dragberry.era.dao.PrerogativeDao;
import org.dragberry.era.dao.CertificateDao;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.dao.OutOfCompetitionDao;
import org.dragberry.era.dao.PersonDao;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.dao.SpecialtyDao;
import org.dragberry.era.dao.SubjectDao;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Benefit;
import org.dragberry.era.domain.Certificate;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.EducationBase;
import org.dragberry.era.domain.EducationForm;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.Registration.Status;
import org.dragberry.era.domain.RegistrationPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceBean implements RegistrationService {

	@Autowired
	private PrerogativeDao prerogativeDao;
	@Autowired
	private OutOfCompetitionDao outOfCompetitionDao;
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
	private SubjectDao subjectDao;
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Autowired
	private ValidationService<Registration> validationService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RegistrationTO> getRegistrationList(RegistrationSearchQuery query) {
		return registrationDao.searchList(query).stream().map(entity -> {
			RegistrationTO to = new RegistrationTO();
			to.setRegistrationId(entity.getRegistrationId());
			to.setStatus(entity.getStatus().value);
			to.setFirstName(entity.getEnrollee().getFirstName());
			to.setLastName(entity.getEnrollee().getLastName());
			to.setMiddleName(entity.getEnrollee().getMiddleName());
			to.setId(entity.getEntityKey());
			to.setSpecialty(entity.getSpecialty().getTitle());
			to.setFundsSource(entity.getFundsSource().value);
			to.setEducationBase(entity.getEducationBase().value);
			to.setEducationForm(entity.getEducationForm().value);
			to.setOutOfCompetitions(getBenefitNames(entity.getOutOfCompetitions()));
			to.setPrerogatives(getBenefitNames(entity.getPrerogatives()));
			to.setRegistrationDate(entity.getRegistrationDate());
			to.setRegisteredBy(entity.getRegisteredBy().getUsername());
			to.setRegisteredById(entity.getRegisteredBy().getEntityKey());
			if (entity.getStatus() == Status.VERIFIED) {
				to.setVerificationDate(entity.getVerificationDate());
				to.setVerifiedBy(entity.getVerifiedBy().getUsername());
				to.setVerifiedById(entity.getVerifiedBy().getEntityKey());
			}
			to.setAttestateAvg(entity.getCertificate() != null 
					? certificateDao.getAverageMark(entity.getCertificate().getEntityKey()) : 0);
			return to;
		}).collect(Collectors.toList());
	}
	
	private static List<String> getBenefitNames(List<? extends Benefit> benefits) {
		return benefits.stream().map(Benefit::getName).collect(Collectors.toList());
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
				document.setCitizenhip(documentTO.getCitizenship());
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
			registration.setFundsSource(FundsSource.resolve(registrationCRUD.getFundsSource()));
		} catch (Exception exc) {
			registration.setFundsSource(null);
		}
		try {
			registration.setEducationForm(EducationForm.resolve(registrationCRUD.getEducationForm()));
		} catch (Exception exc) {
			registration.setEducationForm(null);
		}
		try {
			registration.setEducationBase(EducationBase.resolve(registrationCRUD.getEducationBase()));
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
		
		registration.setPrerogatives(prerogativeDao.fetchByKeys(registrationCRUD.getPrerogatives()));
		registration.setOutOfCompetitions(outOfCompetitionDao.fetchByKeys(registrationCRUD.getOutOfCompetitions()));
		
		CertificateCRUDTO cert = registrationCRUD.getCertificate();
		if (cert != null) {
			Certificate certificate = new Certificate();
			certificate.setEntityKey(cert.getId());
			certificate.setYear(cert.getYear());
			certificate.setInstitution(cert.getInstitution());
			certificate.setEnrollee(registration.getEnrollee());
			certificate.setMarks(cert.getMarks().stream().filter(sm -> sm.getMark() != null).collect(Collectors.toMap(
							sm -> subjectDao.findOne(sm.getSubject().getId()),
							SubjectMarkTO::getMark)));
			registration.setCertificate(certificate);
		}
		
		List<IssueTO> issues = validationService.validate(registration);
		if (issues.isEmpty()) {
			personDao.create(registration.getEnrollee());
			enrolleeCRUD.setId(registration.getEnrollee().getEntityKey());
			
			certificateDao.create(registration.getCertificate());
			registrationCRUD.getCertificate().setId(registration.getCertificate().getEntityKey());
			
			Long registrationId = getIdForRegistration(registration);
			registration.setRegistrationId(registrationId);
			registration.setStatus(Status.NOT_VERIFIED);

			registrationDao.create(registration);
			registrationCRUD.setId(registration.getEntityKey());
		}
		return Results.create(registrationCRUD, issues);
	}
	
	private long getIdForRegistration(Registration registration) {
		return registrationDao.findMaxRegistrationId(registration) + 1L;
	}

	@Override 
	@Transactional
	public List<RegistrationPeriodTO> getActiveRegistrationPeriods(Long customerKey) {
		return registrationPeriodDao.findActivePeriodsForCustomer(customerKey).stream()
				.map(RegistrationServiceBean::convertPeriod)
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public List<RegistrationPeriodTO> getRegistrationPeriodList(Long customerKey) {
		return registrationPeriodDao.fetchList(customerKey).stream()
				.map(RegistrationServiceBean::convertPeriod)
				.collect(Collectors.toList());
	}

	private static RegistrationPeriodTO convertPeriod(RegistrationPeriod entity) {
		RegistrationPeriodTO to = new RegistrationPeriodTO();
		to.setId(entity.getEntityKey());
		to.setTitle(entity.getTitle());
		to.setDateFrom(entity.getFrom());
		to.setDateTo(entity.getTo());
		to.setStatus(entity.getStatus().value);
		EducationInstitutionTO institution = new EducationInstitutionTO();
		institution.setId(entity.getEducationInstitution().getEntityKey());
		institution.setName(entity.getEducationInstitution().getName());
		institution.setShortName(entity.getEducationInstitution().getShortName());
		to.setEducationInstitution(institution);
		to.setSpecialties(entity.getSpecialties().stream().map(spec -> {
			RegisteredSpecialtyTO specTo = new RegisteredSpecialtyTO();
			specTo.setId(spec.getEntityKey());
			specTo.setSpecialty(spec.getSpecialty().getTitle());
			specTo.setSeparateByEducationBase(spec.getSeparateByEducationBase());
			specTo.setEducationBases(spec.getEducationBases().stream().map(EducationBase::getValue).collect(Collectors.toSet()));
			specTo.setSeparateByEducationForm(spec.getSeparateByEducationForm());
			specTo.setEducationForms(spec.getEducationForms().stream().map(EducationForm::getValue).collect(Collectors.toSet()));
			specTo.setSeparateByFundsSource(spec.getSeparateByFundsSource());
			specTo.setFundsSources(spec.getFundsSources().stream().map(FundsSource::getValue).collect(Collectors.toSet()));
			return specTo;
		}).collect(Collectors.toList()));
		return to;
	}
}
