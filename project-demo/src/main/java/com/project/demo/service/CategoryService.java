package com.project.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.demo.entity.Category;
import com.project.demo.response.ResponseObject;

public interface CategoryService {

	public List<Category> get();

	public ResponseEntity<ResponseObject> getById(int id);

	public ResponseEntity<ResponseObject> addNew(Category category);

	public ResponseEntity<ResponseObject> update(Category category, int id);

	public ResponseEntity<ResponseObject> delete(int id);

}
