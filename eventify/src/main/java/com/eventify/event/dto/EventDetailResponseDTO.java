package com.eventify.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.eventify.event.enums.EventMode;
import com.eventify.feedback.dto.FeedbackResponseDTO;
import com.eventify.ticket.dto.BookingDTO;
import com.eventify.user.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailResponseDTO {

    private Long id;
    private String banner;
    private String title;
    private String subtitle;
    private String details;
    private EventMode eventType;
    private Boolean isFree;
    private BigDecimal ticketPrice;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime bookingDeadline;
    private String venue;

    private EventCategoryDTO categoryDetails;
    private Integer totalTickets;
    private Integer ticketsAvailable;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserDTO organizer;

    private boolean isUpcoming;
    private boolean isActive;
    private boolean isExpired;

    private AttendeesDTO attendees;
    private boolean isSaved;
    private List<FeedbackResponseDTO> feedbacks;
    private List<BookingDTO> bookings;
}
