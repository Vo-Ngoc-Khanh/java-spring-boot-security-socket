package com.project.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Post;
import com.project.demo.entity.PostReviewer;
import com.project.demo.entity.User;
import com.project.demo.repository.PostRepository;
import com.project.demo.repository.PostReviewerRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.PostReviewerService;

@Service
public class PostReviewerServiceImpl implements PostReviewerService{

	@Autowired
	private PostReviewerRepository postReviewerRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<ResponseObject> reviewerPost(int id, String username) {

		try {
			User user = userRepository.findByUsername(username);

			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseObject("Error", "Wrong!!!User is not invalid.", ""));
			}
			Optional<Post> post = postRepository.findById(id);
			if(post.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Wrong!!!Post is not invalid.", ""));
			}
			if(post.get().isActive()) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ResponseObject("Error", "Wrong!!!Post has been reviewed.", ""));
			}
			post.get().setActive(true);
			Post reviewer = postRepository.save(post.get());
			PostReviewer posReviewer = new PostReviewer(reviewer, user);			
			postReviewerRepository.save(posReviewer);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Review successful!!!", post.get()));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error",e.getMessage(), ""));
		}

	}
}
