package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	@Query("SELECT r.roleName FROM Role r JOIN UserRole ur ON r.id = ur.role.id WHERE ur.user.id = ?1")
    List<String> findRoleNamesByUserId(Integer userId);
	
	Role findByRoleName(String roleName);
}
