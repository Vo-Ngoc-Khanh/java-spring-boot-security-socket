package com.project.demo.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Role;
import com.project.demo.entity.User;
import com.project.demo.entity.UserRole;
import com.project.demo.repository.RoleRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.repository.UserRoleRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	public boolean isExisting(int userId, int roleId) {

		UserRole check = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
		if (check == null) {
			return false;
		}
		return true;
	}

	public ResponseEntity<ResponseObject> addUserRole(String username, Set<String> roles) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("Error", "Username is not invalid", ""));
		}
		for (String i : roles) {
			Role role = roleRepository.findByRoleName(i);
			if (role == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("Error", "Role is not invalid", ""));
			}
			if (isExisting(user.getId(), role.getId())) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ResponseObject("Error",
						"The " + username + " that has been existed the role " + roles, ""));
			}
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);

			userRoleRepository.save(userRole);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("Success", "The " + username + " that has been added the role " + roles, ""));
	}

	public ResponseEntity<ResponseObject> deleteUserRole(String username, Set<String> roles) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("Error", "Username is not invalid", ""));
		}
		for (String i : roles) {
			Role role = roleRepository.findByRoleName(i);
			if (role == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("Error", "Role is not invalid", ""));
			}
			if (!isExisting(user.getId(), role.getId())) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
						new ResponseObject("Error", "The " + username + " that has't existed the role " + roles, ""));
			}

			UserRole ur = userRoleRepository.findByUserIdAndRoleId(user.getId(), role.getId());
			userRoleRepository.delete(ur);
		}
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Success", "The " + username + " that has been deleted the role " + roles, ""));
	}
}
