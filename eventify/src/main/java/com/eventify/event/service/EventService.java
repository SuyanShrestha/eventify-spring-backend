package com.eventify.event.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eventify.core.email.EmailService;
import com.eventify.core.email.EmailTemplates;
import com.eventify.core.email.dto.EmailDTO;
import com.eventify.event.dto.AttendeeDetailDTO;
import com.eventify.event.dto.AttendeesDTO;
import com.eventify.event.dto.EventDetailResponseDTO;
import com.eventify.event.dto.EventInvitationRequestDTO;
import com.eventify.event.dto.EventRequestDTO;
import com.eventify.event.dto.EventResponseDTO;
import com.eventify.event.dto.SavedEventResponseDTO;
import com.eventify.event.mapper.EventMapper;
import com.eventify.event.model.Event;
import com.eventify.event.model.EventCategory;
import com.eventify.event.model.SavedEvent;
import com.eventify.event.repository.EventCategoryRepository;
import com.eventify.event.repository.EventRepository;
import com.eventify.event.repository.SavedEventRepository;
import com.eventify.feedback.mapper.FeedbackMapper;
import com.eventify.feedback.repository.FeedbackRepository;
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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    // we can use INSTANCE directly instead of injection too for eventMapper
    private final EventMapper eventMapper;
    private final FeedbackMapper feedbackMapper;
    private final BookingMapper bookingMapper;

    private final EmailService emailService;

    private final EventRepository eventRepository;
    private final SavedEventRepository savedEventRepository;
    private final EventCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final FeedbackRepository feedbackRepository;
    private final BookedTicketRepository bookedTicketRepository;

    @Value("${frontend.base.url}")
    private String frontendBaseUrl;


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
    public String toggleSaveEvent(Long eventId, Long userId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if (!event.isApproved()) {
            throw new IllegalStateException("Event is not approved");
        }

        return savedEventRepository.findByUserIdAndEventId(userId, eventId)
                .map(saved -> {
                    savedEventRepository.delete(saved);
                    return "Event unsaved successfully.";
                })
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new EntityNotFoundException("User not found"));

                    SavedEvent se = SavedEvent.builder()
                            .user(user)
                            .event(event)
                            .build();
                    savedEventRepository.save(se);

                    return "Event saved successfully.";
                });
    }

    @Transactional
    public void sendInvitations(EventInvitationRequestDTO request) {

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!event.isApproved()) {
            throw new IllegalStateException(
                    "Invitations cannot be sent because the event is not approved."
            );
        }
        
        for (String recipient : request.getEmail()) {
            EmailDTO emailDTO = EmailTemplates.eventInvitation(
                    recipient,
                    event,
                    frontendBaseUrl
            );
            emailService.send(emailDTO);
        }
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


    public List<EventResponseDTO> getAllEvents(Long userId) {
        List<Event> events = eventRepository.findByApprovedTrue();
        
        Set<Long> savedEventIds = (userId != null)
            ? savedEventRepository.findSavedEventIdsByUserId(userId)
            : Collections.emptySet();

        return events.stream().map(event -> {
            EventResponseDTO dto = eventMapper.toDto(event);
            dto.setAttendeesCount(getAttendeesCount(event, userId));
            dto.setTicketsAvailable(getTicketsAvailable(event));
            dto.setSaved(savedEventIds.contains(event.getId()));
            return dto;
        }).collect(Collectors.toList());
    }

    public List<EventResponseDTO> getEventsByOrganizer(Long organizerId) {
        if (organizerId == null || organizerId <= 0) {
            throw new IllegalArgumentException("Invalid organizer ID: " + organizerId);
        }

        try {
            List<Event> events = eventRepository.findByOrganizerId(organizerId);
            Set<Long> savedEventIds = savedEventRepository.findSavedEventIdsByUserId(organizerId);

            return events.stream().map(event -> {
                EventResponseDTO dto = eventMapper.toDto(event);
                dto.setAttendeesCount(getAttendeesCount(event, organizerId));
                dto.setTicketsAvailable(getTicketsAvailable(event));
                dto.setSaved(savedEventIds.contains(event.getId()));
                return dto;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to fetch events for organizerId: " + organizerId, e);
        }
        
    }

    public List<SavedEventResponseDTO> getSavedEventsForUser(Long userId) {
        List<SavedEvent> savedEvents = savedEventRepository
                .findByUserIdAndEventApprovedTrueOrderBySavedAtDesc(userId);

        return savedEvents.stream().map(se -> {
            EventResponseDTO eventDto = eventMapper.toDto(se.getEvent());

            eventDto.setTicketsAvailable(getTicketsAvailable(se.getEvent()));
            eventDto.setAttendeesCount(getAttendeesCount(se.getEvent(), userId));
            eventDto.setSaved(true);

            return new SavedEventResponseDTO(se.getId(), se.getSavedAt(), eventDto);
        }).toList();
    }

    public EventDetailResponseDTO getEventDetails(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        
        EventDetailResponseDTO dto = eventMapper.toDetailDto(event);
        
        boolean isSaved = (userId != null) && savedEventRepository.existsByUserIdAndEventId(userId, eventId);
        dto.setSaved(isSaved);
        dto.setTicketsAvailable(getTicketsAvailable(event));
        dto.setAttendees(getAttendees(event, userId));

        if (userId != null && event.getOrganizer().getId().equals(userId)) {
            dto.setFeedbacks(
                feedbackRepository.findByEventIdOrderByCreatedAtDesc(eventId)
                    .stream()
                    .map(feedbackMapper::toDto)
                    .toList()
            );
        } else {
            dto.setFeedbacks(List.of());
        }

        // only show bookings for logged in user, and bookings related to that user
        if (userId != null) {
            List<BookedTicket> bookedTickets = bookedTicketRepository
                .findByTicketUserIdAndTicketEventIdAndTicketStatusOrderByTicketPurchaseDateDesc(
                    userId, eventId, TicketStatus.PAID
                );

            List<BookingDTO> bookings = bookedTickets.stream()
                .map(bookingMapper::toDto)
                .toList();

            dto.setBookings(bookings);
        } else {
            dto.setBookings(List.of());
        }

        return dto;
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

        EmailDTO email = EmailTemplates.eventApproval(
                event.getOrganizer().getEmail(),
                event.getOrganizer().getUsername(),
                event.getTitle(),
                frontendBaseUrl + "/dashboard"
        );

        emailService.send(email);
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
        if (userId == null || event.getOrganizer() == null || event.getOrganizer().getId() == null) {
            return 0;
        }

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

    public AttendeesDTO getAttendees(Event event, Long userId) {
        // only organizer can see attendees
        if (userId == null || event.getOrganizer() == null || !event.getOrganizer().getId().equals(userId)) {
            return AttendeesDTO.builder()
                    .attendeesCount(0)
                    .attendeesDetail(List.of())
                    .build();
        }

        // only paid tickets to be counted for attendees
        List<Ticket> paidTickets = event.getTickets().stream()
                .filter(t -> t.getStatus() == TicketStatus.PAID)
                .toList();

        int attendeesCount = paidTickets.stream()
                .mapToInt(Ticket::getQuantity)
                .sum();

        List<AttendeeDetailDTO> attendeesDetail = paidTickets.stream()
                .map(t -> AttendeeDetailDTO.builder()
                        .userId(t.getUser().getId())
                        .username(t.getUser().getUsername())
                        .ticketCount(t.getQuantity())
                        .isCheckedIn(t.getBookedTicket() != null && Boolean.TRUE.equals(t.getBookedTicket().getIsCheckedIn()))
                        .build())
                .toList();

        return AttendeesDTO.builder()
                .attendeesCount(attendeesCount)
                .attendeesDetail(attendeesDetail)
                .build();
    }


}
