package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Role;
import com.project.demo.entity.User;
import com.project.demo.entity.UserRole;
import com.project.demo.repository.RoleRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.repository.UserRoleRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> get() {
		return userRepository.findAll();
	}

	public ResponseEntity<ResponseObject> findUserNameByRole(String role) {
		List<String> result_find = userRepository.findUserNameByRole(role);
		if (result_find == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "User not found", ""));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("Success", "List of users with roles " + role, result_find));
	}

	public ResponseEntity<ResponseObject> getbyId(int id) {
		Optional<User> find = userRepository.findById(id);
		if (find.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Found user", find.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "User not found", ""));
		}
	}

	public ResponseEntity<ResponseObject> addNew(User user) {
		User check_username = userRepository.findByUsername(user.getUsername());
		if (check_username == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);

			Optional<Role> role = roleRepository.findById(2);
			UserRole addUserRole = new UserRole(user, role.get());

			userRoleRepository.save(addUserRole);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Create new user success", ""));

		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(new ResponseObject("Error", "Username already exists.", ""));

	}

	public ResponseEntity<ResponseObject> update(User user, int id) {
		Optional<User> check_exist = userRepository.findById(id);

		if (check_exist == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("Error", "User is not invalid!!!", ""));
		}
		check_exist.get().setFullname(user.getFullname());

		check_exist.get().setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(check_exist.get());

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Update user success", ""));
	}

	// delete, lock
	public ResponseEntity<ResponseObject> delete(int id) {
		Optional<User> check_exist = userRepository.findById(id);

		if (check_exist == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("Error", "User is not invalid!!!", ""));
		}

		userRoleRepository.deleteByUserId(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success",
				"Successfully locked User [" + check_exist.get().getUsername() + "]", ""));
	}

	// unlock
	public ResponseEntity<ResponseObject> un_delete(int id) {
		Optional<User> check_exist = userRepository.findById(id);

		if (check_exist == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("Error", "User is not invalid!!!", ""));
		}

		userRoleRepository.deleteByUserId(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success",
				"Successfully locked User [" + check_exist.get().getUsername() + "]", ""));
	}

}
