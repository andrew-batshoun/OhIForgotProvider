package com.org.OhIForgotProvider.service;

import java.util.List;

import com.org.OhIForgotProvider.dto.UserDTO;
import com.org.OhIForgotProvider.model.User;

public interface UserService {
	public User findById(Long id);
	public User findbyName(String username);
	public User saveUser(User user);
	public User updateUser(Long id, User user);
	public void deleteUser(Long id);
	public List<User> findAllUsers();
	public boolean existsByUsername(String username);
	public boolean existsByEmail(String email);
}
