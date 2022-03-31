package com.pmb.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pmb.demo.model.Connection;
import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.ConnectionRepository;
import com.pmb.demo.repository.UserAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private ConnectionRepository connectionRepository;
	
	
	
	// TODO : Voir si on garde cette méthode après la mise en place de la couche Security
	/**
	 * Get a user from database by his id
	 * @param id of the requested user
	 * @return a object UserAccount, null otherwise
	 */
	/*public UserAccount getUserById(Long id) {
		Optional<UserAccount> user = userAccountRepository.findById(id);
		
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}*/
	
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
	
	// TODO : Add a new user to db with a register form
	/*public UserAccount addNewUser() {
		return null;
	}*/
	
	// This method should update a user profile
	public UserAccount editUser(String firstNameForm,
								String lastNameForm,
								String passwordForm,
								UserAccount user) {
		
		user.setFirstName(firstNameForm);
		user.setLastName(lastNameForm);
		user.setPassword(passwordForm);
		
		return userAccountRepository.save(user);
	}
	
}
