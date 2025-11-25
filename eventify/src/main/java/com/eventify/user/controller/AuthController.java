package com.eventify.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.user.model.User;
import com.eventify.user.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/user")
public class AuthController {
   

    @Autowired
    private UserService userService; 
    
	@PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Logged in ");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/save")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

}
