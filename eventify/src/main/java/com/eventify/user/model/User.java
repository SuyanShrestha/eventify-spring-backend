package com.eventify.user.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor  //hibernate needs this
@AllArgsConstructor  //Builder needs this
@Builder
@Table(name = "users")
public class User {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 25)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    private String profilePicture;

    @Column(unique = true, length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    private Boolean isOrganizer = false;
    private Boolean isActive = true;
    private Boolean isStaff = false;
    private Boolean isSuperuser = false;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime lastLogin = LocalDateTime.now(); 
}
