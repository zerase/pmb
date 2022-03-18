package com.pmb.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmb.demo.model.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
	
	Optional<UserAccount> findUserAccountByEmail(String email); // Pour trouver UN SEUL compte utilisateur par email
	
	UserAccount findAllByEmail(String email); // Pour  trouver l'ensemble des contacts d'un user
	
}
