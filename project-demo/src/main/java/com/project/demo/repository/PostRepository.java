package com.project.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

	
	@Query("select p from Post p where p.active = false and p.deletePost = false")
	Page<Post> findByNoActive(Pageable pageable);

	
	@Query("select p from Post p where p.active = true and p.deletePost = false")
	Page<Post> findByActive(Pageable pageable);
	
	@Query("select p from Post p where p.active = true and p.id = ?1 and p.deletePost = false")
	Optional<Post> findByIdAndActive(Integer id);
	
	@Query("select p from Post p, PostUser pu, User u "
	        + "where p.id = pu.post.id and u.id=pu.user.id and u.username=?1 and p.active =?2 and p.deletePost = false")
	List<Post> findByPoster(String username, boolean active);
	
	@Query("select p from Post p where p.deletePost = false")
	List<Post> findAll();
	
	@Query("select p from Post p where p.id = ?1 and p.deletePost = false")
	Optional<Post> findById(Integer id);
	
}
