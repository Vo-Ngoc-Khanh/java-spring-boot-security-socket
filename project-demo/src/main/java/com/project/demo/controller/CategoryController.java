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
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.Category;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.CategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public List<Category> get(){
		return categoryService.get();
	}
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable("id") int id){
		return categoryService.getById(id);
	}
	
	@PostMapping("/categories")
	public ResponseEntity<ResponseObject> addNew(@RequestBody Category category){
		return categoryService.addNew(category);
	}
	
	@PutMapping("/categories/{id}")
	public ResponseEntity<ResponseObject> update(@RequestBody Category category, @PathVariable("id") int id){
		return categoryService.update(category, id);
	}
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<ResponseObject> delete(@PathVariable("id")int id){
		return categoryService.delete(id);
	}
}
