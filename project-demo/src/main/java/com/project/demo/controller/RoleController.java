package com.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.Role;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.RoleService;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/roles")
	public List<Role> get(){
		return roleService.get();
	}
	
	@GetMapping("/roles/{id}")
	public ResponseEntity<ResponseObject> getbyId(@PathVariable int id){
		return roleService.getById(id);
	}
}
