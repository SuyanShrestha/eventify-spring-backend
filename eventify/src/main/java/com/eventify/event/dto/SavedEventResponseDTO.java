package com.eventify.event.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavedEventResponseDTO {
    private Long id;
    private LocalDateTime savedAt;
    private EventResponseDTO eventDetails;
}