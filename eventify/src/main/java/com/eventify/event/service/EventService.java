package com.eventify.event.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eventify.event.dto.EventResponseDTO;
import com.eventify.event.mapper.EventMapper;
import com.eventify.event.model.Event;
import com.eventify.event.repository.EventRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    // we can use INSTANCE directly instead of injection too for eventMapper
    private final EventMapper eventMapper;

    private final EventRepository eventRepository;


    public Event save(Event event) {
        event.setApproved(false);

        boolean isFree = event.getTicketPrice() == null || event.getTicketPrice().compareTo(BigDecimal.ZERO) <= 0;
        event.setFreeEvent(isFree);

        if (event.getBookingDeadline() == null) {
            event.setBookingDeadline(event.getEndDate());
        }

        return eventRepository.save(event);

    }

    public Event getById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }

    public void deleteById(Integer id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete. Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }

    public List<EventResponseDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        // TODO: manually fill remaining fields : attendeesCount, ticketsAvailable, isSaved
        return events.stream().map(event -> {
            EventResponseDTO dto = eventMapper.toDto(event);
            return dto;
        }).collect(Collectors.toList());
    }

}
