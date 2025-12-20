package com.eventify.notification.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.notification.dto.NotificationResponseDTO;
import com.eventify.notification.service.NotificationService;
import com.eventify.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getNotifications(
            @RequestParam(value = "is_read", required = false) Boolean isRead,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        return ResponseEntity.ok(notificationService.getUserNotifications(userId, isRead));
    }

    @PutMapping("/mark-as-read/{id}")
    public ResponseEntity<Map<String, String>> markAsRead(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        notificationService.markAsRead(id, userId);

        return ResponseEntity.ok(
            Map.of("detail", "Notification marked as read.")
        );
    }

    @PutMapping("/mark-all-as-read")
    public ResponseEntity<Map<String, String>> markAllAsRead(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = (userDetails != null) ? userDetails.getUserId() : null;
        notificationService.markAllAsRead(userId);

        return ResponseEntity.ok(
            Map.of("detail", "All notifications marked as read.")
        );
    }

}
