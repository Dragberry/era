package org.dragberry.era.business.registration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dragberry.era.business.certificate.CertificateService;
import org.dragberry.era.business.institution.EducationInstitutionService;
import org.dragberry.era.business.validation.ValidationService;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.IssueType;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.certificate.CertificateCRUDTO;
import org.dragberry.era.common.certificate.ExamSubjectCRUDTO;
import org.dragberry.era.common.certificate.SubjectCRUDTO;
import org.dragberry.era.common.certificate.SubjectMarkCRUDTO;
import org.dragberry.era.common.expression.ExpressionParser;
import org.dragberry.era.common.institution.EducationInstitutionBaseCRUDTO;
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
import org.dragberry.era.dao.EducationInstitutionBaseDao;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.dao.ExamSubjectDao;
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
import org.dragberry.era.domain.EducationInstitutionBase;
import org.dragberry.era.domain.ExamSubject;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.Registration.Status;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.domain.Subject;
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
	private EducationInstitutionBaseDao educationInstitutionBaseDao;
	@Autowired
	private ExamSubjectDao examSubjectDao;
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
	
	@Autowired
	private EducationInstitutionService eiService;
	@Autowired
	private CertificateService certificationService;
	
	private ExpressionParser<ExamSubjectCRUDTO> parser = new ExpressionParser<ExamSubjectCRUDTO>(code -> {
		ExamSubject es = examSubjectDao.findByCode(code);
		ExamSubjectCRUDTO esTO = new ExamSubjectCRUDTO();
		esTO.setCode(es.getCode());
		esTO.setId(es.getEntityKey());
		esTO.setTitle(es.getTitle());
		return esTO;
	});
	
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
		List<IssueTO> allIssues = new ArrayList<>();
		Registration registration = new Registration();
		PersonCRUDTO enrolleeCRUD = registrationCRUD.getEnrollee();
		if (enrolleeCRUD != null) {
			Person enrollee = convertPerson(enrolleeCRUD);
			registration.setEnrollee(enrollee);
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
			EducationInstitutionBaseCRUDTO institution = cert.getInstitution();
			if (institution != null) {
				EducationInstitutionBase eiBase = null;
				if (institution.getId() != null) {
					eiBase = educationInstitutionBaseDao.findOne(institution.getId());
				} else if (institution.getName() != null && institution.getCountry() != null) {
					eiBase = educationInstitutionBaseDao.findByNameAndCountry(institution.getName(), institution.getCountry());
				}
				if (eiBase == null) {
					ResultTO<EducationInstitutionBaseCRUDTO> eiResult = eiService.create(institution);
					if (eiResult.getIssues().isEmpty()) {
						eiBase = educationInstitutionBaseDao.findOne(institution.getId());
					} else {
						allIssues.addAll(eiResult.getIssues());
					}
				}
				certificate.setInstitution(eiBase);
			}
			
			certificate.setEnrollee(registration.getEnrollee());
			
			certificate.setMarks(cert.getMarks().stream().filter(sm -> sm.getMark() != null).collect(Collectors.toMap(
							sm -> subjectDao.findOne(sm.getSubject().getId()),
							SubjectMarkCRUDTO::getMark)));
			
			List<Subject> extraSubjects = new ArrayList<>();
			boolean extraSubjectsHasIssues = false;
			for (SubjectMarkCRUDTO<SubjectCRUDTO> sm : cert.getExtraMarks()) {
				SubjectCRUDTO subject = sm.getSubject();
				if (subject != null) {
					if (subject.getId() != null) {
						extraSubjects.add(subjectDao.findOne(subject.getId()));
					} else {
						ResultTO<SubjectCRUDTO> subjectResult = certificationService.createSubject(subject);
						if (!subjectResult.getIssues().isEmpty()) {
							allIssues.addAll(subjectResult.getIssues());
							extraSubjectsHasIssues = true;
						}
					}
				}
			}
			
			if (!extraSubjectsHasIssues) {
				certificate.getMarks().putAll(cert.getExtraMarks().stream().filter(esm -> esm.getMark() != null).collect(Collectors.toMap(
						esm -> subjectDao.findOne(esm.getSubject().getId()),
						SubjectMarkCRUDTO::getMark)));
			}
			
			registration.setCertificate(certificate);
		}
		
		if (registrationCRUD.getExamSubjectMarks() != null) {
			Map<ExamSubject, Integer> examMarks = new HashMap<>();
			registrationCRUD.getExamSubjectMarks().forEach(esm -> {
				examMarks.put(examSubjectDao.findOne(esm.getSubject().getId()), esm.getMark());
			});
			registration.setExamMarks(examMarks);
		}
		
		registration.setEnrolleeAsPayer(registrationCRUD.isEnrolleeAsPayer());
		if (registration.getFundsSource() != null && registration.getFundsSource() == FundsSource.PAYER) {
			if (!registration.isEnrolleeAsPayer()) {
				PersonCRUDTO payerCRUD = registrationCRUD.getPayer();
				if (payerCRUD != null) {
					registration.setPayer(convertPerson(payerCRUD));
				}
			}
		}
		
		allIssues.addAll(validationService.validate(registration));
		List<IssueTO> errorIssues = Collections.emptyList();
		Status status = Status.NOT_VERIFIED;
		if (!allIssues.isEmpty() && registrationCRUD.getIgnoreWarnings()) {
			errorIssues = allIssues.stream().filter(issue -> IssueType.WARNING != issue.getType()).collect(Collectors.toList());
			if (errorIssues.isEmpty()) {
				status = Status.UNCOMPLETE;
			}
		}
		if (allIssues.isEmpty() || errorIssues.isEmpty() && registrationCRUD.getIgnoreWarnings()) {
			Person enrollee = personDao.create(registration.getEnrollee());
			enrolleeCRUD.setId(enrollee.getEntityKey());
			
			if (registrationCRUD.isEnrolleeAsPayer()) {
				registration.setPayer(enrollee);
			} else if (registration.getPayer() != null) {
				personDao.create(registration.getPayer());
				registrationCRUD.getPayer().setId(registration.getPayer().getEntityKey());
			}
			
			certificateDao.create(registration.getCertificate());
			registrationCRUD.getCertificate().setId(registration.getCertificate().getEntityKey());
			
			Long registrationId = getIdForRegistration(registration);
			registration.setRegistrationId(registrationId);
			registration.setStatus(status);

			registrationDao.create(registration);
			registrationCRUD.setId(registration.getEntityKey());
			registrationCRUD.setRegistrationId(registrationId);
		}
		return Results.create(registrationCRUD, registrationCRUD.getIgnoreWarnings() ? errorIssues : allIssues);
	}

	private static Person convertPerson(PersonCRUDTO personCRUD) {
		Person person = new Person();
		person.setEntityKey(personCRUD.getId());
		person.setFirstName(personCRUD.getFirstName());
		person.setLastName(personCRUD.getLastName());
		person.setMiddleName(personCRUD.getMiddleName());
		person.setBirthdate(personCRUD.getBirthdate());
		AddressTO addressTO = personCRUD.getAddress();
		if (addressTO != null) {
			Address address = new Address();
			person.setAddress(address);
			address.setCountry(addressTO.getCountry());
			address.setCity(addressTO.getCity());
			address.setStreet(addressTO.getStreet());
			address.setHouse(addressTO.getHouse());
			address.setHousing(addressTO.getHousing());
			address.setFlat(addressTO.getFlat());
			address.setZipCode(addressTO.getZipCode());
		}
		DocumentTO documentTO = personCRUD.getDocument();
		if (documentTO != null) {
			Document document = new Document();
			person.setDocument(document);
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
		ContactDetailsTO contactDetails = personCRUD.getContactDetails();
		if (contactDetails != null) {
			person.setPhone(contactDetails.getPhone());
			person.setEmail(contactDetails.getEmail());
		}
		return person;
	}
	
	private long getIdForRegistration(Registration registration) {
		return registrationDao.findMaxRegistrationId(registration) + 1L;
	}

	@Override 
	@Transactional
	public List<RegistrationPeriodTO> getActiveRegistrationPeriods(Long customerKey) {
		return registrationPeriodDao.findActivePeriodsForCustomer(customerKey).stream()
				.map(this::convertPeriod)
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public List<RegistrationPeriodTO> getRegistrationPeriodList(Long customerKey) {
		return registrationPeriodDao.fetchList(customerKey).stream()
				.map(this::convertPeriod)
				.collect(Collectors.toList());
	}

	private RegistrationPeriodTO convertPeriod(RegistrationPeriod entity) {
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
			if (spec.getExamSubjectExpression() != null) {
				specTo.setExamSubjectsRule(spec.getExamSubjectExpression());
				specTo.setExamSubjects(parser.parse(spec.getExamSubjectExpression()).getList());
			}
			return specTo;
		}).collect(Collectors.toList()));
		return to;
	}
	
	@Transactional
	@Override
	public RegistrationCRUDTO fetchDetails(Long id) {
		Registration entity = registrationDao.findOne(id);
		if (entity == null) {
			return null;
		}
		RegistrationCRUDTO to = new RegistrationCRUDTO();
		to.setId(entity.getEntityKey());
		to.setFundsSource(entity.getFundsSource().value);
		to.setEducationBase(entity.getEducationBase().value);
		to.setEducationForm(entity.getEducationForm().value);
		to.setRegistrationId(entity.getRegistrationId());
		to.setEducationInstitutionId(entity.getInstitution().getEntityKey());
		to.setSpecialtyId(entity.getSpecialty().getEntityKey());
		to.setEnrolleeAsPayer(entity.isEnrolleeAsPayer());
		to.setEnrollee(convertPerson(entity.getEnrollee()));
		to.setPayer(convertPerson(entity.getPayer()));
		return to;
	}
	
	private static PersonCRUDTO convertPerson(Person entity) {
		if (entity == null) {
			return null;
		}
		PersonCRUDTO to = new PersonCRUDTO();
		to.setId(entity.getEntityKey());
		to.setFirstName(entity.getFirstName());
		to.setLastName(entity.getLastName());
		to.setMiddleName(entity.getMiddleName());
		to.setBirthdate(entity.getBirthdate());
		to.setAddress(converAddress(entity.getAddress()));
		to.setDocument(convertDocument(entity.getDocument()));
		to.setContactDetails(convertContactDetails(entity));
		return to;
	}

	private static ContactDetailsTO convertContactDetails(Person entity) {
		ContactDetailsTO to = new ContactDetailsTO();
		to.setEmail(entity.getEmail());
		to.setPhone(entity.getPhone());
		return to;
	}

	private static DocumentTO convertDocument(Document entity) {
		if (entity == null) {
			return null;
		}
		DocumentTO to = new DocumentTO();
		to.setId(entity.getId());
		to.setDocumentId(entity.getDocumentId());
		to.setIssueDate(entity.getIssueDate());
		to.setIssuedBy(entity.getIssuedBy());
		to.setCitizenship(entity.getCitizenhip());
		to.setType(entity.getType().value);
		return to;
	}

	private static AddressTO converAddress(Address address) {
		if (address == null) {
			return null;
		}
		AddressTO to = new AddressTO();
		to.setCountry(address.getCountry());
		to.setCity(address.getCity());
		to.setStreet(address.getStreet());
		to.setHouse(address.getHouse());
		to.setHousing(address.getHousing());
		to.setFlat(address.getFlat());
		to.setZipCode(address.getZipCode());
		return to;
	}
}
