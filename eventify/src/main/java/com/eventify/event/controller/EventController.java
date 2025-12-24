package com.eventify.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eventify.event.dto.EventInvitationRequestDTO;
import com.eventify.event.dto.EventCategoryDTO;
import com.eventify.event.dto.EventDetailResponseDTO;
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
    public ResponseEntity<List<EventResponseDTO>> getEvents(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        List<EventResponseDTO> events = eventService.getAllEvents(userId);
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
        Long userId = userDetails.getUserId();
        List<EventResponseDTO> events = eventService.getEventsByOrganizer(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<EventResponseDTO>> getMyBookings(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        List<EventResponseDTO> bookings = eventService.getMyBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/saved")
    public ResponseEntity<List<SavedEventResponseDTO>> getSavedEvents(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<SavedEventResponseDTO> savedEvents = eventService.getSavedEventsForUser(userDetails.getUserId());
        return ResponseEntity.ok(savedEvents);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailResponseDTO> getEventDetails(
            @PathVariable Long eventId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        EventDetailResponseDTO dto = eventService.getEventDetails(eventId, userId);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/toggle-save/{eventId}")
    public ResponseEntity<Map<String, String>> toggleSaveEvent(
            @PathVariable Long eventId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        String message = eventService.toggleSaveEvent(eventId, userId);

        return ResponseEntity.ok(Map.of("detail", message));
    }

    // accepts images, so modelAttribute, and requestPart for files
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDTO> save(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @ModelAttribute EventRequestDTO event,
        @RequestPart(value = "bannerFile", required = false) MultipartFile bannerFile
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        EventResponseDTO saved = eventService.save(event, bannerFile, userId);
        return ResponseEntity.ok(saved);
        
    }

    @PostMapping("/send-invitation")
    public ResponseEntity<String> sendInvitations(@RequestBody EventInvitationRequestDTO request) {
        eventService.sendInvitations(request);
        return ResponseEntity.ok("Invitations sent");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDTO> update(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute EventRequestDTO dto,
            @RequestPart(value = "bannerFile", required = false) MultipartFile bannerFile) {

        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        EventResponseDTO updated = eventService.update(id, dto, bannerFile, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails user) {
        eventService.deleteEvent(id, user.getUserId());
        return ResponseEntity.noContent().build();
    }


}
