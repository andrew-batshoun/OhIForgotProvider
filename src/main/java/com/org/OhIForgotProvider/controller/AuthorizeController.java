package com.org.OhIForgotProvider.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

	private AuthenticationManager authenticationManager; 
	private UserRepository userDao;
	private PasswordEncoder passwordEncoder;

	public AuthorizeController(UserRepository userDao, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        // add check for username exists in a DB
        if(userDao.existsByUsername(user.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userDao.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
	
	
	
	
	
}