package com.eventify.rsvp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.Event;
import com.eventify.rsvp.model.RSVP;
import com.eventify.user.model.User;

@Repository
public interface RSVPRepository extends JpaRepository<RSVP,Long>{
    List<RSVP> findByUser(User user);

    List<RSVP> findByEvent(Event event);

    Optional<RSVP> findByUserAndEvent(User user, Event event);
}
