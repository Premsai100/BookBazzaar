package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Users;
import com.example.demo.configuration.dto.LoginDto;
import com.example.demo.repos.UserReposi;
import com.example.demo.security.Secconfig;
import com.example.demo.utility.JwtToken;

@RestController
@RequestMapping("/auth")
public class AuthControll {
	
	@Autowired
	UserReposi userepo;
	
	@Autowired
	PasswordEncoder passencode;
	
	@Autowired
	AuthenticationManager authenticatemanager;
	
	@Autowired
	JwtToken jwt;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Users user){
		if(userepo.existsByname(user.getName())) {
			return new ResponseEntity<>("user already exists",HttpStatus.BAD_REQUEST);
		}
		
		user.setPassword(passencode.encode(user.getPassword()));
		userepo.save(user);
		
		return new ResponseEntity<>("registered successfully",HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDto loginreq ){
		try {
			authenticatemanager.authenticate(
					new UsernamePasswordAuthenticationToken(loginreq.getName(),loginreq.getPassword())
		);
		return jwt.generateToken(loginreq.getName());
		
		
		}catch(BadCredentialsException ex) {
			
			return "Unauthorized";
		}
		
	
}
}
