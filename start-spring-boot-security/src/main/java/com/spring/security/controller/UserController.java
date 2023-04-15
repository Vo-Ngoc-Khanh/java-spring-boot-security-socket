package com.spring.security.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.entity.User;
import com.spring.security.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
		
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		return userService.login(user);
	}
	
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		return ResponseEntity.ok("Hello is xin chao");
		
	}
	
	@GetMapping("/users")
	public List<User >getListUser(){
		return userService.getListUser();
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> findUserbyId(@PathVariable("id") int id) {
		return userService.findUser(id);
	}
	
	@PostMapping("/users")
	public User insert(@RequestBody User user) throws Exception {	
		return userService.add(user);
	}
		
}
