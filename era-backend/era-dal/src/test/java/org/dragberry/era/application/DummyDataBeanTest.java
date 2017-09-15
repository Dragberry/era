package org.dragberry.era.application;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Commit
public class DummyDataBeanTest {

	@Autowired
	private DummyDataBean dummyDataBean;
	
	@Before
	public void setupBefore() {
		dummyDataBean.createTestData();
	}
}
