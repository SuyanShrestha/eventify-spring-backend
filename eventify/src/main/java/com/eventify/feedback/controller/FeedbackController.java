package com.eventify.feedback.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.feedback.dto.FeedbackRequestDTO;
import com.eventify.feedback.dto.FeedbackResponseDTO;
import com.eventify.feedback.service.FeedbackService;
import com.eventify.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final FeedbackService feedbackService;
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbacksForEvent(
            @PathVariable Long eventId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        List<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksForEvent(eventId, userId);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/event/{eventId}")
    public ResponseEntity<FeedbackResponseDTO> createFeedback(
            @PathVariable Long eventId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody FeedbackRequestDTO request
    ) {
        Long userId = userDetails.getUser().getId();
        FeedbackResponseDTO feedback = feedbackService.createFeedback(eventId, userId, request);
        return ResponseEntity.ok(feedback);
    }
}
