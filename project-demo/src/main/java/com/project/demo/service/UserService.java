package com.project.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.demo.entity.User;
import com.project.demo.response.ResponseObject;

public interface UserService {

	public List<User> get();

	public ResponseEntity<ResponseObject> findUserNameByRole(String role);

	public ResponseEntity<ResponseObject> getbyId(int id);

	public ResponseEntity<ResponseObject> addNew(User user);

	public ResponseEntity<ResponseObject> update(User user, int id);

	public ResponseEntity<ResponseObject> delete(int id);

	public ResponseEntity<ResponseObject> un_delete(int id);

}
