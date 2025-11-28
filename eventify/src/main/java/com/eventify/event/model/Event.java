package com.eventify.event.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventify.event.enums.EventMode;
import com.eventify.user.model.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "organizer_id")
    private User organizer;

    @ManyToOne
    @JoinColumn (name = "category_id")
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

     @Column (name = "is_free")
    private boolean freeEvent;      // More idiomatic name
    private BigDecimal ticketPrice;

    @Column (name = "is_approved")
    private boolean approved;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

}
