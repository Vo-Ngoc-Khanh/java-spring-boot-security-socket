package com.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.UserComment;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.UserCommentService;

@RestController
@RequestMapping("/api/v1")
public class UserCommentController {
 
	@Autowired
	private UserCommentService userCommentService;

	@PostMapping("/posts/{id}/comments")
	public ResponseEntity<ResponseObject> createComment(@PathVariable("id") int postId,
			@RequestBody UserComment userComment,
			@AuthenticationPrincipal UserDetails userDetails) {
		
	    return userCommentService.createComment(userComment,postId, userDetails.getUsername());
	}
	
	@GetMapping("/posts/{id}/comments")
	public List<UserComment> getCommentsByPostId(@PathVariable("id") int postId) {
	    return userCommentService.getCommentsByPostId(postId);
	}
	
	@GetMapping("/comments")
	public List<UserComment> getCommentsByUserId(@AuthenticationPrincipal UserDetails userDetails) {
	    return userCommentService.getCommentsByUserId(userDetails.getUsername());
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ResponseObject> deleteComment(@PathVariable int commentId,
			@AuthenticationPrincipal UserDetails userDetails) {
		return userCommentService.deleteComment(commentId, userDetails.getUsername());
	}


}