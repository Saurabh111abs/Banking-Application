package com.bank.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByAccountHolderName(String accountHolderName);
	

}
