package com.eventify.feedback.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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

    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedbackResponseDTO> getFeedback(
            @PathVariable Long feedbackId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        FeedbackResponseDTO dto = feedbackService.getFeedbackById(feedbackId, userDetails.getUser().getId());
        return ResponseEntity.ok(dto);
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

    @PutMapping("/{feedbackId}")
    public ResponseEntity<FeedbackResponseDTO> updateFeedback(
            @PathVariable Long feedbackId,
            @RequestBody FeedbackRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        FeedbackResponseDTO dto = feedbackService.updateFeedback(
                feedbackId,
                userDetails.getUser().getId(),
                request
        );
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Map<String, String>> deleteFeedback(
            @PathVariable Long feedbackId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        feedbackService.deleteFeedback(feedbackId, userDetails.getUser().getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of("detail", "Feedback deleted successfully."));
    }

}
