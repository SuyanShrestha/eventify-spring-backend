package com.eventify.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.notification.model.Notification;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification,Long>{
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(
        Long userId,
        Boolean isRead
    );
}
