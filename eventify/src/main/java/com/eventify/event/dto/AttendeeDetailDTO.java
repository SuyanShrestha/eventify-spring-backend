package com.eventify.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeDetailDTO {
    private Long userId;
    private String username;
    private int ticketCount;
    private boolean isCheckedIn;
}
