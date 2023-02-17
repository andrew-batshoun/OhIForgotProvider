package com.org.OhIForgotProvider.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.org.OhIForgotProvider.dto.LoginDTO;
import com.org.OhIForgotProvider.dto.UserDTO;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.UserRepository;
import com.org.OhIForgotProvider.service.UserService;

@RequestMapping("/api/auth")
@RestController
public class AuthorizeController {

	private AuthenticationManager authenticationManager;
	private UserService userService;
	private ModelMapper modelMapper;
	

	public AuthorizeController(AuthenticationManager authenticationManager, UserService userService,
			ModelMapper modelMapper) {
		
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
		

	// login creates dto to verify user
	@PostMapping("/login")
	public ResponseEntity<LoginDTO> loginUser(@RequestBody LoginDTO loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new ResponseEntity<>(loginDto , HttpStatus.OK);
	}

	// adds user
	@PostMapping("/signup")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO user) {

		// add check for username exists in a DB
		if (userService.existsByUsername(user.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}

		// add check for email exists in DB
		if (userService.existsByEmail(user.getEmail())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User toSave = modelMapper.map(user, User.class);
		
		UserDTO userSaved = modelMapper.map(userService.saveUser(toSave), UserDTO.class);


		return new ResponseEntity<>(userSaved, HttpStatus.OK);

	}

}
