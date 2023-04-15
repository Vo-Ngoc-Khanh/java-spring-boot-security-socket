package com.project.demo.service;

import org.springframework.http.ResponseEntity;

import com.project.demo.response.ResponseObject;

public interface PostReviewerService {

	public ResponseEntity<ResponseObject> reviewerPost(int id, String username);
}
