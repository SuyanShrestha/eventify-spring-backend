package com.eventify.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.ticket.enums.TicketStatus;
import com.eventify.ticket.model.BookedTicket;

@Repository
public interface BookedTicketRepository extends JpaRepository<BookedTicket,Long>{
    
    List<BookedTicket> findByTicketUserIdAndTicketEventIdAndTicketStatusOrderByTicketPurchaseDateDesc(
        Long userId,
        Long eventId,
        TicketStatus status
    );

    Optional<BookedTicket> findByQrCodeData(String qrCodeData);

    List<BookedTicket> findByTicketEventIdAndQrCodeDataIsNotNull(Long eventId);
}
