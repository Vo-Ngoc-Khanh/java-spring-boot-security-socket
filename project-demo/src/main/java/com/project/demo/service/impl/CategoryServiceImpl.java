package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Category;
import com.project.demo.repository.CategoryRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> get() {
		return categoryRepository.findAll();
	}

	public ResponseEntity<ResponseObject> getById(int id) {
		Optional<Category> check_exist = categoryRepository.findById(id);
		if (check_exist.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Found category", check_exist.get()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("404", "Category not found ", ""));
	}

	public ResponseEntity<ResponseObject> addNew(Category category) {

		// check category_name
		Category check_category_name = categoryRepository.findByNameCategory(category.getNameCategory());
		if (check_category_name == null) {
			categoryRepository.save(category);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Add new category success.", ""));
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(new ResponseObject("Error", "Wrong!!!Category name already exists.", ""));
	}

	public ResponseEntity<ResponseObject> update(Category category, int id) {
		// check category_existed
		Optional<Category> check_existed = categoryRepository.findById(id);
		if (check_existed.isPresent()) {
			Category update = check_existed.get();
			update.setNameCategory(category.getNameCategory());
			update.setActive(category.isActive());
			categoryRepository.save(update);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Update category success.", ""));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseObject("Error", "Wrong!!!Not found category with id = " + id, ""));
	}

	public ResponseEntity<ResponseObject> delete(int id) {
		// check category_existed
		Optional<Category> check_existed = categoryRepository.findById(id);
		if (check_existed.isPresent()) {
			Category delete = check_existed.get();
			categoryRepository.delete(delete);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Delete category success.", ""));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseObject("Error", "Wrong!!!Not found category with id = " + id, ""));

	}

}
