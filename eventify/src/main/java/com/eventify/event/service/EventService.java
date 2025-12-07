package com.eventify.event.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eventify.event.dto.EventRequestDTO;
import com.eventify.event.dto.EventResponseDTO;
import com.eventify.event.mapper.EventMapper;
import com.eventify.event.model.Event;
import com.eventify.event.model.EventCategory;
import com.eventify.event.repository.EventCategoryRepository;
import com.eventify.event.repository.EventRepository;
import com.eventify.ticket.enums.TicketStatus;
import com.eventify.ticket.model.Ticket;
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
public class EventService {

    // we can use INSTANCE directly instead of injection too for eventMapper
    private final EventMapper eventMapper;

    private final EventRepository eventRepository;

    private final EventCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;


    @Transactional
    public EventResponseDTO save(EventRequestDTO dto, Long userId) {

        validateEventDates(dto);

        EventCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category"));

        Event event = eventMapper.fromRequestDto(dto);

        event.setCategory(category);
        event.setApproved(false);

        boolean isFree =
                dto.getTicketPrice() == null ||
                dto.getTicketPrice().compareTo(BigDecimal.ZERO) <= 0;

        event.setFreeEvent(isFree);
        event.setBookingDeadline(
                dto.getBookingDeadline() != null
                        ? dto.getBookingDeadline()
                        : dto.getEndDate()
        );

        User organizer = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        event.setOrganizer(organizer);

        Event saved = eventRepository.save(event);
        return eventMapper.toDto(saved);
    }

    @Transactional
    public EventResponseDTO update(Long eventId, EventRequestDTO dto, Long userId) {
        validateEventDates(dto);

        Event existing = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!existing.getOrganizer().getId().equals(userId)) {
            throw new SecurityException("Only organizer can update the event");
        }

        if (!dto.getCategoryId().equals(existing.getCategory().getId())) {
            EventCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category"));
            existing.setCategory(category);
        }

        eventMapper.updateFromDto(dto, existing);
        existing.setApproved(false);
        Event saved = eventRepository.save(existing);

        return eventMapper.toDto(saved);
    }

    @Transactional
    public void deleteEvent(Long eventId, Long userId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new SecurityException("You are not allowed to delete this event");
        }

        boolean hasPaidTickets = ticketRepository.existsByEventIdAndStatus(eventId, TicketStatus.PAID);

        if (hasPaidTickets) {
            throw new IllegalStateException(
                    "You cannot delete this event because users have already booked it."
            );
        }

        eventRepository.deleteById(eventId);
    }

    public List<EventResponseDTO> getMyBookings(Long userId) {
        List<Ticket> tickets = ticketRepository.findPaidTicketsForUser(userId);

        List<Event> events = tickets.stream()
                .map(Ticket::getEvent)
                .distinct()
                .toList();
        List<EventResponseDTO> bookings = new ArrayList<>();

        for (Event event : events) {
            EventResponseDTO dto = eventMapper.toDto(event);
            dto.setAttendeesCount(getAttendeesCount(event, userId));
            dto.setTicketsAvailable(getTicketsAvailable(event));
            bookings.add(dto);
        }

        return bookings;
    }


    public List<EventResponseDTO> getAllEvents() {
        List<Event> events = eventRepository.findByApprovedTrue();

        // TODO: manually fill remaining fields : attendeesCount, ticketsAvailable, isSaved
        return events.stream().map(event -> {
            EventResponseDTO dto = eventMapper.toDto(event);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<EventResponseDTO> getEventsByOrganizer(Long organizerId) {
        if (organizerId == null || organizerId <= 0) {
            throw new IllegalArgumentException("Invalid organizer ID: " + organizerId);
        }

        try {
            List<Event> events = eventRepository.findByOrganizerId(organizerId);

            // TODO: manually fill remaining fields : attendeesCount, ticketsAvailable, isSaved
            return events.stream().map(event -> {
                EventResponseDTO dto = eventMapper.toDto(event);
                return dto;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to fetch events for organizerId: " + organizerId, e);
        }
        
    }


    // ADMIN SIDE

    public List<EventResponseDTO> getPendingEvents() {
        List<Event> pendingEvents = eventRepository.findByApprovedFalse();

        return pendingEvents.stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventResponseDTO approveEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + eventId + " not found"));

        if (event.isApproved()) {
            throw new IllegalArgumentException("Event with ID " + eventId + " is already approved");
        }

        event.setApproved(true);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }


    // PRIVATE METHODS

    private void validateEventDates(EventRequestDTO dto) {
        if (!dto.getStartDate().isBefore(dto.getEndDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (dto.getBookingDeadline() != null &&
            !dto.getBookingDeadline().isBefore(dto.getEndDate())) {
            throw new IllegalArgumentException("Booking deadline must be before event end date");
        }
        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException("Event category is required");
        }
    }

    private int getAttendeesCount(Event event, Long userId) {
        if (event.getOrganizer().getId().equals(userId)) {
            return event.getTickets().stream()
                    .filter(t -> t.getStatus() == TicketStatus.PAID)
                    .mapToInt(Ticket::getQuantity)
                    .sum();
        }
        return 0;
    }

    private Integer getTicketsAvailable(Event event) {
        int sold = event.getTickets().stream()
                .filter(t -> t.getStatus() == TicketStatus.PAID)
                .mapToInt(Ticket::getQuantity)
                .sum();

        if (event.getTotalTickets() != null) {
            return event.getTotalTickets() - sold;
        } else if (event.isFreeEvent()) {
            return null;
        }
        return 0;
    }


}
