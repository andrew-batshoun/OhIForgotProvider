package com.org.OhIForgotProvider.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.org.OhIForgotProvider.model.LoginDTO;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/auth")
@RestController
public class AuthorizeController {

	@Autowired
	private UserRepository userDao;

	// login creates dto to verify user
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody LoginDTO ld) {

		System.out.println(ld.getUsername());

		if (userDao.existsByUsername(ld.getUsername())) {

			User current = userDao.findByUsername(ld.getUsername());

			System.out.println(current.getPassword());

			if (current.getPassword().equals(ld.getPassword())) {

				return new ResponseEntity<User>(current, HttpStatus.OK);
			} else {

				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} else {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	@PostMapping("/login")
//    public ResponseEntity<User> authenticateUser(@RequestBody User user){
////        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
////                user.getUsername(), user.getPassword()));
//
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//		
//		User currentUser = userDao.findByUsername(user.getUsername());
//		if(currentUser == null || currentUser.getPassword() != user.getPassword()) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
//    }

	// adds user
	@PostMapping("/signup")
	public ResponseEntity<User> registerUser(@RequestBody User user) {

		// add check for username exists in a DB
		if (userDao.existsByUsername(user.getUsername())) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}

		// add check for email exists in DB
		if (userDao.existsByEmail(user.getEmail())) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}

		// create user object
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
//        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

		userDao.save(user);

		return new ResponseEntity<User>(newUser, HttpStatus.OK);

	}

}
