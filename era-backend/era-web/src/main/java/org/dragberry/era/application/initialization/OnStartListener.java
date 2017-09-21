package org.dragberry.era.application.initialization;

import org.dragberry.era.application.DummyDataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OnStartListener {
	
	@Autowired
	private DummyDataBean dummyDataBean;

	@EventListener(classes = {ContextRefreshedEvent.class})
	public void onStart() {
		dummyDataBean.createTestData();
	}
}
