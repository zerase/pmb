package com.pmb.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmb.demo.exception.MyCustomBusinessException;
//import com.pmb.demo.model.Connection;
import com.pmb.demo.model.UserAccount;
//import com.pmb.demo.repository.ConnectionRepository;
import com.pmb.demo.repository.UserAccountRepository;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	//@Autowired
	//private ConnectionRepository connectionRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;


	/**
	 * Get current authenticated user
	 * @see UserAccountService
	 */
	@Override
	public Optional<UserAccount> getCurrentAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userAccountRepository.findUserAccountByEmail(email);
	}


	@Override
	public UserAccount getUserByEmail(String email) {
		Optional<UserAccount> user = userAccountRepository.findUserAccountByEmail(email);
		
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}


	@Override
	public Iterator<UserAccount> getUserConnectionsByMail(String email) {
		UserAccount user = userAccountRepository.findAllByEmail(email);
		
		return user.getFriends().iterator();
	}


	/*@Override
	public Connection addConnection(String userEmail, String friendEmail) {
		UserAccount user = getUserByEmail(userEmail);
		UserAccount friend = getUserByEmail(friendEmail);

		Connection friendshipToAdd = new Connection();
		friendshipToAdd.setUserAccountId(user);
		friendshipToAdd.setUserFriendId(friend);

		return connectionRepository.save(friendshipToAdd);
	}*/


	// This method should credit the connection (friend) with the amount of the transaction
	@Override
	public UserAccount credit(UserAccount user, BigDecimal amount) {
		BigDecimal userbalance = user.getBalance().setScale(2, RoundingMode.HALF_UP);
		user.setBalance(userbalance.add(amount));
		return userAccountRepository.save(user);
	}


	// This method should debit the current user with the amount of the transaction
	@Override
	public UserAccount debit(UserAccount user, BigDecimal amount) {
		BigDecimal userbalance = user.getBalance().setScale(2, RoundingMode.HALF_UP);
		user.setBalance(userbalance.subtract(amount));
		return userAccountRepository.save(user);
	}


	/**
	 * Save new user account.
	 * @see UserAccountService
	 */
	@Override
    public UserAccount saveNewUserAccount(String firstName,
                                          String lastName,
                                          String email,
                                          String password) throws MyCustomBusinessException {

		checkInputDataValidity(firstName, lastName, email, password);
		checkExistingEmail(email);

		String encodedPassword = passwordEncoder.encode(password);
		UserAccount userToSave = new UserAccount();

		userToSave.setFirstName(firstName);
		userToSave.setLastName(lastName);
		userToSave.setEmail(email);
		userToSave.setPassword(encodedPassword);
		userToSave.setBalance(new BigDecimal(0.00));

		return userAccountRepository.save(userToSave);
	}

	private void checkExistingEmail(String email) throws MyCustomBusinessException {
		Optional<UserAccount> existingUserAccount = userAccountRepository.findUserAccountByEmail(email);
		if(existingUserAccount.isPresent()) {
			logger.error("The email {} already exists", email);
			throw new MyCustomBusinessException("The email " + email + " is already taken !");
		}
	}
	
	private void checkInputDataValidity(String firstName, String lastName, String email, String password) throws MyCustomBusinessException {
		if(firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
			logger.error("Incorrect data : fields can't be null");
			throw new MyCustomBusinessException("Invalid field value !");
		}
	}


	/**
	 * Update user account.
	 * @see UserAccountService
	 */
	@Override
	public UserAccount editUserAccount(String firstName,
								String lastName,
								String password,
								UserAccount userToUpdate) throws MyCustomBusinessException {

		Optional<UserAccount> existingUserAccount = userAccountRepository.findUserAccountByEmail(userToUpdate.getEmail());
		
		if(!existingUserAccount.isPresent()) {
			logger.error("Can't update unknown user account");
			throw new MyCustomBusinessException("Unknown user account");
		}
		
		checkInputDataValidity(firstName, lastName, userToUpdate.getEmail(), password);

		String encodedPassword = passwordEncoder.encode(password);

		userToUpdate.setFirstName(firstName);
		userToUpdate.setLastName(lastName);
		userToUpdate.setPassword(encodedPassword);
		
		return userAccountRepository.save(userToUpdate);
	}
	
}
