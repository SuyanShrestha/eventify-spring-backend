package com.eventify.ticket.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.eventify.core.email.EmailService;
import com.eventify.core.email.EmailTemplates;
import com.eventify.core.email.dto.EmailDTO;
import com.eventify.event.model.Event;
import com.eventify.event.repository.EventRepository;
import com.eventify.notification.service.NotificationService;
import com.eventify.ticket.dto.BookingDTO;
import com.eventify.ticket.dto.CheckinRequestDTO;
import com.eventify.ticket.dto.CheckinResponseDTO;
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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final BookedTicketRepository bookedTicketRepository;
    private final UserRepository userRepository;

    private final BookingMapper bookingMapper;
    private final QrCodeService qrCodeService;
    private final EmailService emailService;
    private final NotificationService notificationService;

    @Value("${frontend.base.url}")
    private String frontendBaseUrl;

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

    @Transactional
    public CheckinResponseDTO checkIn(
            CheckinRequestDTO request,
            Long userId
    ) {

        User currentUser = userRepository.findById(userId)
            .orElseThrow(() ->
                    new EntityNotFoundException("User not found")
            );
        
        if (!currentUser.isOrganizer()) {
            throw new AccessDeniedException(
                "You do not have permission for ticket verification"
            );
        }

        String qrCodeData = request.getQrCodeData();
        if (qrCodeData == null || qrCodeData.isBlank()) {
            throw new IllegalArgumentException("QR code data is required");
        }

        BookedTicket bookedTicket = bookedTicketRepository
                .findByQrCodeData(qrCodeData)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid QR code")
                );

        Event event = bookedTicket.getTicket().getEvent();

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new AccessDeniedException(
                "You do not have permission to verify this ticket."
            );
        }

        if (Boolean.TRUE.equals(bookedTicket.getIsCheckedIn())) {
            throw new IllegalStateException("Ticket already checked in");
        }

        if (event.getEndDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("This event has already passed");
        }

        if (bookedTicket.getTicket().getStatus() != TicketStatus.PAID) {
            throw new IllegalArgumentException(
                "Ticket status is "
                + bookedTicket.getTicket().getStatus().name().toLowerCase()
                + ", which is not valid for check-in"
            );
        }

        bookedTicket.setIsCheckedIn(true);
        bookedTicket.setCheckedInTime(LocalDateTime.now());
        bookedTicketRepository.save(bookedTicket);

        // notify booked user about check-in
        notificationService.notify(
            bookedTicket.getTicket().getUser(),
            event,
            "Your ticket has been successfully checked in for '" + event.getTitle() + "'."
        );
        
        EmailDTO email = EmailTemplates.checkInConfirmation(
            bookedTicket.getTicket().getUser().getEmail(),
            bookedTicket.getTicket().getUser().getUsername(),
            event.getTitle(),
            event.getVenue(),
            bookedTicket.getCheckedInTime()
                .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a")),
            frontendBaseUrl + "/events/" + event.getId(),
            event.getOrganizer().getUsername()
        );
        emailService.send(email);

        return CheckinResponseDTO.builder()
                .detail("Ticket successfully checked in")
                .ticketInfo(bookingMapper.toTicketInfo(bookedTicket))
                .build();
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
