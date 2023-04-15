package com.project.demo.repository;

import java.util.List;

import com.project.demo.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

	@Query("select u.username from "
			+ "User u, "
			+ "Role r, "
			+ "UserRole ur "
			+ "where u.id = ur.user.id "
			+ "and r.id = ur.role.id "
			+ "and r.roleName=?1")
	public List<String> findUserNameByRole(String role);

}
