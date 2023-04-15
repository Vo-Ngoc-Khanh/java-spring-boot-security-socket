package com.project.demo.service;

import org.springframework.http.ResponseEntity;

import com.project.demo.entity.User;
import com.project.demo.response.ResponseObject;

public interface AuthService {

	public ResponseEntity<ResponseObject> login(User user);
	
	public ResponseEntity<ResponseObject> getProfile(String username);

	public ResponseEntity<ResponseObject> updateProfile(User user, String username);

}
