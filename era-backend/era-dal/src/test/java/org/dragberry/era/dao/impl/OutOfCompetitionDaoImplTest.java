package org.dragberry.era.dao.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dragberry.era.application.DummyDataBean;
import org.dragberry.era.application.config.DataConfigTest;
import org.dragberry.era.dao.OutOfCompetitionDao;
import org.dragberry.era.domain.OutOfCompetition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfigTest.class})
public class OutOfCompetitionDaoImplTest extends DummyDataBean {

	@Autowired
	private OutOfCompetitionDao outOfCompetitionDao;
	
	@Test
	public void testFetchActiveBenefits() {
		outOfCompetitionDao.fetchActiveBenefits().forEach(b -> assertFalse("Deleted OutOfCompetition in the list: " + b.getEntityKey(), b.getDeleted()));
	}
	
	@Test
	public void testFetchPrerogativesByKeysEmtyList() {
		List<OutOfCompetition> oocs = outOfCompetitionDao.fetchByKeys(Collections.emptyList());
		assertEquals(0, oocs.size());
	}
	
	@Test
	public void testFetchPrerogativesByKeys() {
		List<OutOfCompetition> oocs = outOfCompetitionDao.fetchByKeys(Arrays.asList(1000L));
		assertEquals(1, oocs.size());
	}
	
}
