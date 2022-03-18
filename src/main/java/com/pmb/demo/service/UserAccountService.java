package com.pmb.demo.service;

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
		friendshipToAdd.setUserAccountId(user.getUserId());
		friendshipToAdd.setUserFriendId(friend.getUserId());

		return connectionRepository.save(friendshipToAdd);
	}
	
	
	
	// TODO : This method should credit the connection (friend) with the amount of the transaction
	/*public UserAccount credit(UserAccount user, BigDecimal amount) {
		return null;
	}*/
	
	// TODO: This method should debit the current user with the amount of the transaction
	/*public UserAccount debit(UserAccount user, BigDecimal amount) {
		return null;
	}*/
	
	// TODO : Add a new user to db with a register form
	/*public UserAccount addNewUser() {
		return null;
	}*/
	
	// TODO : Update a user profile
	/*public UserAccount editUser() {
		return null;
	}*/
	
}
