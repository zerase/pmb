package com.pmb.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmb.demo.model.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	/**
	 * Retrieves an user by his email.
	 * @param email this email should not be null.
	 * @return the value of the given email, Optional.empty() if not found.
	 */
	Optional<UserAccount> findUserAccountByEmail(String email);
	
	UserAccount findAllByEmail(String email);
	
	/**
	 * Checks whether if an email exists in database.
	 * @param email string representing the input email entered.
	 * @return true if the given email exists, false otherwise.
	 */
	//boolean existsByEmail(String email);
	
}
