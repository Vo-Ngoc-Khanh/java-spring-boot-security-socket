package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.entity.UserComment;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Integer>{

	@Query("select uc from UserComment uc where uc.post.id =?1")
	List<UserComment> findByPostId(int postId);

	
	@Query("select uc from UserComment uc where uc.id = ?1 and uc.user.id =?2")
	UserComment findByIdAndUserId(int commentId, int id);

	@Query("select uc from UserComment uc where uc.user.id =?1")
	List<UserComment> findByUserId(int userId);
	
	
}
