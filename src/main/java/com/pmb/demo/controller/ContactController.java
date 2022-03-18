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
	
	
	// TODO: Ajouter Security
	// OK :-) Retourne la page contacts contenant la liste d'un user (ici John Doe)
	@GetMapping("/contact")
	public String showContactView(Model model) {
		
		// TODO: Remplacer par vrai authentification par email avec Spring Security
		// Simule un utilisateur authentifié par son email
		UserAccount user = userAccountService.getUserByEmail("john@test.com");
		
		Iterator<UserAccount> userfriends = userAccountService.getUserConnectionsByMail(user.getEmail());

		// Return user information to view
		model.addAttribute("userconnections", userfriends);
		
		return "contact" ;
	}
	
	
	// TODO: Ajouter Security
	// OK :-) Formulaire d'ajout d'un nouveau contact à sa liste
	@PostMapping("/contact")
	public String addNewConnection(Model model, @RequestParam String email) {

		// TODO: Remplacer par vrai authentification par email avec Spring Security
		// Simule un utilisateur authentifié par son email
		UserAccount user = userAccountService.getUserByEmail("john@test.com");

		try {
			userAccountService.addConnection(user.getEmail(), email);
			return "redirect:/contact?successAddConnection";
		} catch (Exception e) {
			System.out.println(e);
			return "redirect:/contact?errorAddConnection";
		}
	}
}
