package com.eventify.ticket.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.eventify.core.email.EmailService;
import com.eventify.core.email.EmailTemplates;
import com.eventify.core.email.dto.EmailDTO;
import com.eventify.event.model.Event;
import com.eventify.event.repository.EventRepository;
import com.eventify.ticket.dto.BookingDTO;
import com.eventify.ticket.enums.TicketStatus;
import com.eventify.ticket.mapper.BookingMapper;
import com.eventify.ticket.model.BookedTicket;
import com.eventify.ticket.model.Ticket;
import com.eventify.ticket.repository.BookedTicketRepository;
import com.eventify.ticket.repository.TicketRepository;
import com.eventify.user.model.User;
import com.eventify.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final BookedTicketRepository bookedTicketRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final QrCodeService qrCodeService;
    private final EmailService emailService;

    @Transactional
    public BookingDTO bookEvent(Long eventId, Long userId, int quantity) {

        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // PAID EVENT : check quantity availability
        Integer available = getTicketsAvailable(event);

        if (!event.isFreeEvent() && available < quantity) {
            throw new IllegalArgumentException(
                "Only " + available + " tickets available"
            );
        }


        Ticket ticket = Ticket.builder()
            .event(event)
            .user(user)
            .quantity(quantity)
            .unitPrice(event.isFreeEvent() ? BigDecimal.ZERO : event.getTicketPrice())
            .totalPrice(
                event.isFreeEvent()
                    ? BigDecimal.ZERO
                    : event.getTicketPrice().multiply(BigDecimal.valueOf(quantity))
            )
            // TODO: Just for MVP: directly PAID
            // Later: For PAID events, RESERVED until Stripe confirms, if Stripe fails CANCELLED status, if succeeds PAID status
            .status(TicketStatus.PAID)
            .purchaseDate(LocalDateTime.now())
            .build();

        ticketRepository.save(ticket);

        // Ticket handles quantity, purchaseDate, etc, but bookedTicket links that ticket with QR code.
        String qrData = qrCodeService.generateQrCodeData(ticket);
        String qrPath = qrCodeService.generateQrImage(qrData, ticket.getTicketCode());

        BookedTicket bookedTicket = BookedTicket.builder()
            .ticket(ticket)
            .qrCodeData(qrData)
            .qrCodeImage(qrPath)
            .build();

        bookedTicketRepository.save(bookedTicket);

        EmailDTO email = EmailTemplates.ticketBooked(bookedTicket);
        emailService.sendWithAttachment(email, bookedTicket.getQrCodeImage());

        return bookingMapper.toDto(bookedTicket);
    }


    private Integer getTicketsAvailable(Event event) {
        if (event.isFreeEvent()) {
            return null;
        }

        int sold = ticketRepository.sumQuantityByEventAndStatus(
            event.getId(),
            TicketStatus.PAID
        );

        return event.getTotalTickets() - sold;
    }

}
