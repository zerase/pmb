package com.pmb.demo.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmb.demo.model.UserAccount;
import com.pmb.demo.service.UserAccountService;

@Controller
public class ContactController {
	
	@Autowired
	private UserAccountService userAccountService;
	
	
	// XXX: Retourne la page contacts contenant la liste d'un user
	@GetMapping("/contact")
	public String showContactView(Model model) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();
		
		Iterator<UserAccount> userfriends = userAccountService.getUserConnectionsByMail(user.getEmail());

		// Return user information to view
		model.addAttribute("username", user.getFirstName() + " " +user.getLastName());
		model.addAttribute("userbalance", user.getBalance());
		model.addAttribute("userconnections", userfriends);
		
		return "contact" ;
	}
	
	
	// XXX: Formulaire d'ajout d'un nouveau contact à sa liste
	@PostMapping("/contact")
	public String addNewConnection(Model model, @RequestParam String email) {

		// Get current authenticated user
		UserAccount user = userAccountService.getCurrentConnectedUser().orElseThrow();

		try {
			userAccountService.addConnection(user.getEmail(), email);
			return "redirect:/contact?successAddConnection";
		} catch (Exception e) {
			System.out.println(e);
			return "redirect:/contact?errorAddConnection";
		}
	}
}
