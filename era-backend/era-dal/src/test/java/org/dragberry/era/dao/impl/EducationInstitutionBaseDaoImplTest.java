package org.dragberry.era.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.dragberry.era.application.config.DataConfigTest;
import org.dragberry.era.dao.EducationInstitutionBaseDao;
import org.dragberry.era.domain.EducationInstitutionBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfigTest.class})
public class EducationInstitutionBaseDaoImplTest {

	@Autowired
	private EducationInstitutionBaseDao educationInstitutionBaseDao;
	
	@Test
	public void testFindByNameAndCountry_BY() {
		List<EducationInstitutionBase> result = educationInstitutionBaseDao
				.findByNameAndCountry("СШ", "BY", 5);
		assertNotEquals(0, result.size());
	}
	
	@Test
	public void testFindByNameAndCountry_BY_99() {
		List<EducationInstitutionBase> result = educationInstitutionBaseDao
				.findByNameAndCountry("99", "BY", 5);
		assertEquals(2, result.size());
	}
	
	@Test
	public void testFindByNameAndCountry_BY_maxLimit() {
		List<EducationInstitutionBase> result = educationInstitutionBaseDao
				.findByNameAndCountry("минск", "BY", 5);
		assertEquals(5, result.size());
	}

}
