package com.pmb.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmb.demo.model.BankAccount;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.BankAccountRepository;
import com.pmb.demo.repository.UserAccountRepository;

@Service
public class BankAccountService {

	@Autowired
	BankAccountRepository bankAccountRepository;
	@Autowired
	UserAccountRepository userAccountRepository;
	@Autowired
	UserAccountService userAccountService;
	
	
	public BankAccount saveBank(String email, String userBankToAdd) {
		
		// The bank info we need to save in db
		BankAccount bankAccountToSaveInDb = new BankAccount();
		bankAccountToSaveInDb.setIban(userBankToAdd);
		
		BankAccount succeedAddBank = bankAccountRepository.save(bankAccountToSaveInDb);
		
		// The user related to the bank we want to add
		UserAccount user = userAccountService.getUserByEmail(email);
		Long id = user.getUserId();
		// We add bank account to entity UserAccount
		UserAccount userToUpdate = userAccountRepository.getById(id);
		userToUpdate.setBankAccount(bankAccountToSaveInDb);
		userAccountRepository.save(userToUpdate);
		
		return succeedAddBank;
	}
}
