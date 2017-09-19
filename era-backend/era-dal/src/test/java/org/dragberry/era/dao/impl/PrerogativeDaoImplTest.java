package org.dragberry.era.dao.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dragberry.era.application.DummyDataBean;
import org.dragberry.era.application.config.DataConfigTest;
import org.dragberry.era.dao.PrerogativeDao;
import org.dragberry.era.domain.Prerogative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfigTest.class})
public class PrerogativeDaoImplTest extends DummyDataBean {

	@Autowired
	private PrerogativeDao prerogativeDao;
	
	@Test
	public void testFetchActiveBenefits() {
		prerogativeDao.fetchActiveBenefits().forEach(b -> assertFalse("Deleted prerogative in the list: " + b.getEntityKey(), b.getDeleted()));
	}
	
	@Test
	public void testFetchPrerogativesByKeysEmtyList() {
		List<Prerogative> prerogatives = prerogativeDao.fetchByKeys(Collections.emptyList());
		assertEquals(0, prerogatives.size());
	}
	
	@Test
	public void testFetchPrerogativesByKeys() {
		List<Prerogative> prerogatives = prerogativeDao.fetchByKeys(Arrays.asList(1000L, 1001L, 1003L));
		assertEquals(3, prerogatives.size());
	}
	
}
