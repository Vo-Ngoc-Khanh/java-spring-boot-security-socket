package com.project.demo.service;

import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.project.demo.response.ResponseObject;

public interface UserRoleService {

	public boolean isExisting(int userId, int roleId);

	public ResponseEntity<ResponseObject> addUserRole(String username, Set<String> roles);

	public ResponseEntity<ResponseObject> deleteUserRole(String username, Set<String> roles);

}
