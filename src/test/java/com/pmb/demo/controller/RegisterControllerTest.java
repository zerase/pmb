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

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	
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
	}

	@Test
	@DisplayName("Display Register page")
	void testShowRegisterView() throws Exception {
		mockMvc.perform(get("/register")).andDo(print())
			.andExpect(view().name("register"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("register")));
	}

	@Test
	@DisplayName("Display Register page when user registered successfully")
	void testShowRegisterView_whenUserSucceedToRegister() throws Exception {
		mockMvc.perform(post("/register").param("firstNameForm", "Marie")
										 .param("lastNameForm", "Curie")
										 .param("emailForm", "marie@test.com")
										 .param("passwordForm", "test")).andDo(print())
			.andExpect(view().name("register"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("You have been registered successfully")));
	}
	
	@Test
	@DisplayName("Display Register page when user failed to register")
	void testShowRegisterView_whenUserFailedToRegister() throws Exception {
		mockMvc.perform(post("/register").param("firstNameForm", "John")
				 						 .param("lastNameForm", "Ford")
				 						 .param("emailForm", "john@test.com")
				 						 .param("passwordForm", "test")).andDo(print())
			.andExpect(view().name("register"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Failed to register")));
	}
}
