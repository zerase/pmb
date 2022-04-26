package com.pmb.demo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pmb.demo.model.UserAccount;

public class MyCustomUserDetails implements UserDetails {

	private static final long serialVersionUID = -333718728194444531L;
	UserAccount userAccount;

	// Constructor
	public MyCustomUserDetails(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	// Getter
	public UserAccount getUser() {
		return userAccount;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		// This should be encrypted/hashed
		return userAccount.getPassword();
	}

	@Override
	public String getUsername() {
		// We replace the default "username" by the email
		return userAccount.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
