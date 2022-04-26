package com.pmb.demo.security;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//import com.pmb.demo.model.UserAccount;
import com.pmb.demo.repository.UserAccountRepository;

public class MyCustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	
	// This method is used by Spring Security for login
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		/*Optional<UserAccount> userAccount = userAccountRepository.findUserAccountByEmail(email);

		if(userAccount.isEmpty()) {
			throw new UsernameNotFoundException("Username " + email + " not found");
		}

		return new MyCustomUserDetails(userAccount.get());*/
		return userAccountRepository.findUserAccountByEmail(email).map(MyCustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Username " + email + " not found"));
	}

}
