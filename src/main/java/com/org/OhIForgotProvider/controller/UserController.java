package com.org.OhIForgotProvider.controller;




import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.OhIForgotProvider.dto.UserDTO;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.service.UserService;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	private ModelMapper modelMapper; 
	

	//gets user by id NOT USED
	 @GetMapping("/profile/{id}")
	    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
	        System.out.println("Fetching User with id " + id);
	        User user = userService.findById(id);
	       
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    }
	
	 //updates user profile NOT USED
	@PutMapping("profile/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO user){
		System.out.println("Updating user " + id);
		User toUpdate = modelMapper.map(user, User.class);
		userService.updateUser(id, toUpdate);
		
		return new ResponseEntity<> (user, HttpStatus.OK);
	}
	
	//deletes user NOT USED
	@DeleteMapping("profile/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
 
        User user = userService.findById(id);
     
 
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	
}
