package com.eventify.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventify.security.JwtService;
import com.eventify.user.dto.LoginRequestDto;
import com.eventify.user.dto.LoginResponseDto;
import com.eventify.user.dto.RegisterRequestDto;
import com.eventify.user.dto.RegisterResponseDto;
import com.eventify.user.model.User;
import com.eventify.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateAccessToken(user);

        log.debug("principal user in login: {} and token: {}", user, token);

        return new LoginResponseDto(token, user.getId());
    }

    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        User user = userRepository.findByEmail(registerRequestDto.getEmail()).orElse(null);
        if(user != null) throw new IllegalArgumentException("User already exists");

        User savedUser = userRepository.save(User.builder()
                            .email(registerRequestDto.getEmail())
                            .username(registerRequestDto.getUsername())
                            .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                            .build());

        String token = jwtService.generateAccessToken(savedUser);

        log.debug("saved user in register: {} and token: {}", savedUser, token);

        return new RegisterResponseDto(token, savedUser.getId());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

}
