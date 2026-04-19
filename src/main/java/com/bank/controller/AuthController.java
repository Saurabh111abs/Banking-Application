package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.AuthRequest;
import com.bank.dto.AuthResponse;
import com.bank.dto.RegistrationRequest;
import com.bank.dto.RegistrationResponse;
import com.bank.entity.User;
import com.bank.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;


	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
		
		return ResponseEntity.ok(userService.register(request));
		
	}


	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest)
	{

		return  ResponseEntity.ok(userService.login(authRequest));
	}

}
