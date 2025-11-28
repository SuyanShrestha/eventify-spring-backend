package com.eventify.event.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventify.event.enums.EventMode;
import com.eventify.user.model.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User organizer;

    @ManyToOne
    private EventCategory category;

    private String banner;          // File path or URL
    private String title;
    private String subtitle;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Enumerated(EnumType.STRING)
    private EventMode eventType;

    private String venue;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime bookingDeadline;

    private Integer totalTickets;

    private boolean freeEvent;      // More idiomatic name
    private BigDecimal ticketPrice;

    private boolean approved;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

}
