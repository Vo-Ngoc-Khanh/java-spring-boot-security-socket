package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findByNameCategory(String nameCategory);

}
