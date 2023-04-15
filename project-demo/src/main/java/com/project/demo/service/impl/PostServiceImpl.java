package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Post;
import com.project.demo.repository.PostRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> getAll() {

		return postRepository.findAll();
	}

	public Page<Post> getPostByNoActive(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		return postRepository.findByNoActive(pageable);
	}

	public Page<Post> getPostByActive(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		return postRepository.findByActive(pageable);
	}

	public ResponseEntity<ResponseObject> getByIdAndActice(int id) {

		Optional<Post> check_existed = postRepository.findByIdAndActive(id);
		if (check_existed.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Found post", check_existed.get()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("404", "Post not found ", ""));
	}

	public ResponseEntity<ResponseObject> delete(int id) {
		try {
			Optional<Post> post = postRepository.findById(id);
			if(post.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "The post not found", ""));			
			}
			post.get().setDeletePost(true);
			postRepository.save(post.get());
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "This post delete = true", ""));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error", e.getMessage(), ""));
		}

	}

	@Override
	public ResponseEntity<ResponseObject> getById(int id) {
		Optional<Post> check_existed = postRepository.findById(id);
		if (check_existed.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("Success", "Found post", check_existed.get()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("404", "Post not found ", ""));
	}

}
