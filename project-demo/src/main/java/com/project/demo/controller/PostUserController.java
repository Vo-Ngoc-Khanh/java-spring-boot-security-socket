package com.project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.Post;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.PostUserService;

@RestController
@RequestMapping("/api/v1")
public class PostUserController {

	@Autowired
	private PostUserService postUserService;
	
	@GetMapping("/poster/await")
	public ResponseEntity<ResponseObject> getPostAwaitReviewer(@AuthenticationPrincipal UserDetails userDetails) {
		return postUserService.getPostAwaitReviewer(userDetails.getUsername());
	}
	
	@GetMapping("/poster")
	public ResponseEntity<ResponseObject> getPostReviewed(@AuthenticationPrincipal UserDetails userDetails) {
		return postUserService.getPostReviewered(userDetails.getUsername());
	}
	
	@PostMapping("/poster")
	public ResponseEntity<ResponseObject> addNew(@RequestBody Post post,
			@AuthenticationPrincipal UserDetails userDetails) {
		return postUserService.addNew(post, userDetails.getUsername());
	}

	@PutMapping("/poster/{id}")
	public ResponseEntity<ResponseObject> updatePost(@RequestBody Post post, @PathVariable int id,
			@AuthenticationPrincipal UserDetails userDetails) {
		return postUserService.updatePost(post, id, userDetails.getUsername());
	}

	@DeleteMapping("/poster/{id}")
	public ResponseEntity<ResponseObject> deletePost(@PathVariable int id,
			@AuthenticationPrincipal UserDetails userDetails) {
		return postUserService.deletePost(id, userDetails.getUsername());
	}
}
