package com.pmb.demo.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmb.demo.exception.MyCustomBusinessException;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.UserAccountRepository;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	/**
	 * Add a contact
	 * @see ContactService
	 */
	@Override
	public void addNewContact(String currentUserEmail, String contactEmail) throws MyCustomBusinessException {
		Optional<UserAccount> currentUser = userAccountRepository.findUserAccountByEmail(currentUserEmail);
		Optional<UserAccount> contactToBe = userAccountRepository.findUserAccountByEmail(contactEmail);
		
		// Verify both mails validity
		if(!currentUser.isPresent()) {
			logger.error("Unknown user");
			throw new MyCustomBusinessException("Unknown user !");
		}
		
		if(!contactToBe.isPresent()) {
			logger.error("Unknown contact");
			throw new MyCustomBusinessException("Unknown contact !");
		}
		
		// Prepare contact list
		UserAccount userAccountToUpdate = userAccountRepository.getById(currentUser.get().getUserId());
		Set<UserAccount> contacts = userAccountToUpdate.getFriends();
		
		contacts.add(contactToBe.get());
		
		// Update user account with his contacts list
		userAccountToUpdate.setFriends(contacts);
		userAccountRepository.save(userAccountToUpdate);
	}

}
