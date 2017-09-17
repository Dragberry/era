package org.dragberry.era.business.registration.validation;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.dragberry.era.common.IssueTO;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.EducationBase;
import org.dragberry.era.domain.EducationForm;
import org.dragberry.era.domain.EducationInstitution;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.RegisteredSpecialty;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.domain.RegistrationPeriod.Status;
import org.dragberry.era.domain.Specialty;
import org.dragberry.era.security.AccessControl;
import org.dragberry.era.security.JwtUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationValidatorTest {
	
	@Mock
	private AccessControl accessControl;
	@Mock
	private RegistrationPeriodDao registrationPeriodDao;
	
	@InjectMocks
	private RegistrationValidator validator = new RegistrationValidator();
	
	private JwtUser loggedUser;
	private Registration registration;
	
	@Before
	public void before() {
		loggedUser = new JwtUser(1000L, 1000L, "user", "user", "user", "email", "password", null, true, LocalDateTime.now());
		registration = new Registration();
	}
	
	private Registration registration(RegistrationPeriod period, Long institutionKey, Long specialtyKey, EducationBase base, EducationForm form, FundsSource source) {
		if (period != null) {
			registration.setRegistrationPeriod(period);
		}
		if (institutionKey != null) {
			EducationInstitution institution = new EducationInstitution();
			institution.setEntityKey(institutionKey);
			registration.setInstitution(institution);
		}
		if (specialtyKey != null) {
			Specialty spec = new Specialty();
			spec.setEntityKey(specialtyKey);
			registration.setSpecialty(spec);
		}
		registration.setEducationBase(base);
		registration.setEducationForm(form);
		registration.setFundsSource(source);
		return registration;
	}
	
	private RegistrationPeriod period(Long periodKey, Long institutionKey, Long specialtyKey, Set<EducationBase> bases, Set<EducationForm> forms, Set<FundsSource> sources) {
		RegistrationPeriod period = new RegistrationPeriod();
		period.setEntityKey(periodKey);
		period.setStatus(Status.OPENED);
		EducationInstitution inst = new EducationInstitution();
		inst.setEntityKey(institutionKey);
		period.setEducationInstitution(inst);
		RegisteredSpecialty regSpec = new RegisteredSpecialty();
		regSpec.setEducationBases(bases);
		regSpec.setEducationForms(forms);
		regSpec.setFundsSources(sources);
		Specialty spec = new Specialty();
		spec.setEntityKey(specialtyKey);
		regSpec.setSpecialty(spec);
		period.setSpecialties(Arrays.asList(regSpec));
		return period;
	}
	
	private List<RegistrationPeriod> periods(Long periodKey, Long institutionKey, Long specialtyKey, Set<EducationBase> bases, Set<EducationForm> forms, Set<FundsSource> sources) {
		return Arrays.asList(period(periodKey, institutionKey, specialtyKey, bases, forms, sources));
	}
	
	private List<RegistrationPeriod> periods(RegistrationPeriod period) {
		return Arrays.asList(period);
	}

	/**
	 * Period with specialty: FULL_TIME, BUDGET, L9; EI = 1000, Specialty = 1000
	 * Registration for FULL_TIME, BUDGET, L9, EI = 1000, Specialty = 1000
	 */
	@Test
	public void testValidateSuccessful() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L))
			.thenReturn(periods(period));
		
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 0, result.size());
	}
	
	@Test
	public void testValidateNoInstitution() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		List<IssueTO> result = validator.validate(registration(period, null, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-institution-is-empty", result.get(0).getErrorCode());
		assertEquals("educationInstitution", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoPeriod() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L))
			.thenReturn(periods(period));
		List<IssueTO> result = validator.validate(registration(null, 1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.no-registration-period", result.get(0).getErrorCode());
		assertNull(result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidatePeriodIsNotOpened() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		period.setStatus(Status.CLOSED);
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L))
			.thenReturn(periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.registration-period-is-not-opened", result.get(0).getErrorCode());
		assertNull(result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateWrongPeriod() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L))
			.thenReturn(Collections.emptyList());
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.registration-period-is-wrong", result.get(0).getErrorCode());
		assertNull(result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateMismatchInstitution() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1001L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-institution-is-wrong", result.get(0).getErrorCode());
		assertEquals("educationInstitution", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	

	@Test
	public void testValidateNoSpecialty() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, null, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.specialty-is-empty", result.get(0).getErrorCode());
		assertEquals("specialty", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateSpecialtyMismatch() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1001L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.specialty-is-wrong", result.get(0).getErrorCode());
		assertEquals("specialty", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoFundsSource() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, null));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.funds-source-is-empty", result.get(0).getErrorCode());
		assertEquals("fundsSource", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateInvalidFundsSource() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.of(FundsSource.BUDGET));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.PAYER));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.funds-source-is-not-available", result.get(0).getErrorCode());
		assertEquals("fundsSource", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoEducationForm() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, null, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-form-is-empty", result.get(0).getErrorCode());
		assertEquals("educationForm", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateInvalidEducationForm() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.of(EducationForm.FULL_TIME), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, EducationForm.EXTRAMURAL, FundsSource.PAYER));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-form-is-not-available", result.get(0).getErrorCode());
		assertEquals("educationForm", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoEducationBase() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, null, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-base-is-empty", result.get(0).getErrorCode());
		assertEquals("educationBase", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateInvalidEducationBase() {
		RegistrationPeriod period = period(1000l, 1000l, 1000l, EnumSet.of(EducationBase.L11), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class));
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodsForCustomer(1000L)).thenReturn(
				periods(period));
		List<IssueTO> result = validator.validate(registration(period, 1000L, 1000L, EducationBase.L9, EducationForm.EXTRAMURAL, FundsSource.PAYER));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-base-is-not-available", result.get(0).getErrorCode());
		assertEquals("educationBase", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}

}
