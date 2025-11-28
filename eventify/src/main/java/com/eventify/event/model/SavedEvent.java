package com.eventify.event.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.eventify.user.model.User;

@Entity
@Table(
    name = "saved_events"
)
public class SavedEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "saved_at", insertable = false, updatable = false)
    private LocalDateTime savedAt;

}
