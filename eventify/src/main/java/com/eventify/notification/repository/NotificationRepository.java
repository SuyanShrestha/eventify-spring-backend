package com.eventify.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.notification.model.Notification;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification,Long>{
    
}
