package com.pmb.demo.controller;

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
import com.pmb.demo.service.BankAccountService;
import com.pmb.demo.service.UserAccountService;

@Controller
public class ProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BankAccountService bankAccountService;
	
	
	@GetMapping("/profile")
	public String showProfileView(Model model) {	
		return "redirect:/profile/personal-info";
	}


	@GetMapping("/profile/personal-info")
    public String showProfilePersonalInfoView(@RequestParam(required = false) String error,
                                              @RequestParam(required = false) String message,
                                              Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());
		
		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);
		
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("email", user.getEmail());
		
		model.addAttribute("error", error);
		model.addAttribute("message", message);
		
		logger.info("Load personal-info view with request GET /profile/personal-info");
		return "personal-info";
	}


	@PostMapping("/profile/personal-info")
    public String updateProfilePersonalInfo(@RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String password,
                                            Model model) {
		logger.info("Load personal-info view with request POST /profile/personal-info");
		
		String error = null;
		String message = null;
		
		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());
		
		try {
			userAccountService.editUser(firstName, lastName, password, user);
			message = "msg-update-success";
			model.addAttribute("message", message);
			logger.info("The user profile info is updated");
		} catch (Exception e) {
			error = "Failed to update profile : " + e.getMessage();
			model.addAttribute("error", error);
			logger.error("An error occurred : " + e.getMessage());
		}
		
		// Return other informations to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);
		
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("email", user.getEmail());
		
		return "personal-info";
	}


	@GetMapping("/profile/bank-info")
    public String showProfileBankInfoView(@RequestParam(required = false) String error,
                                          @RequestParam(required = false) String message,
                                          Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());
		
		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);
		
		model.addAttribute("error", error);
		model.addAttribute("message", message);
		
		logger.info("Load bank-info view with request GET /profile/bank-info");
		return "bank-info";
	}


	@PostMapping("/profile/bank-info")
    public String updateProfileBankInfo(@RequestParam String iban,
                                        Model model) {
		logger.info("Load bank-info view with request POST /profile/bank-info");
		
		String error = null;
		String message = null;
		
		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		Optional<BankAccount> userBank = Optional.ofNullable(user.getBankAccount());
		
		try {
			bankAccountService.saveBank(user.getEmail(), iban);
			message = "msg-add-success";
			model.addAttribute("message", message);
			logger.info("The user bank info is added");
		} catch (Exception e) {
			error = "Failed to add bank info : " + e.getMessage();
			model.addAttribute("error", error);
			logger.error("An error occurred : " + e.getMessage());
		}
		
		// Return other informations to view
		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userbank", userBank);
		
		return "bank-info";
	}
}
