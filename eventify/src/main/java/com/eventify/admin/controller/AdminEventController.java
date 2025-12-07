package com.eventify.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.event.dto.EventResponseDTO;
import com.eventify.event.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    
    private final EventService eventService;

    @GetMapping("/pending")
    public List<EventResponseDTO> getPendingEvents() {
        return eventService.getPendingEvents();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<EventResponseDTO> approveEvent(@PathVariable Long id) {
        EventResponseDTO event = eventService.approveEvent(id);
        return ResponseEntity.ok(event);
    }
}
