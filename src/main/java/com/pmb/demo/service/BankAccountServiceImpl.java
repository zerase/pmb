package com.pmb.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmb.demo.exception.MyCustomBusinessException;
import com.pmb.demo.model.BankAccount;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.BankAccountRepository;
import com.pmb.demo.repository.UserAccountRepository;


@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

	private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	@Autowired
	UserAccountRepository userAccountRepository;
	@Autowired
	UserAccountServiceImpl userAccountService;
	
	/**
	 * Save bank account info
	 * @see BankAccountService
	 */
	@Override
	public BankAccount saveBankAccountInfo(String emailOfCurrentUser, String ibanToAdd) throws MyCustomBusinessException{
		
		// Verify data validity
		if (ibanToAdd.isBlank() || emailOfCurrentUser.isBlank()) {
			logger.error("Invalid data : can't be null");
			throw new MyCustomBusinessException("Invalid data !");
		}
		
		Optional<UserAccount> existingUser = userAccountRepository.findUserAccountByEmail(emailOfCurrentUser);
		if(!existingUser.isPresent()) {
			logger.error("Unknown user");
			throw new MyCustomBusinessException("Unknown user !");
		}
		
		// The bank info we need to save in database
		BankAccount bankAccountToSaveInDb = new BankAccount();
		bankAccountToSaveInDb.setIban(ibanToAdd);
		
		BankAccount savedBankAccount = bankAccountRepository.save(bankAccountToSaveInDb);
		
		// The user related to the bank account we want to add
		Long id = existingUser.get().getUserId();
		
		// We add BankAccount to corresponding UserAccount
		UserAccount userAccountToUpdateWithBankInfo = userAccountRepository.getById(id);
		userAccountToUpdateWithBankInfo.setBankAccount(bankAccountToSaveInDb);
		userAccountRepository.save(userAccountToUpdateWithBankInfo);
		
		return savedBankAccount;
	}
}
