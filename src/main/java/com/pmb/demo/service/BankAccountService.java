package com.pmb.demo.service;

import com.pmb.demo.exception.MyCustomBusinessException;
import com.pmb.demo.model.BankAccount;

public interface BankAccountService {

	/**
	 * Save bank account info
	 * @param email the email of authenticated user
	 * @param iban  bank info to add
	 * @return the saved bank account
	 * @throws MyCustomBusinessException in case of invalid data or unknown user
	 */
	public BankAccount saveBankAccountInfo(String email, String iban) throws MyCustomBusinessException;
	
}
