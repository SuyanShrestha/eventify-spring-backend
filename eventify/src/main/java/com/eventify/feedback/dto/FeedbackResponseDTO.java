package com.eventify.feedback.dto;

import java.time.LocalDateTime;

import com.eventify.user.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {
    private Long id;
    private Long eventId;
    private UserDTO user;
    private String message;
    private LocalDateTime createdAt;
}
