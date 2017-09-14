package org.dragberry.era.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.dragberry.era.application.config.DataConfigTest;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.domain.EducationForm;
import org.dragberry.era.domain.FundsSource;
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
public class RegistrationDaoImplTest {
	
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	@Autowired
	private RegistrationDao registrationDao;

	/**
	 * Registration: FULL TIME, BUDGET, Specialty = 1000, Period = 1000
	 * Settings: separate by FULL_TIME, separate by BUDGET 
	 * Database: 1 records for FULL TIME, BUDGET, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_00() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(1L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL TIME, BUDGET, Specialty = 1002, Period = 1000
	 * Settings: separate by FULL_TIME, separate by BUDGET 
	 * Database: 0 records for FULL TIME, BUDGET, Specialty = 1002, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_01() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(2).getSpecialty());
		assertEquals(0L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL TIME, PAID, Specialty = 1000, Period = 1000
	 * Settings: separate by FULL_TIME, separate by BUDGET 
	 * Database: 1 records for FULL TIME, PAID, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_02() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.FULL_TIME);
		reg.setFundsSource(FundsSource.BUDGET);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(1L, registrationDao.findMaxRegistrationId(reg));
	}
	
	/**
	 * Registration: FULL TIME, PAID, Specialty = 1000, Period = 1000
	 * Settings: separate by FULL_TIME, separate by BUDGET 
	 * Database: 1 records for FULL TIME, PAID, Specialty = 1000, Period = 1000
	 */
	@Test
	@Transactional
	public void testFindMaxRegistrationId_03() {
		Registration reg = new Registration();
		reg.setEducationForm(EducationForm.EXTRAMURAL);
		reg.setFundsSource(FundsSource.BUDGET);
		RegistrationPeriod period = registrationPeriodDao.findOne(1000L);
		reg.setRegistrationPeriod(period);
		reg.setSpecialty(period.getSpecialties().get(0).getSpecialty());
		assertEquals(1L, registrationDao.findMaxRegistrationId(reg));
	}

}
