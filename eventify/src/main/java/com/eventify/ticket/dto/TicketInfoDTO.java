package com.eventify.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfoDTO {

    private String eventName;
    private String ticketCode;
    private Integer ticketQuantity;
    private String ticketStatus;
    private LocalDateTime purchaseDate;
    private String attendeeName;
    private LocalDateTime checkInTime;
}
