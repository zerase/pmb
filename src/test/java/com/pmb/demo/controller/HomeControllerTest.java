package com.pmb.demo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pmb.demo.repository.UserAccountRepository;
import com.pmb.demo.service.UserAccountService;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		
		userAccountService.saveNewUserAccount("John", "Doe", "john@test.com", "testpwd");
	}

	@AfterEach
	void tearDown() throws Exception {
		userAccountRepository.deleteAll();
	}

	@DisplayName("Display Home view with anonymous user")
	@Test
	@WithAnonymousUser
	void testShowHomeView_shouldReturnRedirectionToLoginView_whenUserIsAnonymous() throws Exception {
		mockMvc.perform(get("/home"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}

	@DisplayName("Display Home view with authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowHomeView_shouldReturnIsOk_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk());
	}
	
	@DisplayName("Display Home view with data content of authenticated user")
	@Test
	@WithMockUser("john@test.com")
	void testShowHomeView_shouldReturnUserData_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"))
			.andExpect(model().attributeExists("username", "userbalance", "userbank"))
			.andExpect(model().attribute("username", "John Doe"));
	}

}
