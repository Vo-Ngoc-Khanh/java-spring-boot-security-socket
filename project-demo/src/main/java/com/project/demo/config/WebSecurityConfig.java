package com.project.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.demo.config.service.UserDetailsServiceImpl;
import com.project.demo.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth

				.requestMatchers("auth/login","auth/register").permitAll()
				
				.requestMatchers("auth/profile","/api/v1/comments/**").hasAnyAuthority("user", "admin", "poster", "reviewer")
				
				.requestMatchers("/api/v1/categories/**","/api/v1/roles/**","/api/v1/users/**","/api/v1/user-roles/**").hasAuthority("admin")	
			
				.requestMatchers("/api/v1/poster/**").hasAuthority("poster")
				.requestMatchers("/api/v1/posts/reviewer/**").hasAuthority("reviewer")
				.requestMatchers("/api/v1/posts/all/**").hasAuthority("admin")	
				.requestMatchers(HttpMethod.GET, "/api/v1/posts","/api/v1/posts/{id}","/api/v1/posts/{id}/comments").permitAll()	
				.requestMatchers(HttpMethod.POST, "/api/v1/posts/{id}/comments").hasAuthority("user")
				.requestMatchers(HttpMethod.POST, "/api/v1/posts").hasAuthority("admin")
				.requestMatchers(HttpMethod.PUT, "/api/v1/posts/{id}").hasAuthority("admin")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/posts/{id}").hasAuthority("admin")

				
				.anyRequest().authenticated())		
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				//.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				
				.exceptionHandling()
	            .authenticationEntryPoint((request, response, authException) -> {
	                response.setStatus(HttpStatus.UNAUTHORIZED.value());
	                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	                response.getWriter().write("{\"message\": \"Unauthorized access.\"}");
	            })
	            .accessDeniedHandler((request, response, accessDeniedException) -> {
	                response.setStatus(HttpStatus.FORBIDDEN.value());
	                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	                response.getWriter().write("{\"message\": \"Access denied.\"}");
	            });
		return http.build();

	}

	@Bean
	public AuthenticationManager authentiManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
