package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Post;
import com.project.demo.entity.User;
import com.project.demo.entity.UserComment;
import com.project.demo.repository.PostRepository;
import com.project.demo.repository.UserCommentRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.response.ResponseObject;
import com.project.demo.service.UserCommentService;

@Service
public class UserCommentServiceImpl implements UserCommentService{

    @Autowired
    private UserCommentRepository userCommentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;

	@Override
	public List<UserComment> getCommentsByPostId(int postId) {
	    List<UserComment> comments = userCommentRepository.findByPostId(postId);
	    return comments;
	}

	@Override
	public ResponseEntity<ResponseObject> createComment(UserComment userComment,int postId, String username) {	
		User user = userRepository.findByUsername(username);
		Optional<Post> post = postRepository.findById(postId);
		try {
			if(post.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ResponseObject("Error","Post not found", userComment));	
			}
			userComment.setPost(post.get());
			userComment.setUser(user);
		    userCommentRepository.save(userComment);
		    return ResponseEntity.status(HttpStatus.OK).body( new ResponseObject("Success", "Comment The Post id = "+userComment.getPost().getId()+(" Successfully"), ""));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ResponseObject("Error", e.getMessage(), userComment));
		}
    	    
	}

	@Override
	public ResponseEntity<ResponseObject> deleteComment(int commentId, String username) {

		Optional<UserComment> comment = userCommentRepository.findById(commentId);
		
		if(comment.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Comment is not valid.", ""));
		}

		User user = userRepository.findByUsername(username);
		
		UserComment checkUerComment = userCommentRepository.findByIdAndUserId(commentId, user.getId());
		
		if(checkUerComment == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "You are not the author of this comment", ""));
		}
		userCommentRepository.delete(checkUerComment);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "The comment is deleted success.", ""));
	}

	@Override
	public List<UserComment> getCommentsByUserId(String username) {
		User user = userRepository.findByUsername(username);
		List<UserComment> comments = userCommentRepository.findByUserId(user.getId());
	    return comments;
	}

}
