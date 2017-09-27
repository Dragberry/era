package org.dragberry.era.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import org.dragberry.era.application.config.SecurityConfig;
import org.dragberry.era.application.config.TestContext;
import org.dragberry.era.application.config.WebConfig;
import org.dragberry.era.business.institution.EducationInstitutionService;
import org.dragberry.era.common.institution.EducationInstitutionBaseTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebConfig.class, SecurityConfig.class })
@WebAppConfiguration
public class EducationInstitutionControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private EducationInstitutionService service;
	@Autowired
	private EducationInstitutionController controller;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testLookup() throws Exception {
		String name = "Name";
		String country = "BY";
		Integer maxSize = 5;
		EducationInstitutionBaseTO base = new EducationInstitutionBaseTO();
		base.setId(1L);
		base.setName("Name");
		Mockito.when(service.lookup(name, country, maxSize)).thenReturn(Arrays.asList(base));

		mockMvc.perform(get("/api/education-institution/lookup")
				.param("name", name)
				.param("country", country)
				.param("max-size", String.valueOf(5)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.issues", hasSize(0)))
			.andExpect(jsonPath("$.value", notNullValue()))
			.andExpect(jsonPath("$.value", hasSize(1)))
			.andExpect(jsonPath("$.value[0].name", is("Name")))
			.andExpect(jsonPath("$.value[0].id", is(1)));
		
		verify(service, times(1)).lookup(name, country, maxSize);
		
	}

}
