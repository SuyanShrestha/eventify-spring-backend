package com.eventify.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.ticket.enums.TicketStatus;
import com.eventify.ticket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long>{

    boolean existsByEventIdAndStatus(Long eventId, TicketStatus status);

}
