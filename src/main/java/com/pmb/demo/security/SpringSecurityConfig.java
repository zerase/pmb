package com.pmb.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	// TODO: Ajouter Javadoc comment --> Custom UserDetails
	@Bean
	public UserDetailsService userDetailsService() {
		return new MyCustomUserDetailsService();
	}
	
	// TODO: Ajouter Javadoc comment --> Password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// TODO: Ajouter Javadoc comment --> Jpa Authentication
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userDetailsService());
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
	}
	
	// TODO: Ajouter Javadoc comment --> Authentication manager
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	// TODO: Ajouter Javadoc comment --> HTTP builder configurations for: authorize requests, form login, logout
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/login", "/register").permitAll() // anyone can access to these pages...
				.anyRequest().authenticated() // ... but the other pages require authentication
				.and()
			.formLogin() // configuration for login
				.loginPage("/login") // the custom login page
					.usernameParameter("custom-username") // name of the input in HTML
					.passwordParameter("custom-password") // name of the input in HTML
				.loginProcessingUrl("/perform_login") // the URL to submit the "username"(email) and password to
				.defaultSuccessUrl("/home", true) // the landing page after a successful login
				.failureUrl("/login?error") // the landing page after an unsuccessful login
				.and()
			.logout() // configuration for logout
				.logoutUrl("/perform_logout") // the URL that invoke logout
				.logoutSuccessUrl("/login?logout") // the landing page after a successful logout
				.deleteCookies("JSESSIONID"); // TODO: Need to check if this should be place before or after .logoutSuccessUrl
	}

}
