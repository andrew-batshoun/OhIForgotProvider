package com.org.OhIForgotProvider.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.OhIForgotProvider.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
