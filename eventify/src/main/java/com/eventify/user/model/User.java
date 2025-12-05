package com.eventify.user.model;

import java.time.LocalDateTime;

import com.eventify.core.enums.UserRole;

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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime lastLogin = LocalDateTime.now();

    public boolean isOrganizer() {
        return this.role == UserRole.ORGANIZER;
    }

    public boolean isStaff() {
        return this.role == UserRole.STAFF;
    }

    public boolean isSuperuser() {
        return this.role == UserRole.SUPERUSER;
    }


}
