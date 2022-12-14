package com.org.OhIForgotProvider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userDao;
	
	@Override
	public User findById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public User findbyName(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findAll();
	}

}
