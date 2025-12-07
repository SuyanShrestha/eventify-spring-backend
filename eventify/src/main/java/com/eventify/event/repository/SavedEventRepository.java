package com.eventify.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.SavedEvent;

@Repository
public interface SavedEventRepository extends JpaRepository<SavedEvent,Long> {

    Optional<SavedEvent> findByUserIdAndEventId(Long userId, Long eventId);

    List<SavedEvent> findByUserIdAndEventApprovedTrueOrderBySavedAtDesc(Long userId);

} 