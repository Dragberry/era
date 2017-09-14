package org.dragberry.era.application;

import org.dragberry.era.application.config.DataConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfigTest.class})
public class LauncherTest {
	
	@Test
	public void init() {
		Launcher.init(DataConfigTest.class);
	}

}
