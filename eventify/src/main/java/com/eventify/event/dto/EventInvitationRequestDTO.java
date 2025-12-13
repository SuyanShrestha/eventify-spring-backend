package com.eventify.event.dto;

import java.util.List;

import lombok.Data;

@Data
public class EventInvitationRequestDTO {
    private Long eventId;
    private List<String> email;
}