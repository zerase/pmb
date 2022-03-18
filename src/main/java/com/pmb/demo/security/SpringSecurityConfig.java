/*
 * package com.pmb.demo.security;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.core.userdetails.UserDetailsService;
 * 
 * import com.pmb.demo.service.UserAccountService;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity public class SpringSecurityConfig extends
 * WebSecurityConfigurerAdapter{
 * 
 * @Autowired UserAccountService userDetailsService;
 * 
 * @Bean
 * 
 * @Override public UserDetailsService userDetailsService() { return
 * userDetailsService; }
 * 
 * @Override public void configure(HttpSecurity http) throws Exception {
 * http.authorizeRequests() .antMatchers("/",
 * "/home").authenticated().anyRequest().permitAll() .and() .formLogin()
 * .loginPage("/login") .permitAll() .and() .logout() .permitAll(); }
 * 
 * }
 */