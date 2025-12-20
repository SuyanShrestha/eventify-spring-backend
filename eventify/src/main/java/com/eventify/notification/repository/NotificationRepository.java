package com.eventify.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventify.notification.model.Notification;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification,Long>{
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(
        Long userId,
        Boolean isRead
    );

    Optional<Notification> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Query("""
        update Notification n
        set n.isRead = true
        where n.user.id = :userId
            and n.isRead = false
    """)
    int markAllAsRead(Long userId);
}
