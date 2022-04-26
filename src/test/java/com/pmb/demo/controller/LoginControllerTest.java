package com.pmb.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	@Test
	@DisplayName("Display Login view")
	void testShowLoginView_shouldReturnIsOk_whenRequested() throws Exception {
		mockMvc.perform(get("/login")).andDo(print())
			.andExpect(view().name("login"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("login")));
	}
	
	@Test
	@DisplayName("Display Login view when user failed to login")
	void testShowLoginView_shouldDisplayErrorMessage_whenUserFailedToLogIn() throws Exception {
		mockMvc.perform(get("/login").param("error", "error")).andDo(print())
			.andExpect(view().name("login"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Failed to log in")));
	}
	
	@Test
	@DisplayName("Display Login view when user logged out")
	void testShowLoginView_shouldDisplayLogoutMessage_whenUserLoggedOut() throws Exception {
		mockMvc.perform(get("/login").param("logout", "logout")).andDo(print())
			.andExpect(view().name("login"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("You've been logged out")));
	}

}
