package com.bank.dto;

import com.bank.entity.User.Role;

public class RegistrationResponse {
	
	
	private String accountHolderName;
	
	private String email;
	
	private String password;
	
	private Role role;
	

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role2) {
		this.role = role2;
	}
	
	

}
