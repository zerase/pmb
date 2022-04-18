package com.pmb.demo.controller;

import java.util.Iterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.model.BankAccount;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.UserAccountService;

@Controller
public class ContactController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private UserAccountService userAccountService;


	@GetMapping("/contact")
	public String showContactView(Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());
		
		Iterator<UserAccount> userfriends = userAccountService.getUserConnectionsByMail(user.getEmail());

		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " +user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);
		model.addAttribute("userconnections", userfriends);
		logger.info("The user is invited to add a connection to his list");
		return "contact" ;
	}


	@PostMapping("/contact")
	public String addNewConnection(@RequestParam String email, Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();

		try {
			userAccountService.addConnection(user.getEmail(), email);
			logger.info("The user add a connection successfully");
			return "redirect:/contact?successAddConnection";
		} catch (Exception e) {
			logger.error("An error occur : " + e.getMessage());
			return "redirect:/contact?errorAddConnection";
		}
	}
}
