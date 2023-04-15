package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entity.PostReviewer;

public interface PostReviewerRepository extends JpaRepository<PostReviewer, Integer>{

}
