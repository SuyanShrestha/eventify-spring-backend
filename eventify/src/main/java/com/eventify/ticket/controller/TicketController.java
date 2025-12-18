package com.eventify.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.security.CustomUserDetails;
import com.eventify.ticket.dto.BookingDTO;
import com.eventify.ticket.dto.BookingRequestDTO;
import com.eventify.ticket.dto.CheckinRequestDTO;
import com.eventify.ticket.dto.CheckinResponseDTO;
import com.eventify.ticket.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {
    
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> bookEvent(
            @RequestBody BookingRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        BookingDTO booking = bookingService.bookEvent(
                request.getEventId(),
                userId,
                request.getQuantity()
        );

        return ResponseEntity.ok(booking);
    }

    @PostMapping("/check-in")
    public ResponseEntity<CheckinResponseDTO> checkIn(
            @RequestBody CheckinRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        CheckinResponseDTO response = bookingService.checkIn(request, userId);
        return ResponseEntity.ok(response);
    }
}
