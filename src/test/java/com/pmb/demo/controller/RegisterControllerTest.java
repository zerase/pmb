package com.pmb.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pmb.demo.repository.UserAccountRepository;
import com.pmb.demo.service.UserAccountService;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

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
	}

	@AfterEach
	void tearDown() throws Exception {
		userAccountRepository.deleteAll();
	}

	@Test
	@DisplayName("Display Register view")
	void testShowRegisterView_shouldReturnIsOk_whenRequested() throws Exception {
		mockMvc.perform(get("/register")).andDo(print())
			.andExpect(view().name("register"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("register")));
	}

	@Test
	@DisplayName("Display Register view when user registered successfully")
	void testShowRegisterView_shouldDisplaySuccessMessage_whenUserSucceedToRegister() throws Exception {
		mockMvc.perform(post("/register").param("firstNameForm", "John")
										 .param("lastNameForm", "Doe")
										 .param("emailForm", "john@test.com")
										 .param("passwordForm", "testpwd")).andDo(print())
			.andExpect(view().name("register"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("You've been registered successfully")));
	}
	
	@Test
	@DisplayName("Display Register view when user failed to register")
	void testShowRegisterView_shouldDisplayErrorMessage_whenUserFailedToRegister() throws Exception {
		userAccountService.saveNewUserAccount("John", "Doe", "john@test.com", "testpwd");
		
		mockMvc.perform(post("/register").param("firstNameForm", "John")
				 						 .param("lastNameForm", "Ford")
				 						 .param("emailForm", "john@test.com")
				 						 .param("passwordForm", "testpwd")).andDo(print())
			.andExpect(view().name("register"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Failed to register")));
	}
}
