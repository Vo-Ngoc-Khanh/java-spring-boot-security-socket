package com.project.demo.service;

import org.springframework.http.ResponseEntity;

import com.project.demo.entity.Post;
import com.project.demo.response.ResponseObject;

public interface PostUserService {
	
	public ResponseEntity<ResponseObject> getPostAwaitReviewer(String username);
	
	public ResponseEntity<ResponseObject> getPostReviewered(String username);

	public ResponseEntity<ResponseObject> addNew(Post post, String username);
	
	public ResponseEntity<ResponseObject> updatePost(Post post, int postId, String username);
	
	public ResponseEntity<ResponseObject> deletePost(int postId, String username);
}
