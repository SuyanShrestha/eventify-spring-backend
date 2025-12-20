package com.eventify.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventify.event.model.Event;
import com.eventify.notification.dto.NotificationResponseDTO;
import com.eventify.notification.mapper.NotificationMapper;
import com.eventify.notification.model.Notification;
import com.eventify.notification.repository.NotificationRepository;
import com.eventify.user.model.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepo;

    private final NotificationMapper notificationMapper;

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

    public List<NotificationResponseDTO> getUserNotifications(Long userId, Boolean isRead) {
        List<Notification> notifications;

        if (isRead == null) {
            notifications = notificationRepo.findByUserIdOrderByCreatedAtDesc(userId);
        } else {
            notifications = notificationRepo.findByUserIdAndIsReadOrderByCreatedAtDesc(userId, isRead);
        }

        return notificationMapper.toDtoList(notifications);
    }
}
