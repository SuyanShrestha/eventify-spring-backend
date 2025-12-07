package com.eventify.event.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public EventResponseDTO getById(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
        return eventMapper.toDto(event);
    }

    public void deleteById(Integer id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete. Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
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


}
