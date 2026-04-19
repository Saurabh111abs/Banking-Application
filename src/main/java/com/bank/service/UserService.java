package com.bank.service;

import java.net.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.config.JwtUtils;
import com.bank.dao.UserRepository;
import com.bank.dto.AuthRequest;
import com.bank.dto.AuthResponse;
import com.bank.dto.RegistrationRequest;
import com.bank.dto.RegistrationResponse;
import com.bank.entity.User;
import com.bank.entity.User.Role;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

    // REGISTER SERVICE
	public RegistrationResponse  register(RegistrationRequest registrationRequest) {

		User user = new User();
		user.setAccountHolderName(registrationRequest.getAccountHolderName());
		user.setEmail(registrationRequest.getEmail());
		user.setRole(Role.valueOf(registrationRequest.getRole().toUpperCase()));
		user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

		userRepository.save(user);
		
		RegistrationResponse registrationResponse = new RegistrationResponse();
				registrationResponse.setAccountHolderName(user.getAccountHolderName());
		        registrationResponse.setEmail(user.getEmail());
		        registrationResponse.setPassword(user.getPassword());
		        registrationResponse.setRole(user.getRole());

//		String token =  jwtUtils.generateToken(user);
//
//		AuthResponse authResponse = new AuthResponse();
//		authResponse.setToken(token);

		return registrationResponse;

	}

	//LOGIN SERVICE
	public AuthResponse login(AuthRequest authRequest) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authRequest.getAccountHolderName(), authRequest.getPassword()));

		User user =	userRepository.findByAccountHolderName(authRequest.getAccountHolderName())
				.orElseThrow(() -> new RuntimeException("user not found"));

		String token =	jwtUtils.generateToken(user);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setToken(token);


		return authResponse;


	}

}
