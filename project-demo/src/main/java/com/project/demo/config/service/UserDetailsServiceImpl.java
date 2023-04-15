package com.project.demo.config.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project.demo.config.model.UserDetailsImpl;
import com.project.demo.entity.User;
import com.project.demo.repository.RoleRepository;
import com.project.demo.repository.UserRepository;


@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        List<String> roles = roleRepository.findRoleNamesByUserId(user.getId());
        
        return new  UserDetailsImpl(user.getUsername(), user.getPassword(),roles);
	}

}
