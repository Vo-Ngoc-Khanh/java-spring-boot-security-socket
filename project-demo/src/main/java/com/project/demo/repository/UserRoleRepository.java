package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

	@Query("SELECT ur FROM UserRole ur WHERE ur.user.id = ?1 AND ur.role.id = ?2")
    UserRole findByUserIdAndRoleId(Integer userId, Integer roleId);
	
	void deleteByUserId(Integer userId);
}
