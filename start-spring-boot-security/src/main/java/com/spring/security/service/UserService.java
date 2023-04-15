package com.spring.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import com.spring.security.jwt.JwtService;
import com.spring.security.repository.RoleRepository;
import com.spring.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String login(User user){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						user.getEmail(), 
						user.getPassword())
				);
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getEmail());
		}else {
			throw new UsernameNotFoundException("Username is invaild!!!");
		}
	}

	public List<User>getListUser(){
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public Optional<User> findUser(int id) {
		return userRepository.findById(id);
	}
	
	public User add(User user) throws Exception {
	    boolean check_email = userRepository.existsByEmail(user.getEmail());
	    if (check_email) {
	        throw new Exception("Email already exists");
	    }

	    Role role = roleRepository.findById(user.getRole().getId())
	            .orElseThrow(() -> new Exception("Role not found"));
	    user.setRole(role);
	    user.setPassword(passwordEncoder.encode(user.getPassword()));

	    return userRepository.save(user);
	}
}
