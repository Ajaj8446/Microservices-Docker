package com.ajaj.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajaj.document.Users;
import com.ajaj.repository.ImageRpository;
import com.ajaj.repository.UserRepository;

@RestController
@RequestMapping("/Users")
public class UserResource {
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	

	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "get/{userId}")
	public ResponseEntity<Users> getUser(@PathVariable String userId) {
		Optional<Users> Users = userRepository.findById(userId);

		if (Users.isPresent()) {
			return new ResponseEntity<>(Users.get(), HttpStatus.OK);
		} else
			return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/add", consumes = "application/json")
	public ResponseEntity<String> addUser(@RequestBody Users users) {
		Optional<Users> Users = userRepository.findById(users.getEmail());
		if (Users.isPresent()) {
			return new ResponseEntity("User Already registered", HttpStatus.CONFLICT);
		} else
			// User id is email id
		users.setUserId(users.getEmail());
		// Passowrd is encodded
		users.setPassword(encoder.encode(users.getPassword()));
		// While adding new User Active flag is false, after confirming email we can set
		// this to true
		users.setActive(false);
		// Non-admin user
		users.setUser_type("NORMAL");

		userRepository.save(users);
	
		return new ResponseEntity("User Added Succesfully", HttpStatus.OK);
	}

	@GetMapping(path = "remove/{userId}")
	public ResponseEntity removeUser(@PathVariable String userId) {

		Optional<Users> Users = userRepository.findById(userId);
		if (Users.isPresent()) {
			userRepository.deleteById(userId);
			return new ResponseEntity("User Removed", HttpStatus.OK);
		} else
			return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);

	}

	// user can only firstname,lastname and email .if email is chnaged then treat as
	// new user and paste old data to new
	@PostMapping(path = "/modify/{userId}", consumes = "application/json")
	public ResponseEntity modifyUser(@RequestBody Users users, @PathVariable String userId) {
		Optional<Users> Users = userRepository.findById(users.getUserId());
		if (Users.isPresent()) {
			Users usertomodify = new Users();
			usertomodify = Users.get();

			if (null != users.getFirstname()) {
				usertomodify.setFirstname(users.getFirstname());
			}
			if (null != users.getLastname()) {
				usertomodify.setLastname(users.getLastname());
			}

			// if email is chnaged - then copy all old id data to new , and make flag false
			if (null != users.getEmail()) {
				// User id is email id
				usertomodify.setEmail(users.getEmail());
				usertomodify.setUserId(users.getEmail());
				// While adding new User Active flag is false, after confirming email we can set
				// this to true
				usertomodify.setActive(false);
			}

			userRepository.save(usertomodify);
			return new ResponseEntity("User Data Modified", HttpStatus.OK);

		} else
			return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);
	}

	// code remaininf for verify and sent mail

}
