package com.project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.response.ResponseObject;
import com.project.demo.response.UserRoleRequestBody;
import com.project.demo.service.UserRoleService;

@RestController
@RequestMapping("/api/v1")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
		
	
	@PostMapping("/user-roles/add")
	public ResponseEntity<ResponseObject> addNewUserRole(@RequestBody UserRoleRequestBody request){
		return userRoleService.addUserRole(request.getUsername(), request.getRole());
	}
	
	@DeleteMapping("/user-roles/delete")
	public ResponseEntity<ResponseObject> deleteUserRole(@RequestBody UserRoleRequestBody request){
		return userRoleService.deleteUserRole(request.getUsername(), request.getRole());
	}
}
