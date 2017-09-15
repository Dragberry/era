package org.dragberry.era.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.dragberry.era.application.DummyDataBeanTest;
import org.dragberry.era.application.config.DataConfigTest;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.Benefit;
import org.dragberry.era.domain.EducationBase;
import org.dragberry.era.domain.EducationForm;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.OutOfCompetition;
import org.dragberry.era.domain.Prerogative;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.RegistrationPeriod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfigTest.class})
public class RegistrationDaoImplTest extends DummyDataBeanTest {
	
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	@Autowired
	private RegistrationDao registrationDao;

	/**
	 * Registration: FULL TIME, BUDGET, L9 Specialty = 1000, Period = 1000
	 * Settings: separate by form, source and base 
	 * Database: 3 records for FULL TIME, BUDGET, L9 Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_00() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		reg.setEducationBase(EducationBase.L9);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(3L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL TIME, BUDGET, L11, Specialty = 1000, Period = 1000
	 * Settings: separate by form, base and source
	 * Database: 3 records for FULL TIME, BUDGET, L11, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_01() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		reg.setEducationBase(EducationBase.L11);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(3L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL TIME, PAYER, L9, Specialty = 1000, Period = 1000
	 * Settings: separate by form, base and source
	 * Database: 3 records for FULL TIME, PAYER, L9, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_02() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.PAYER);
		reg.setEducationBase(EducationBase.L9);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(3L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: EXTRAMURAL, BUDGET, L9, Specialty = 1000, Period = 1000
	 * Settings: separate by form, base and source
	 * Database: 3 records for EXTRAMURAL, BUDGET, L9, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_03() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.EXTRAMURAL);
		reg.setFundsSource(FundsSource.BUDGET);
		reg.setEducationBase(EducationBase.L9);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(3L, registrationDao.findMaxRegistrationId(reg));
	}

	/**
	 * Registration: EXTRAMURAL, PAYER, L9, Specialty = 1000, Period = 1000
	 * Settings: separate by form, base and source
	 * Database: 3 records for EXTRAMURAL, PAYER, L9, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_04() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.EXTRAMURAL);
		reg.setFundsSource(FundsSource.PAYER);
		reg.setEducationBase(EducationBase.L9);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(3L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: EXTRAMURAL, PAYER, L11, Specialty = 1000, Period = 1000
	 * Settings: separate by form, base and source
	 * Database: 3 records for EXTRAMURAL, PAYER, L11, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_05() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.EXTRAMURAL);
		reg.setFundsSource(FundsSource.PAYER);
		reg.setEducationBase(EducationBase.L11);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(3L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL_TIME, BUDGET, L9, Specialty = 1001, Period = 1000
	 * Settings: separate by form, base
	 * Database: 6 records for FULL_TIME, BUDGET and PAYER, L9, Specialty = 1001, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_06() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		reg.setEducationBase(EducationBase.L9);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(1).getSpecialty());
		assertEquals(6L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL_TIME, PAYER, L11, Specialty = 1001, Period = 1000
	 * Settings: separate by form, base
	 * Database: 6 records for FULL_TIME, BUDGET and PAYER, L11, Specialty = 1001, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_07() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.PAYER);
		reg.setEducationBase(EducationBase.L11);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(1).getSpecialty());
		assertEquals(6L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL_TIME, BUDGET, L9, Specialty = 1002, Period = 1000
	 * Settings: separate by base and funds
	 * Database: no records for Specialty = 1002, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_08() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		reg.setEducationBase(EducationBase.L9);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(2).getSpecialty());
		assertEquals(0L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL_TIME, BUDGET, L9, Specialty = 1005, Period = 1000
	 * Settings: separate by nothing
	 * Database: 24 records for Specialty = 1005, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_09() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		reg.setEducationBase(EducationBase.L9);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(5).getSpecialty());
		assertEquals(24L, registrationDao.findMaxRegistrationId(reg));
	}
	
	@Test
	@Transactional
	public void testGetBenefits1_0() {
		Registration reg = registrationDao.findOne(1000L);
		List<Benefit> benefits = reg.getBenefits();
		assertEquals(1, benefits.size());
		assertEquals(Prerogative.class, benefits.get(0).getClass());
	}
	
	@Test
	@Transactional
	public void testGetBenefits2_0() {
		Registration reg = registrationDao.findOne(1001L);
		List<Benefit> benefits = reg.getBenefits();
		assertEquals(2, benefits.size());
		assertEquals(Prerogative.class, benefits.get(0).getClass());
		assertEquals(Prerogative.class, benefits.get(1).getClass());
	}
	
	@Test
	@Transactional
	public void testGetBenefits2() {
		Registration reg = registrationDao.findOne(1002L);
		List<Benefit> benefits = reg.getBenefits();
		assertEquals(1, benefits.size());
		assertEquals(OutOfCompetition.class, benefits.get(0).getClass());
	}
	
	@Test
	@Transactional
	public void testGetBenefits1_1() {
		Registration reg = registrationDao.findOne(1003L);
		List<Benefit> benefits = reg.getBenefits();
		assertEquals(2, benefits.size());
		assertEquals(Prerogative.class, benefits.get(0).getClass());
		assertEquals(OutOfCompetition.class, benefits.get(1).getClass());
	}
}
