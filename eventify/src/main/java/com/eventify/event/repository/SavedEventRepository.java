package com.eventify.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.SavedEvent;

@Repository
public interface SavedEventRepository extends JpaRepository<SavedEvent,Integer> {

    
} 