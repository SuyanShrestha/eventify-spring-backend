package com.eventify.notification.service;

import org.springframework.stereotype.Service;

import com.eventify.event.model.Event;
import com.eventify.notification.model.Notification;
import com.eventify.notification.repository.NotificationRepository;
import com.eventify.user.model.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepo;

    @Transactional
    public void notify(User user, Event event, String message) {
        Notification n = Notification.builder()
            .user(user)
            .event(event)
            .message(message)
            .isRead(false)
            .build();

        notificationRepo.save(n);
    }
}