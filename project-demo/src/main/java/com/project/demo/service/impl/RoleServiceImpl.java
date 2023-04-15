package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Role;
import com.project.demo.repository.RoleRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<Role> get() {
		return roleRepository.findAll();
	}

	public ResponseEntity<ResponseObject> getById(int id) {
		Optional<Role> check_exist = roleRepository.findById(id);
		if (check_exist.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Found role", check_exist.get()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Role not found!!!", ""));
	}
}
