package com.eventify.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eventify.core.email.EmailService;
import com.eventify.core.email.EmailTemplates;
import com.eventify.core.email.dto.EmailDTO;
import com.eventify.security.CustomUserDetails;
import com.eventify.security.JwtService;
import com.eventify.user.dto.LoginRequestDTO;
import com.eventify.user.dto.LoginResponseDTO;
import com.eventify.user.dto.RegisterRequestDTO;
import com.eventify.user.dto.RegisterResponseDTO;
import com.eventify.user.model.User;
import com.eventify.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final EmailService emailService;
    private final  AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${frontend.base.url}")
    private String frontendBaseUrl;


    public LoginResponseDTO login(LoginRequestDTO loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
        User user = cud.getUser();

        String token = jwtService.generateAccessToken(user);

        return new LoginResponseDTO(token, user.getId());
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDto) {
        User user = userRepository.findByEmail(registerRequestDto.getEmail()).orElse(null);
        if(user != null) throw new IllegalArgumentException("User already exists");

        User savedUser = userRepository.save(User.builder()
                            .email(registerRequestDto.getEmail())
                            .username(registerRequestDto.getUsername())
                            .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                            .build());

        String token = jwtService.generateAccessToken(savedUser);

        // send email to notify new user
        EmailDTO email = EmailTemplates.welcomeEmail(
                registerRequestDto.getEmail(),
                registerRequestDto.getUsername(),
                frontendBaseUrl + "/dashboard"
        );
        emailService.send(email);

        return new RegisterResponseDTO(token, savedUser.getId());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
