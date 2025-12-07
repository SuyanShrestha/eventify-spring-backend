package com.eventify.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.Event;
import java.util.List;
import java.util.Optional;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findById(Long id);

    List<Event> findByApprovedTrue();

    List<Event> findByApprovedFalse();

    List<Event> findByOrganizerId(Long organizerId);
}
