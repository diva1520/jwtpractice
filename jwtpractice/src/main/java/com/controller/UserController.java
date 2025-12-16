package com.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;


import com.dto.JwtResponse;
import com.dto.LoginRequest;
import com.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	JwtUtil jwtUtil;
	
    @PostMapping("/login")
	public JwtResponse login(@RequestBody LoginRequest request) {
    	Long userId = 101L;
    	String username = request.getUserName();


    	String token = jwtUtil.generateToken(userId, username);


    	return new JwtResponse(token);
	}
    
//    @PostMapping("/test/validate")
//    public Map<String, Object> validateToken(
//            @RequestHeader("Authorization") String authHeader) {
//
//        String token = authHeader.replace("Bearer ", "");
//
//        String sub = jwtUtil.validateAndGetSubject(token);
//
//        return Map.of(
//                "sub", sub
//        );
//    }
    
    @GetMapping("/test/validate")
    public Map<String, Object> test(Authentication authentication) {

        return Map.of(
            "sub", authentication.getPrincipal()
        );
    }

}
