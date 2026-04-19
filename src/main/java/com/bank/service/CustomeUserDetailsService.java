package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.dao.UserRepository;
import com.bank.entity.User;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		  User user = userRepository.findByAccountHolderName(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	        return user;
	}

}
