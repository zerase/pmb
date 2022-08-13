package com.pmb.demo.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Optional;

import com.pmb.demo.exception.MyCustomBusinessException;
//import com.pmb.demo.model.Connection;
import com.pmb.demo.model.UserAccount;

public interface UserAccountService {

	/**
	 * Get current authenticated user
	 * @return an Optional UserAccount object, null otherwise
	 */
	public Optional<UserAccount> getCurrentAuthenticatedUser();
	
	/**
	 * Get a user from database by his email
	 * @param email The email of the requested user
	 * @return a object UserAccount, null otherwise
	 */
	public UserAccount getUserByEmail(String email);


	/**
	 * Get list of connections (friends) of an user
	 * @param email The email of the requested user
	 * @return an Iterator<UserAccount> over the user contact list
	 */
	public Iterator<UserAccount> getUserConnectionsByMail(String email);


	/**
	 * Add a connection (friend) to an user by his email address
	 * @param userEmail The email of the requested user
	 * @param friendEmail The email of the contact to add
	 * @return
	 */
	//public Connection addConnection(String userEmail, String friendEmail);


	/**
	 * Credit the balance of an user
	 * @param user The user to credit
	 * @param amount The amount to credit
	 * @return
	 */
	public UserAccount credit(UserAccount user, BigDecimal amount);


	/**
	 * Debit the balance of an user
	 * @param user The user to debit
	 * @param amount The amount to debit
	 * @return
	 */
	public UserAccount debit(UserAccount user, BigDecimal amount);


	/**
	 * Save new user account
	 * @param firstName string representing the input first name entered
	 * @param lastName  string representing the input last name entered
	 * @param email     string representing the input email entered
	 * @param password  string representing the input password entered
	 * @return the saved user
	 * @throws MyCustomBusinessException in case the email already exists or invalid data
	 */
    public UserAccount saveNewUserAccount(String firstName, String lastName, String email, String password) throws MyCustomBusinessException;


	/**
	 * Update user profile info
	 * @param firstName    string representing the input first name entered
	 * @param lastName     string representing the input last name entered
	 * @param password     string representing the input password entered
	 * @param userToUpdate UserAccount object to be updated for
	 * @return the updated user
	 * @throws MyCustomBusinessException in case of invalid data
	 */
	public UserAccount editUserAccount(String firstName, String lastName, String password, UserAccount userToUpdate) throws MyCustomBusinessException;
	
}
