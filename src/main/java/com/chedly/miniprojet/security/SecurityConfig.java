package com.chedly.miniprojet.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
	{ 		
		 http.csrf().disable()
		    .sessionManagement()
	          .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	          
	          .cors().configurationSource(new CorsConfigurationSource() {
	 			 @Override
	 			 public CorsConfiguration getCorsConfiguration(HttpServletRequest 
	 			request) {
	 			 CorsConfiguration config = new CorsConfiguration();
	 			 
	 			config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	 			 config.setAllowedMethods(Collections.singletonList("*"));
	 			 config.setAllowCredentials(true);
	 			 config.setAllowedHeaders(Collections.singletonList("*"));
	 			 config.setExposedHeaders(Arrays.asList("Authorization"));
	 			 config.setMaxAge(3600L);
	 			 return config;
	 			 }
	 			 }).and()
	          
	          	          
		    .authorizeHttpRequests()
				//.requestMatchers("/api/employees/**").hasAnyAuthority("ADMIN","USER")
				// consulter un produit par son id
		        .requestMatchers(HttpMethod.GET, "/api/departments").hasAnyAuthority("ADMIN","USER")
		        .requestMatchers(HttpMethod.GET, "/api/departments/getemp/{name}").hasAnyAuthority("ADMIN","USER")
		        .requestMatchers(HttpMethod.DELETE, "/api/departments/deletedep/{id}").hasAnyAuthority("ADMIN","USER")
		        .requestMatchers(HttpMethod.PUT, "/api/departments/updatedep/{id}").hasAnyAuthority("ADMIN","USER")
		        .requestMatchers(HttpMethod.POST, "/api/departments/adddep").hasAnyAuthority("ADMIN","USER")
		  
		        
				.requestMatchers(HttpMethod.GET, "/api/employees").hasAnyAuthority("ADMIN", "USER")
				.requestMatchers(HttpMethod.GET, "/api/employees/getemp/{id}").hasAnyAuthority("ADMIN","USER")
				.requestMatchers(HttpMethod.GET, "/api/employees/getname/{name}").hasAnyAuthority("ADMIN","USER")
				.requestMatchers(HttpMethod.POST, "/api/employees/addemp").hasAnyAuthority("ADMIN","USER")
				
				.requestMatchers(HttpMethod.PUT, "/api/employees/updateemp").hasAnyAuthority("ADMIN","USER")
				
				.requestMatchers(HttpMethod.DELETE, "/api/employees/deleteemp/{id}").hasAnyAuthority("ADMIN","USER")
				.anyRequest()
				.authenticated().and()
				.addFilterBefore(new JWTAuthorizationFilter(), BasicAuthenticationFilter.class);
		return http.build();

	}

}