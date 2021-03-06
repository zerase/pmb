package com.pmb.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmb.demo.model.Connection;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.ConnectionRepository;
import com.pmb.demo.repository.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private ConnectionRepository connectionRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;


	/**
	 * Get current authenticated user
	 * @return
	 */
	public Optional<UserAccount> getCurrentConnectedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userAccountRepository.findUserAccountByEmail(email);
	}


	/**
	 * Get a user from database by his email
	 * @param email The email of the requested user
	 * @return a object UserAccount, null otherwise
	 */
	public UserAccount getUserByEmail(String email) {
		Optional<UserAccount> user = userAccountRepository.findUserAccountByEmail(email);
		
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}


	/**
	 * Get list of connections (friends) of an user
	 * @param email The email of the requested user
	 * @return an Iterator<UserAccount> over the user contact list
	 */
	public Iterator<UserAccount> getUserConnectionsByMail(String email) {
		UserAccount user = userAccountRepository.findAllByEmail(email);
		
		return user.getFriends().iterator();
	}


	/**
	 * Add a connection (friend) to an user by his email address
	 * @param userEmail The email of the requested user
	 * @param friendEmail The email of the contact to add
	 * @return
	 */
	public Connection addConnection(String userEmail, String friendEmail) {
		UserAccount user = getUserByEmail(userEmail);
		UserAccount friend = getUserByEmail(friendEmail);

		Connection friendshipToAdd = new Connection();
		friendshipToAdd.setUserAccountId(user);
		friendshipToAdd.setUserFriendId(friend);

		return connectionRepository.save(friendshipToAdd);
	}


	// This method should credit the connection (friend) with the amount of the transaction
	/**
	 * Credit the balance of an user
	 * @param user The user to credit
	 * @param amount The amount to credit
	 * @return
	 */
	public UserAccount credit(UserAccount user, BigDecimal amount) {
		BigDecimal userbalance = user.getBalance().setScale(2, RoundingMode.HALF_UP);
		user.setBalance(userbalance.add(amount));
		return userAccountRepository.save(user);
	}


	// This method should debit the current user with the amount of the transaction
	/**
	 * Debit the balance of an user
	 * @param user The user to debit
	 * @param amount The amount to debit
	 * @return
	 */
	public UserAccount debit(UserAccount user, BigDecimal amount) {
		BigDecimal userbalance = user.getBalance().setScale(2, RoundingMode.HALF_UP);
		user.setBalance(userbalance.subtract(amount));
		return userAccountRepository.save(user);
	}


	/**
	 * Save new user in database.
	 * @param firstName string representing the input first name entered.
	 * @param lastName  string representing the input last name entered.
	 * @param email     string representing the input email entered.
	 * @param password  string representing the input password entered.
	 * @return the saved user.
	 * @throws Exception in case the email already exists.
	 */
    public UserAccount saveNewUserAccount(String firstName,
                                          String lastName,
                                          String email,
                                          String password) throws Exception {

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

	private void checkExistingEmail(String email) throws Exception {
		if(userAccountRepository.existsByEmail(email)) {
			throw new Exception("The provided email " + email + " is already taken");
		}
	}


	/**
	 * Update user profile info.
	 * @param firstName string representing the input first name entered.
	 * @param lastName  string representing the input last name entered.
	 * @param password  string representing the input password entered.
	 * @param userToUpdate UserAccount object to be updated for.
	 * @return the updated user.
	 */
	public UserAccount editUser(String firstName,
								String lastName,
								String password,
								UserAccount userToUpdate) {

		String encodedPassword = passwordEncoder.encode(password);

		userToUpdate.setFirstName(firstName);
		userToUpdate.setLastName(lastName);
		userToUpdate.setPassword(encodedPassword);
		
		return userAccountRepository.save(userToUpdate);
	}
	
}
