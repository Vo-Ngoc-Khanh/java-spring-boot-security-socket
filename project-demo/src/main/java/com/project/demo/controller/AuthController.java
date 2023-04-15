package com.project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.User;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.AuthService;
import com.project.demo.service.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ResponseObject> login(@RequestBody User user) {
		return authService.login(user);
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseObject> register(@RequestBody User user) {
		return userService.addNew(user);
	}
	
//	@GetMapping("/users/profile")
//	public ResponseEntity<ResponseObject> getProfile() {
//		String user_name = SecurityContextHolder.getContext().getAuthentication().getName();
//		return userService.getProfile(user_name);
//	}
	
	@GetMapping("/profile")
	public ResponseEntity<ResponseObject> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		return authService.getProfile(username);
	}

	@PutMapping("/profile")
	public ResponseEntity<ResponseObject> updateProfile(@RequestBody User user,
			@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		return authService.updateProfile(user, username);
	}

}
