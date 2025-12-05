package com.eventify.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eventify.core.enums.UserRole;
import com.eventify.event.model.Event;
import com.eventify.event.model.SavedEvent;
import com.eventify.feedback.model.Feedback;
import com.eventify.notification.model.Notification;
import com.eventify.payment.model.Payment;
import com.eventify.rsvp.model.RSVP;
import com.eventify.ticket.model.Ticket;

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

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    private LocalDateTime lastLogin = LocalDateTime.now();

    @Builder.Default
    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> organizedEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RSVP> rsvps;

    @OneToMany(mappedBy = "user")
    private List<SavedEvent> savedEvents;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();



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
