package com.pmb.demo.service;

import com.pmb.demo.exception.MyCustomBusinessException;

public interface ContactService {
	
	/**
	 * Add a new contact in user's contact list
	 * @param currentUserEmail email of the current authenticated user
	 * @param contactEmail email of the contact to add
	 * @throws MyCustomBusinessException in case the contact is already in user's contact list
	 */
	public void addNewContact(String currentUserEmail, String contactEmail) throws MyCustomBusinessException;
}
