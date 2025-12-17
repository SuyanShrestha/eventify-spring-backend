package com.eventify.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckinResponseDTO {
    private String detail;
    private TicketInfoDTO ticketInfo;
    
}
