
package com.example.demo.config;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Spring Security class to handle log-in users (Admin and User) roles
 * Encrypts the password in memory
 */
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/**").hasAnyRole("ADMIN","USER").and().formLogin();
		
		http.csrf().disable().authorizeRequests().antMatchers("/**").hasAnyRole("ADMIN","USER").and().formLogin();
		
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		
		  auth.inMemoryAuthentication().withUser("admin").password(encoder().encode("admin")).roles("ADMIN");
		  auth.inMemoryAuthentication().withUser("user").password(encoder().encode("user")).roles("USER");
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}   
