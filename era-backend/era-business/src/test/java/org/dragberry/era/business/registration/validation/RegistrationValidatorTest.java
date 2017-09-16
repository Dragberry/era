package org.dragberry.era.business.registration.validation;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
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
	private RegistrationPeriod period;
	
	@Before
	public void before() {
		loggedUser = new JwtUser(1000L, 1000L, "user", "user", "user", "email", "password", null, true, LocalDateTime.now());
		registration = new Registration();
		period = new RegistrationPeriod();
	}
	
	private Registration registration(Long institutionKey, Long specialtyKey, EducationBase base, EducationForm form, FundsSource source) {
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
	
	private RegistrationPeriod period(Long institutionKey, Long specialtyKey, Set<EducationBase> bases, Set<EducationForm> forms, Set<FundsSource> sources) {
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

	/**
	 * Period with specialty: FULL_TIME, BUDGET, L9; EI = 1000, Specialty = 1000
	 * Registration for FULL_TIME, BUDGET, L9, EI = 1000, Specialty = 1000
	 */
	@Test
	public void testValidateSuccessful() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L))
			.thenReturn(period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 0, result.size());
	}
	
	@Test
	public void testValidateNoInstitution() {
		List<IssueTO> result = validator.validate(registration(null, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-institution-is-empty", result.get(0).getErrorCode());
		assertEquals("educationInstitution", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoActivePeriod() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L))
			.thenReturn(null);
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.no-active-registration-period", result.get(0).getErrorCode());
		assertNull(result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateMismatchInstitution() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1001L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-institution-is-wrong", result.get(0).getErrorCode());
		assertEquals("educationInstitution", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	

	@Test
	public void testValidateNoSpecialty() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1000L, null, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.specialty-is-empty", result.get(0).getErrorCode());
		assertEquals("specialty", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateSpecialtyMismatch() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1000L, 1001L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.specialty-is-wrong", result.get(0).getErrorCode());
		assertEquals("specialty", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoFundsSource() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, null));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.funds-source-is-empty", result.get(0).getErrorCode());
		assertEquals("fundsSource", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateInvalidFundsSource() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.of(FundsSource.BUDGET)));
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, EducationBase.L9, EducationForm.FULL_TIME, FundsSource.PAYER));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.funds-source-is-not-available", result.get(0).getErrorCode());
		assertEquals("fundsSource", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoEducationForm() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, EducationBase.L9, null, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-form-is-empty", result.get(0).getErrorCode());
		assertEquals("educationForm", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateInvalidEducationForm() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.of(EducationForm.FULL_TIME), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, EducationBase.L9, EducationForm.EXTRAMURAL, FundsSource.PAYER));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-form-is-not-available", result.get(0).getErrorCode());
		assertEquals("educationForm", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateNoEducationBase() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.allOf(EducationBase.class), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, null, EducationForm.FULL_TIME, FundsSource.BUDGET));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-base-is-empty", result.get(0).getErrorCode());
		assertEquals("educationBase", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}
	
	@Test
	public void testValidateInvalidEducationBase() {
		Mockito.when(accessControl.getLoggedUser()).thenReturn(loggedUser);
		Mockito.when(registrationPeriodDao.findActivePeriodForCustomer(1000L)).thenReturn(
				period(1000l, 1000l, EnumSet.of(EducationBase.L11), EnumSet.allOf(EducationForm.class), EnumSet.allOf(FundsSource.class)));
		List<IssueTO> result = validator.validate(registration(1000L, 1000L, EducationBase.L9, EducationForm.EXTRAMURAL, FundsSource.PAYER));
		assertEquals(result.toString(), 1, result.size());
		assertEquals("validation.registration.education-base-is-not-available", result.get(0).getErrorCode());
		assertEquals("educationBase", result.get(0).getFieldId());
		assertEquals(0, result.get(0).getParams().size());
	}

}
