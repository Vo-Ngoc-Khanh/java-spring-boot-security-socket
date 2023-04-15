package com.project.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.demo.entity.Role;
import com.project.demo.response.ResponseObject;

public interface RoleService {

	public List<Role> get();

	public ResponseEntity<ResponseObject> getById(int id);

}
