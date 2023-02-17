package com.org.OhIForgotProvider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.OhIForgotProvider.dto.UserDTO;
import com.org.OhIForgotProvider.exceptions.NotFoundException;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findById(Long id) {
		return userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	public User findbyName(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User saveUser(User user) {
		
				User newUser = new User();
				newUser.setUsername(user.getUsername());
				newUser.setEmail(user.getEmail());
				newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userDao.saveAndFlush(newUser);
	}

	@Override
	public User updateUser(Long id, User user) {
		
		User currentUser = findById(id);
			currentUser.setUsername(user.getUsername());
			currentUser.setEmail(user.getEmail());
			currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
			return userDao.save(currentUser);
		
		
	}

	@Override
	public void deleteUser(Long id) {
		
		userDao.deleteById(id);
	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	public boolean existsByUsername(String username) {
		
		return userDao.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		
		return userDao.existsByEmail(email);
	}

}
