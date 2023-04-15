package com.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.Post;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.PostReviewerService;
import com.project.demo.service.PostService;
import com.project.demo.service.PostUserService;

@RestController
@RequestMapping("/api/v1")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private PostUserService postUserService;

	@Autowired
	private PostReviewerService postReviewerService;

	@GetMapping("/posts/all")
	public List<Post> getAll() {
		return postService.getAll();
	}
	
	@GetMapping("/posts/all/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable("id") int id) {
		return postService.getById(id);
	}
	
	@GetMapping("/posts")
	public Page<Post> getActice(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return postService.getPostByActive(page, size);
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<ResponseObject> getByIdAndActice(@PathVariable("id") int id) {
		return postService.getByIdAndActice(id);
	}

	@PostMapping("/posts")
	public ResponseEntity<ResponseObject> addNew(@RequestBody Post post,
			@AuthenticationPrincipal UserDetails userDetails) {
		return postUserService.addNew(post, userDetails.getUsername());
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<ResponseObject> updatePost(@RequestBody Post post, @PathVariable int id,
			@AuthenticationPrincipal UserDetails userDetails) {
		return postUserService.updatePost(post, id, userDetails.getUsername());
	}
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<ResponseObject> delete(@PathVariable("id") int id) {
		return postService.delete(id);
	}
	
	@GetMapping("/posts/reviewer")
	public Page<Post> getPostNoActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		return postService.getPostByNoActive(page, size);
	}

	@PutMapping("/posts/reviewer/{id}")
	public ResponseEntity<ResponseObject> reviewPost(@PathVariable("id") int id,
			@AuthenticationPrincipal UserDetails userDetails) {
		return postReviewerService.reviewerPost(id, userDetails.getUsername());
	}
}
