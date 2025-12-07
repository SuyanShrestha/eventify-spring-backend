package com.eventify.event.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.SavedEvent;

@Repository
public interface SavedEventRepository extends JpaRepository<SavedEvent,Long> {

    @Query("select se.event.id from SavedEvent se where se.user.id = :userId")
    Set<Long> findSavedEventIdsByUserId(@Param("userId") Long userId);


    Optional<SavedEvent> findByUserIdAndEventId(Long userId, Long eventId);

    List<SavedEvent> findByUserIdAndEventApprovedTrueOrderBySavedAtDesc(Long userId);

} 