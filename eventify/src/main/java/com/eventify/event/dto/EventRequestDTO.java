package com.eventify.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventify.event.enums.EventMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {

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

    private BigDecimal ticketPrice;

    private Integer categoryId;
    
}
