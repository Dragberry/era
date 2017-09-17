package org.dragberry.era.dao.impl;

import static org.junit.Assert.*;

import org.dragberry.era.application.DummyDataBean;
import org.dragberry.era.application.config.DataConfigTest;
import org.dragberry.era.dao.BenefitDao;
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
	
}
