package com.eventify.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.SavedEvent;

@Repository
public interface SavedEventRepository extends JpaRepository<SavedEvent,Long> {
    
    List<SavedEvent> findByUserIdAndEventApprovedTrueOrderBySavedAtDesc(Long userId);

} 