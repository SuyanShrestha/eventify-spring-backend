package com.eventify.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.user.dto.LoginRequestDTO;
import com.eventify.user.dto.LoginResponseDTO;
import com.eventify.user.dto.RegisterRequestDTO;
import com.eventify.user.dto.RegisterResponseDTO;
import com.eventify.user.model.User;
import com.eventify.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {
   

    private final UserService userService; 
    
	@PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/save")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

}
