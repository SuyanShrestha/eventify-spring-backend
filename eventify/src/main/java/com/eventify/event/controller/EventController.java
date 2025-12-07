package com.eventify.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.event.dto.EventCategoryDTO;
import com.eventify.event.dto.EventRequestDTO;
import com.eventify.event.dto.EventResponseDTO;
import com.eventify.event.dto.SavedEventResponseDTO;
import com.eventify.event.service.EventCategoryService;
import com.eventify.event.service.EventService;
import com.eventify.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
@Slf4j
public class EventController {


    private final EventService eventService;
    private final EventCategoryService eventCategoryService;
   
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents() {
        List<EventResponseDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<EventCategoryDTO>> getCategories()
    {
      List<EventCategoryDTO> categories = eventCategoryService.getCategories();
      return ResponseEntity.ok(categories);
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventResponseDTO>> getMyEvents(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        List<EventResponseDTO> events = eventService.getEventsByOrganizer(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<EventResponseDTO>> getMyBookings(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        List<EventResponseDTO> bookings = eventService.getMyBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/saved")
    public ResponseEntity<List<SavedEventResponseDTO>> getSavedEvents(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<SavedEventResponseDTO> savedEvents = eventService.getSavedEventsForUser(userDetails.getUser().getId());
        return ResponseEntity.ok(savedEvents);
    }

    @PostMapping("/toggle-save/{eventId}")
    public ResponseEntity<Map<String, String>> toggleSaveEvent(
            @PathVariable Long eventId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        String message = eventService.toggleSaveEvent(eventId, userId);

        return ResponseEntity.ok(Map.of("detail", message));
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> save(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody EventRequestDTO event) {
        Long userId = userDetails.getUser().getId();
        EventResponseDTO saved = eventService.save(event, userId);
        return ResponseEntity.ok(saved);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> update(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody EventRequestDTO dto) {

        Long userId = userDetails.getUser().getId();
        EventResponseDTO updated = eventService.update(id, dto, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails user) {
        eventService.deleteEvent(id, user.getUser().getId());
        return ResponseEntity.noContent().build();
    }


}
