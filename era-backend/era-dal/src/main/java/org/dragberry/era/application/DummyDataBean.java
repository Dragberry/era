package org.dragberry.era.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
import org.dragberry.era.domain.EducationInstitution;
import org.dragberry.era.domain.EducationInstitutionBase;
import org.dragberry.era.domain.ExamSubject;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.OutOfCompetition;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Prerogative;
import org.dragberry.era.domain.RegisteredSpecialty;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.domain.Subject;
import org.dragberry.era.domain.UserAccount;
import org.dragberry.era.domain.Document.Type;
import org.dragberry.era.domain.RegistrationPeriod.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DummyDataBean {
	
	@Autowired
	private PersonDao personDao;
	@Autowired
	private CertificateDao certificateDao;
	@Autowired
	private SubjectDao subjectDao;
	@Autowired
	private SpecialtyDao specialityDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private EducationInstitutionDao einstitutionDao;
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private PrerogativeDao prerogativeDao;
	@Autowired
	private OutOfCompetitionDao outOfCompetitionDao;
	@Autowired
	private EducationInstitutionBaseDao educationInstitutionBaseDao;
	@Autowired
	private ExamSubjectDao examSubjectDao;
	
	private UserAccount user1;
	private UserAccount user2;
	private EducationInstitution eInstitution;
	private RegistrationPeriod period;
	
	private Map<String, ExamSubject> examSubjects = new HashMap<>();
	
	@Transactional
	public void createTestData() {
		if (registrationPeriodDao.findOne(1000L) != null) {
			return;
		}
		fetchExamSubjects();
		createBaseInstitutions();
		
		user1 = userAccountDao.findOne(1000L);
		user2 = userAccountDao.findOne(1001L);
		eInstitution = einstitutionDao.findOne(1000l);
		
		period = period(eInstitution);
		registrationPeriodDao.create(period);
		
		// The second period
		registrationPeriodDao.create(period(einstitutionDao.findOne(1001l)));
		
		// All separated
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET,
				prerogativeDao.findOne(1000L));
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET,
				prerogativeDao.findOne(1000L), prerogativeDao.findOne(1001L));
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET,
				outOfCompetitionDao.findOne(1000L));
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER,
				prerogativeDao.findOne(1000L), outOfCompetitionDao.findOne(1000L));
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		
		//separated by form and base
		createRegistration(1, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(1, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		
		//separated by nothing
		createRegistration(1, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(4, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(5, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(6, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(7, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(8, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(9, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(10, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(11, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(12, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(13, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(14, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(15, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(16, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(17, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(18, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(19, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(20, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(21, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(22, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(23, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(24, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		
	}
	
	private void fetchExamSubjects() {
		examSubjects = examSubjectDao.fetchList().stream().collect(Collectors.toMap(ExamSubject::getCode, es -> es));
	}

	private void createRegistration(long id, int specId, EducationForm form, EducationBase base, FundsSource source, Benefit... benefits) {
		Person person = person();
		personDao.create(person);
		Certificate certificate = certificate(person);
		certificateDao.create(certificate);
		Registration registration = registration(id, specId, form, base, source, person, certificate, benefits);
		registrationDao.create(registration);
	}

	private Registration registration(long id, int specId, EducationForm form, EducationBase base, FundsSource source,
			Person enr, Certificate certificate, Benefit... benefits) {
		Registration registration = new Registration();
		registration.setCertificate(certificate);
		registration.setEnrollee(enr);
		registration.setInstitution(eInstitution);
		registration.setRegisteredBy(id % 2 == 0 ? user1 : user2);
		registration.setRegistrationDate(LocalDateTime.now());
		if (id % 3 == 0) {
			registration.setVerifiedBy(id % 2 == 0 ? user2 : user1);
			registration.setStatus(Registration.Status.VERIFIED);
			registration.setVerificationDate(LocalDateTime.now().plusMinutes(45));
		} else {
			registration.setStatus(Registration.Status.NOT_VERIFIED);
		}
		registration.setSpecialty(period.getSpecialties().get(specId).getSpecialty());
		registration.setRegistrationId(id);
		registration.setRegistrationPeriod(period);
		registration.setFundsSource(source);
		registration.setEducationForm(form);
		registration.setEducationBase(base);
		registration.setPrerogatives(Arrays.stream(benefits).filter(b -> b instanceof Prerogative).map(b -> (Prerogative) b).collect(Collectors.toList()));
		registration.setOutOfCompetitions(Arrays.stream(benefits).filter(b -> b instanceof OutOfCompetition).map(b -> (OutOfCompetition) b).collect(Collectors.toList()));
		
		if (registration.getEducationBase() == EducationBase.L11) {
			Map<ExamSubject, Integer> examMarks = new HashMap<>();
			examMarks.put(examSubjects.get(RandomProvider.RANDOM.nextInt() % 2 == 0 ? "01" : "02"), 23);
			if (specId == 1) {
				examMarks.put(examSubjects.get("01"), RandomProvider.RANDOM.nextInt(101));
				examMarks.put(examSubjects.get("03"), RandomProvider.RANDOM.nextInt(101));
			} else {
				examMarks.put(examSubjects.get("05"), RandomProvider.RANDOM.nextInt(101));
				examMarks.put(examSubjects.get("06"), RandomProvider.RANDOM.nextInt(101));
			}
			
			registration.setExamMarks(examMarks);
		}
		
		if (registration.getFundsSource() == FundsSource.PAYER) {
			registration.setPayer(registration.getEnrollee());
			registration.setEnrolleeAsPayer(Boolean.TRUE);
		} else {
			registration.setEnrolleeAsPayer(Boolean.FALSE);
		}
		
		return registration;
	}
	
	private RegistrationPeriod period(EducationInstitution eInstitution) {
		RegistrationPeriod period = new RegistrationPeriod();
		period.setEducationInstitution(eInstitution);
		period.setFrom(LocalDate.of(2017, Month.JUNE, 1));
		period.setTo(LocalDate.of(2017, Month.DECEMBER, 1));
		period.setTitle("2017/2018 учебный год");
		period.setStatus(Status.OPENED);

		period.setSpecialties(specialityDao.fetchList().stream().map(spec -> {
			RegisteredSpecialty rSpec = new RegisteredSpecialty();
			rSpec.setSpecialty(spec);
			rSpec.setRegistrationPeriod(period);
			switch (spec.getEntityKey().intValue()) {
				case 1000:
					rSpec.setSeparateByEducationBase(true);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(true);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					rSpec.setExamSubjectExpression("\"01\" or \"02\" and \"03\" and \"04\"");
					break;
				case 1001:
					rSpec.setSeparateByEducationBase(true);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(false);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					rSpec.setExamSubjectExpression("\"05\" and \"01\" or \"02\" and \"06\"");
					break;
				case 1002:
					rSpec.setSeparateByEducationBase(true);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(false);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(true);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					rSpec.setExamSubjectExpression("\"01\" or \"02\" and \"03\" and \"04\"");
					break;
				case 1003:
					rSpec.setSeparateByEducationBase(false);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(true);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					rSpec.setExamSubjectExpression("\"01\" or \"02\" and \"03\" and \"04\"");
					break;
				case 1004:
					rSpec.setSeparateByEducationBase(false);
					rSpec.setEducationBases(EnumSet.of(EducationBase.L9));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.of(EducationForm.FULL_TIME));
					rSpec.setSeparateByFundsSource(false);
					rSpec.setFundsSources(EnumSet.of(FundsSource.BUDGET));
					rSpec.setExamSubjectExpression("\"01\" or \"02\" and \"03\" and \"04\"");
					break;
				case 1005:
					rSpec.setSeparateByEducationBase(false);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(false);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(false);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					rSpec.setExamSubjectExpression("\"01\" or \"02\" and \"03\" and \"04\"");
					break;
			}
			return rSpec;
		}).collect(Collectors.toList()));
		return period;
	}
	
	private Person person() {
		Person enr = new Person();
		enr.setFirstName(RandomProvider.getName());
		enr.setMiddleName(RandomProvider.getMiddleName());
		enr.setLastName(RandomProvider.getSurname());
		enr.setBirthdate(RandomProvider.getBirthDate());
		enr.setPhone(RandomProvider.getPhone());
		enr.setEmail(RandomProvider.getMail());
		
		Document doc = new Document();
		doc.setCitizenhip("BY");
		doc.setType(Type.PASSPORT);
		doc.setIssueDate(RandomProvider.getDate());
		doc.setIssuedBy(RandomProvider.getRUVD());
		doc.setId(RandomProvider.getString(14));
		doc.setDocumentId(RandomProvider.getString("MP", 7));
		enr.setDocument(doc);
		
		Address addr = new Address();
		addr.setCity(RandomProvider.getCity());
		addr.setCountry("BY");
		addr.setFlat(RandomProvider.getNumber());
		addr.setHouse(RandomProvider.getNumber());
		addr.setStreet(RandomProvider.getStreet());
		addr.setZipCode(RandomProvider.getString(6));
		enr.setAddress(addr);
		return enr;
	}
	
	private Certificate certificate(Person enrollee) {
		Certificate certificate = new Certificate();
		certificate.setInstitution(educationInstitutionBaseDao.findOne(1000L + RandomProvider.RANDOM.nextInt(10)));
		certificate.setYear(RandomProvider.getYear());
		certificate.setEnrollee(enrollee);
		
		Map<Subject, Integer> marks;
		Random random = new Random();
		marks = subjectDao.fetchList().stream().collect(Collectors.toMap(subject -> subject, subject -> Math.abs(random.nextInt() % 11)));
		certificate.setMarks(marks);
		return certificate;
	}
	
	private void createBaseInstitutions() {
		createBase("BY", "ГУО СШ №999 г.Минска");
		createBase("BY", "ГУО СШ №998 г.Минска");
		createBase("BY", "ГУО СШ №988 г.Минска");
		createBase("BY", "ГУО СШ №888 г.Минска");
		createBase("BY", "ГУО СШ №87 г.Могилева");
		createBase("BY", "ГУО СШ №3 г.Бреста");
		createBase("BY", "ГУО СШ №12 г.Минска");
		createBase("BY", "ГУО СШ №143 г.Минска");
		createBase("BY", "ГУО СШ №25 г.Минска");
		createBase("BY", "ГУО СШ №89 г.Минска");
	}

	private void createBase(String country, String name) {
		EducationInstitutionBase base = new EducationInstitutionBase();
		base.setCountry(country);
		base.setName(name);
		educationInstitutionBaseDao.create(base);
	}

}
