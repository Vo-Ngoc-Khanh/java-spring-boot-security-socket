package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Category;
import com.project.demo.entity.Post;
import com.project.demo.entity.PostUser;
import com.project.demo.entity.User;
import com.project.demo.repository.CategoryRepository;
import com.project.demo.repository.PostRepository;
import com.project.demo.repository.PostUserRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.PostUserService;

@Service
public class PostUserServiceImpl implements PostUserService{

	@Autowired
	private PostUserRepository postUserRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;


	
	public ResponseEntity<ResponseObject> addNew(Post post, String username) {
		try {
			User check_username = userRepository.findByUsername(username);
			if(check_username == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Username not found!!!", ""));
			}
			Optional<Category> check_category = categoryRepository.findById(post.getCategory().getId());
		    
			if(check_category.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Wrong!!!Category is not valid.", ""));
			}
			post.setCategory(check_category.get());
			
			post.setDeletePost(true);
			postRepository.save(post);
			
			PostUser postUser = new PostUser(post, check_username);
			postUserRepository.save(postUser);
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Add new post success", ""));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error", e.getMessage(), ""));
		}
	}
	

	@Override
	public ResponseEntity<ResponseObject> updatePost(Post post, int postId, String username) {
		try {
			//check post exist
			Optional<Post> postExisting = postRepository.findById(postId);
			
			if(postExisting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "The Post is not valid.", ""));
			}
			// check user is poster of this post
			User checkUser = userRepository.findByUsername(username);
			PostUser checkPostUser = postUserRepository.findByUserIdAndPostId(checkUser.getId(), postId);
			if(checkPostUser == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "We're sorry, but you cannot edit a post that was not created by you. Please contact the original author or a moderator if you believe changes need to be made.", ""));
			}
			//update
			postExisting.get().setTitle(post.getTitle());
			postExisting.get().setCategory(post.getCategory());
			postExisting.get().setContent(post.getContent());
			postExisting.get().setDescription(post.getDescription());
			
			postRepository.save(postExisting.get()); 
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "The post is updated success.", ""));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error", e.getMessage(), ""));
		}
	}

	@Override
	public ResponseEntity<ResponseObject> deletePost(int postId, String username) {
		try {
			Optional<Post> postExisting = postRepository.findById(postId);			
			if(postExisting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "The Post is not valid.", ""));
			}
			User checkUser = userRepository.findByUsername(username);
			PostUser checkPostUser = postUserRepository.findByUserIdAndPostId(checkUser.getId(), postId);
			if(checkPostUser == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "We're sorry, but you cannot edit a post that was not created by you. Please contact the original author or a moderator if you believe changes need to be made.", ""));
			}			
			Post update = postExisting.get();
			update.setDeletePost(true);
			postRepository.save(update);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "This post delete = true", ""));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error", e.getMessage(), ""));
		}
	}


	@Override
	public ResponseEntity<ResponseObject> getPostAwaitReviewer(String username) {
		
		List<Post> lstPost = postRepository.findByPoster(username,false);
		if(lstPost == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "You currently have no posts!", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "You post await reviewer here.", lstPost));
	}


	@Override
	public ResponseEntity<ResponseObject> getPostReviewered(String username) {
		List<Post> lstPost = postRepository.findByPoster(username,true);
		if(lstPost == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "You currently have no posts!", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "You post reviewed here.", lstPost));
	}



}
