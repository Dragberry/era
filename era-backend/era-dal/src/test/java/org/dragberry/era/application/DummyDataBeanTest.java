package org.dragberry.era.application;

import org.dragberry.era.application.config.DataConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Commit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfigTest.class})
public class DummyDataBeanTest {

	@Autowired
	private DummyDataBean dummyDataBean;
	
	@Test
	public void test() {
		dummyDataBean.createTestData();
	}
}
