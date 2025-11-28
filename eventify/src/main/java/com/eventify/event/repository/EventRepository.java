package com.eventify.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.Event;
import com.eventify.user.model.User;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    
}
