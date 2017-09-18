package org.dragberry.era.dao.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dragberry.era.application.DummyDataBean;
import org.dragberry.era.application.config.DataConfigTest;
import org.dragberry.era.dao.BenefitDao;
import org.dragberry.era.domain.Benefit;
import org.dragberry.era.domain.OutOfCompetition;
import org.dragberry.era.domain.Prerogative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfigTest.class})
public class BenefitDaoImplTest extends DummyDataBean {

	@Autowired
	private BenefitDao benefitDao;
	
	@Test
	public void testFetchActiveBenefits() {
		benefitDao.fetchActiveBenefits().forEach(b -> assertFalse("Deleted benefit in the list: " + b.getEntityKey(), b.getDeleted()));
	}
	
	@Test
	public void testFetchPrerogativesByKeysEmtyList() {
		List<Benefit> prerogatives = benefitDao.fetchBenefits(Prerogative.class, Collections.emptyList());
		assertEquals(0, prerogatives.size());
	}
	
	@Test
	public void testFetchPrerogativesByKeys() {
		List<Benefit> prerogatives = benefitDao.fetchBenefits(Prerogative.class, Arrays.asList(1000L, 1001L, 1003L));
		assertEquals(3, prerogatives.size());
		prerogatives.forEach(b -> {
			assertEquals(Prerogative.class, b.getClass());
		});
	}
	
	@Test
	public void testFetchOutOCompetitionsByKeys() {
		List<Benefit> prerogatives = benefitDao.fetchBenefits(OutOfCompetition.class, Arrays.asList(1007L));
		assertEquals(1, prerogatives.size());
		prerogatives.forEach(b -> {
			assertEquals(OutOfCompetition.class, b.getClass());
		});
	}
}
