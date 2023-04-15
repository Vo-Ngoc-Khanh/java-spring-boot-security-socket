package com.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.User;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> get() {
		return userService.get();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable("id") int id) {
		return userService.getbyId(id);
	}

	@PostMapping("/users")
	public ResponseEntity<ResponseObject> addNew(@RequestBody User user) {
		return userService.addNew(user);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ResponseObject> update(@RequestBody User user, @PathVariable("id") int id) {
		return userService.update(user, id);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ResponseObject> delete(@PathVariable("id") int id) {
		return userService.delete(id);
	}
	
	@GetMapping("/users/role")
	public ResponseEntity<ResponseObject> findUserNameByRole(@RequestParam("role-name") String role) {
		return userService.findUserNameByRole(role);
	}

}
