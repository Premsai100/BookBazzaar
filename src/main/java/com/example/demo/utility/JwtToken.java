package com.example.demo.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtToken {
	
	final String key ="MyNewSuperSecretKey1234567890!@#$%";
	final SecretKey sec = Keys.hmacShaKeyFor(key.getBytes());
	public String generateToken(String name) {
		
		
		return Jwts.builder()
			.setSubject(name)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
			.signWith(sec, SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims extractToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(sec)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractUserName(String token) {
		return extractToken(token).getSubject();
	}

	public boolean validate(String userName, UserDetails usd,String token) {
		return (userName.equals(usd.getUsername())) && (!isExpired(token));
	}

	private boolean isExpired(String token) {
		return extractToken(token).getExpiration().before(new Date());
	}
}
