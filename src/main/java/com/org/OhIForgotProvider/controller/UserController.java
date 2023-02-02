package com.org.OhIForgotProvider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.service.UserService;

@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	//gets user by id NOT USED
	 @GetMapping("/profile/{id}")
	    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
	        System.out.println("Fetching User with id " + id);
	        User user = userService.findById(id);
	        if (user == null) {
	            System.out.println("User with id " + id + " not found");
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<User>(user, HttpStatus.OK);
	    }
	
	 //updates user profile NOT USED
	@PutMapping("profile/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
		System.out.println("Updating user " + id);
		
	User currentUser = userService.findById(id);
		
		if(currentUser == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		currentUser.setPassword(user.getPassword());
//		currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userService.updateUser(id, currentUser);
		return new ResponseEntity<User> (currentUser, HttpStatus.OK);
	}
	
	//deletes user NOT USED
	@DeleteMapping("profile/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
 
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        userService.deleteUser(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
	
	
}
