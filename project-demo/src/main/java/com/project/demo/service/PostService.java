package com.project.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.project.demo.entity.Post;
import com.project.demo.response.ResponseObject;

public interface PostService {
	
	public List<Post> getAll();

	public Page<Post> getPostByNoActive(int page, int size);

	public Page<Post> getPostByActive(int page, int size);

	public ResponseEntity<ResponseObject> getByIdAndActice(int id);
	
	public ResponseEntity<ResponseObject> getById(int id);
	
	public ResponseEntity<ResponseObject> delete(int id);
}
