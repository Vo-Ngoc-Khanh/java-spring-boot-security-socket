package com.project.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.demo.entity.UserComment;
import com.project.demo.response.ResponseObject;

@Service
public interface UserCommentService {

    
    public List<UserComment> getCommentsByPostId(int postId);

    public List<UserComment> getCommentsByUserId(String username);
    
    //public ResponseEntity<ResponseObject> createComment(UserComment userComment);
     
    public ResponseEntity<ResponseObject> deleteComment(int commentId, String username);

	public ResponseEntity<ResponseObject> createComment(UserComment userComment, int postId, String username);
    
}
