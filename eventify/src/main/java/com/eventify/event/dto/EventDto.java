package com.eventify.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventify.event.enums.EventMode;
import com.eventify.event.model.EventCategory;
import com.eventify.user.model.User;

public class EventDto {

    private User organizer;

    private EventCategory category;

    private String banner;          
    private String title;
    private String subtitle;

    private String details;

    private EventMode eventType;

    private String venue;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime bookingDeadline;

    private Integer totalTickets;

    private boolean freeEvent;     
    private BigDecimal ticketPrice;
}