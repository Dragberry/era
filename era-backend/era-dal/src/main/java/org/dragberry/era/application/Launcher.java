package org.dragberry.era.application;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.dragberry.era.application.config.DataConfig;
import org.dragberry.era.dao.CertificateDao;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.dao.EnrolleeDao;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.dao.SpecialtyDao;
import org.dragberry.era.dao.SubjectDao;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Certificate;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.EducationInstitution;
import org.dragberry.era.domain.Enrollee;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.domain.RegistrationPeriod.Status;
import org.dragberry.era.domain.Subject;
import org.dragberry.era.domain.UserAccount;
import org.dragberry.era.domain.Document.Type;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Launcher {
	
	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class)) {
			EnrolleeDao enrolleeDao = context.getBean(EnrolleeDao.class);
			CertificateDao certificateDao = context.getBean(CertificateDao.class);
			SubjectDao subjectDao = context.getBean(SubjectDao.class);
			SpecialtyDao specialityDao = context.getBean(SpecialtyDao.class);
			UserAccountDao userAccountDao = context.getBean(UserAccountDao.class);
			EducationInstitutionDao einstitutionDao = context.getBean(EducationInstitutionDao.class);
			RegistrationPeriodDao registrationPeriodDao = context.getBean(RegistrationPeriodDao.class);
			RegistrationDao registrationDao = context.getBean(RegistrationDao.class);
			
			
			first(enrolleeDao, certificateDao, subjectDao, specialityDao, userAccountDao, einstitutionDao,
					registrationPeriodDao, registrationDao);
			second(enrolleeDao, certificateDao, subjectDao, specialityDao, userAccountDao, einstitutionDao,
					registrationPeriodDao, registrationDao);
			third(enrolleeDao, certificateDao, subjectDao, specialityDao, userAccountDao, einstitutionDao,
					registrationPeriodDao, registrationDao);
		}
	}
	
	private static void third(EnrolleeDao enrolleeDao, CertificateDao certificateDao, SubjectDao subjectDao,
			SpecialtyDao specialityDao, UserAccountDao userAccountDao, EducationInstitutionDao einstitutionDao,
			RegistrationPeriodDao registrationPeriodDao, RegistrationDao registrationDao) {
		Enrollee enr = new Enrollee();
		enr.setFirstName("Сергеев");
		enr.setMiddleName("Сергеевич");
		enr.setLastName("Рогатов");
		enr.setBirthdate(LocalDate.of(1991, Month.FEBRUARY, 4));
		enr.setPhone("+375293120855");
		enr.setEmail("max-hellfire@mail.ru");
		
		Document doc = new Document();
		doc.setType(Type.PASSPORT);
		doc.setIssueDate(LocalDate.of(2016, Month.JANUARY, 6));
		doc.setIssuedBy("Ленинское РУВД г.Минска");
		doc.setId("25222222");
		doc.setDocumentId("MP2179693");
		enr.setDocument(doc);
		
		Address addr = new Address();
		addr.setCity("Минск");
		addr.setCountry("BY");
		addr.setFlat("161");
		addr.setHouse("60");
		addr.setStreet("Прушинских");
		addr.setZipCode("220112");
		enr.setAddress(addr);
		enr = enrolleeDao.create(enr);
		
		Certificate certificate = new Certificate();
		certificate.setInstitution("СШ №777 г.Минска");
		certificate.setYear(2006);
		certificate.setEnrollee(enr);
		
		Map<Subject, Integer> marks;
		Random random = new Random();
		marks = subjectDao.fetchList().stream().collect(Collectors.toMap(subject -> subject, subject -> Math.abs(random.nextInt() % 11)));
		certificate.setMarks(marks);
		certificateDao.create(certificate);
		
		UserAccount registeredBy = userAccountDao.findOne(1002L);
		
		EducationInstitution eInstitution = einstitutionDao.findOne(1001l);
		
		RegistrationPeriod period = new RegistrationPeriod();
		period.setEducationInstitution(eInstitution);
		period.setFrom(LocalDate.of(2017, Month.JUNE, 1));
		period.setTo(LocalDate.of(2017, Month.DECEMBER, 1));
		period.setTitle("2017/2018 Учебный год");
		period.setStatus(Status.OPENED);
		period.setSpecialties(specialityDao.fetchList());
		registrationPeriodDao.create(period);
		
		Registration registration = new Registration();
		registration.setCertificate(certificate);
		registration.setEnrollee(enr);
		registration.setInstitution(eInstitution);
		registration.setRegisteredBy(registeredBy);
		registration.setRegistrationDate(LocalDate.now());
		registration.setSpecialty(specialityDao.findOne(1000L));
		registration.setRegistrationPeriod(period);
		registration.setType(Registration.Type.BUDGET);
		registrationDao.create(registration);
	}
	
	private static void second(EnrolleeDao enrolleeDao, CertificateDao certificateDao, SubjectDao subjectDao,
			SpecialtyDao specialityDao, UserAccountDao userAccountDao, EducationInstitutionDao einstitutionDao,
			RegistrationPeriodDao registrationPeriodDao, RegistrationDao registrationDao) {
		Enrollee enr = new Enrollee();
		enr.setFirstName("Иван");
		enr.setMiddleName("Иванович");
		enr.setLastName("Иванов");
		enr.setBirthdate(LocalDate.of(1991, Month.FEBRUARY, 4));
		enr.setPhone("+375293120855");
		enr.setEmail("max-hellfire@mail.ru");
		
		Document doc = new Document();
		doc.setType(Type.PASSPORT);
		doc.setIssueDate(LocalDate.of(2016, Month.JANUARY, 6));
		doc.setIssuedBy("Ленинское РУВД г.Минска");
		doc.setId("1112223");
		doc.setDocumentId("MP2149093");
		enr.setDocument(doc);
		
		Address addr = new Address();
		addr.setCity("Минск");
		addr.setCountry("BY");
		addr.setFlat("161");
		addr.setHouse("60");
		addr.setStreet("Прушинских");
		addr.setZipCode("220112");
		enr.setAddress(addr);
		enr = enrolleeDao.create(enr);
		
		Certificate certificate = new Certificate();
		certificate.setInstitution("СШ №54 г.Минска");
		certificate.setYear(2006);
		certificate.setEnrollee(enr);
		
		Map<Subject, Integer> marks;
		Random random = new Random();
		marks = subjectDao.fetchList().stream().collect(Collectors.toMap(subject -> subject, subject -> Math.abs(random.nextInt() % 11)));
		certificate.setMarks(marks);
		certificateDao.create(certificate);
		
		UserAccount registeredBy = userAccountDao.findOne(1000L);
		
		EducationInstitution eInstitution = einstitutionDao.findOne(1000l);
		
		RegistrationPeriod period = registrationPeriodDao.findOne(1000l);
		
		Registration registration = new Registration();
		registration.setCertificate(certificate);
		registration.setEnrollee(enr);
		registration.setInstitution(eInstitution);
		registration.setRegisteredBy(registeredBy);
		registration.setRegistrationDate(LocalDate.now());
		registration.setSpecialty(specialityDao.findOne(1000L));
		registration.setRegistrationPeriod(period);
		registration.setType(Registration.Type.BUDGET);
		registrationDao.create(registration);
	}

	private static void first(EnrolleeDao enrolleeDao, CertificateDao certificateDao, SubjectDao subjectDao,
			SpecialtyDao specialityDao, UserAccountDao userAccountDao, EducationInstitutionDao einstitutionDao,
			RegistrationPeriodDao registrationPeriodDao, RegistrationDao registrationDao) {
		Enrollee enr = new Enrollee();
		enr.setFirstName("Максим");
		enr.setMiddleName("Леонидович");
		enr.setLastName("Драгун");
		enr.setBirthdate(LocalDate.of(1991, Month.FEBRUARY, 4));
		enr.setPhone("+375293120855");
		enr.setEmail("max-hellfire@mail.ru");
		
		Document doc = new Document();
		doc.setType(Type.PASSPORT);
		doc.setIssueDate(LocalDate.of(2016, Month.JANUARY, 6));
		doc.setIssuedBy("Ленинское РУВД г.Минска");
		doc.setId("22222222");
		doc.setDocumentId("MP2179093");
		enr.setDocument(doc);
		
		Address addr = new Address();
		addr.setCity("Минск");
		addr.setCountry("BY");
		addr.setFlat("161");
		addr.setHouse("60");
		addr.setStreet("Прушинских");
		addr.setZipCode("220112");
		enr.setAddress(addr);
		enr = enrolleeDao.create(enr);
		
		Certificate certificate = new Certificate();
		certificate.setInstitution("СШ №143 г.Минска");
		certificate.setYear(2006);
		certificate.setEnrollee(enr);
		
		Map<Subject, Integer> marks;
		Random random = new Random();
		marks = subjectDao.fetchList().stream().collect(Collectors.toMap(subject -> subject, subject -> Math.abs(random.nextInt() % 11)));
		certificate.setMarks(marks);
		certificateDao.create(certificate);
		
		UserAccount registeredBy = userAccountDao.findOne(1000L);
		
		EducationInstitution eInstitution = einstitutionDao.findOne(1000l);
		
		RegistrationPeriod period = new RegistrationPeriod();
		period.setEducationInstitution(eInstitution);
		period.setFrom(LocalDate.of(2017, Month.JUNE, 1));
		period.setTo(LocalDate.of(2017, Month.DECEMBER, 1));
		period.setTitle("2017/2018 Учебный год");
		period.setStatus(Status.OPENED);
		period.setSpecialties(specialityDao.fetchList());
		registrationPeriodDao.create(period);
		
		Registration registration = new Registration();
		registration.setCertificate(certificate);
		registration.setEnrollee(enr);
		registration.setInstitution(eInstitution);
		registration.setRegisteredBy(registeredBy);
		registration.setRegistrationDate(LocalDate.now());
		registration.setSpecialty(specialityDao.findOne(1000L));
		registration.setRegistrationPeriod(period);
		registration.setType(Registration.Type.BUDGET);
		registrationDao.create(registration);
	}
	
}