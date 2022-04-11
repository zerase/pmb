package com.pmb.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/login")
	public String showLoginView(Model model, 
								@RequestParam(required = false) String error, 
								@RequestParam(required = false) String logout) {
		if(error != null) {
			logger.error("The user failed to login");
			model.addAttribute("error", "msg-error");
		}
		
		if(logout != null) {
			logger.info("The user logged out");
			model.addAttribute("message", "msg-logout");
		}
		
		logger.info("The user is invited to login with his credentials");
		return "login";
	}

}
