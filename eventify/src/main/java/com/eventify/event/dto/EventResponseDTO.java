package com.eventify.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {

    private Long id;
    private String banner;
    private String title;
    private String subtitle;
    private String eventType;

    private boolean freeEvent;
    private BigDecimal ticketPrice;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime bookingDeadline;

    private String venue;

    private EventCategoryDTO categoryDetails;

    private Integer ticketsAvailable;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private OrganizerDTO organizer;

    private boolean isUpcoming;
    private boolean isActive;
    private boolean isExpired;

    private int attendeesCount;

    private boolean isSaved;
}
