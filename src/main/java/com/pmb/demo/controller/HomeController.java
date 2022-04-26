package com.pmb.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pmb.demo.model.BankAccount;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.UserAccountService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserAccountService userAccountService;


	@GetMapping("/home")
	public String showHomeView(Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());

		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);

		logger.info("Load home view with request GET /home");
		return "home";
	}

}
