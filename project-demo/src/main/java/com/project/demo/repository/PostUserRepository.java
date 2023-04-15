package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.PostUser;

public interface PostUserRepository extends JpaRepository<PostUser, Integer>{

	@Query("select pu from PostUser pu where pu.user.id = ?1 and pu.post.id = ?2")
	PostUser findByUserIdAndPostId(Integer userId, Integer postId);

}
