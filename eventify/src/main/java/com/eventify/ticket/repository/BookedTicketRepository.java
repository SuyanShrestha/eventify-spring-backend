package com.eventify.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.ticket.model.BookedTicket;

@Repository
public interface BookedTicketRepository extends JpaRepository<BookedTicket,Long>{
    
}
