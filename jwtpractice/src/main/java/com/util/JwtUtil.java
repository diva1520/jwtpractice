package com.util;

import io.jsonwebtoken.security.Keys;
 
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 
@Component
public class JwtUtil {
	
	private final String SECRET = "my-secret-key-1234567890-my-secret-key";

	public String generateToken(Long userId , String userName) {
		
		return Jwts.builder()
				.setSubject(String.valueOf(userName))  
				.claim("userId", userId)
				.claim("username", userName)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String validateAndGetSubject(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token) 
                .getBody();

        return claims.getSubject();
    }
}
