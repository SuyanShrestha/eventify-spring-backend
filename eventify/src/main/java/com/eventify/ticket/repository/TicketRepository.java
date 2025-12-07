package com.eventify.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventify.ticket.enums.TicketStatus;
import com.eventify.ticket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long>{

    boolean existsByEventIdAndStatus(Long eventId, TicketStatus status);

    @Query("""
        SELECT t
        FROM Ticket t
        JOIN FETCH t.event e
        WHERE t.user.id = :userId
          AND t.status = 'PAID'
          AND e.approved = true
        ORDER BY t.purchaseDate DESC
    """)
    List<Ticket> findPaidTicketsForUser(@Param("userId") Long userId);

}
