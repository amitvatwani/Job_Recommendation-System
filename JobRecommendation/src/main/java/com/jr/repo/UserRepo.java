package com.jr.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jr.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByEmail(String email);
	
}
