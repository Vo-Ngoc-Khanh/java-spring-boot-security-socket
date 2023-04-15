package com.project.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.demo.entity.User;
import com.project.demo.jwt.JwtService;
import com.project.demo.repository.UserRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.AuthService;

@Service
public class AuServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public ResponseEntity<ResponseObject> login(User user) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtService.generateToken(user.getUsername());
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Login success", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ResponseObject("Error", "Invalid username or password", ""));
		}
	}

	public ResponseEntity<ResponseObject> getProfile(String username) {
		User check_exist = userRepository.findByUsername(username);
		if (check_exist != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Get profile success", check_exist));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject("Error", "Wrong!!!User not found/ error.", ""));
		}
	}

	public ResponseEntity<ResponseObject> updateProfile(User user, String username) {
		User existingUser = userRepository.findByUsername(username);
		if (existingUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("Error", "Invalid username", ""));
		}

		boolean isFullnameChanged = user.getFullname() != null && !user.getFullname().isEmpty();
		boolean isPasswordChanged = user.getPassword() != null && !user.getPassword().isEmpty();

		if (!isFullnameChanged && !isPasswordChanged) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("Success", "Your profile has been updated, but no changes were detected.", ""));
		}

		if (isFullnameChanged && isPasswordChanged) {
			existingUser.setFullname(user.getFullname());
			existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(existingUser);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Profile updated successfully", ""));
		}

		if (isFullnameChanged) {
			existingUser.setFullname(user.getFullname());
			userRepository.save(existingUser);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Your fullname has been updated.", ""));
		}

		existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(existingUser);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("Success", "Your password has been updated.", ""));
	}

}
