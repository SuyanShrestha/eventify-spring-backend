package com.eventify.feedback.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eventify.event.model.Event;
import com.eventify.event.repository.EventRepository;
import com.eventify.feedback.dto.FeedbackResponseDTO;
import com.eventify.feedback.mapper.FeedbackMapper;
import com.eventify.feedback.model.Feedback;
import com.eventify.feedback.repository.FeedbackRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackMapper feedbackMapper;

    private final EventRepository eventRepository;
    private final FeedbackRepository feedbackRepository;
    
    public List<FeedbackResponseDTO> getFeedbacksForEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!event.isApproved()) {
            throw new IllegalArgumentException("Event not approved");
        }

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new SecurityException("You do not have permission to view feedbacks for this event");
        }

        List<Feedback> feedbacks = feedbackRepository.findByEventIdOrderByCreatedAtDesc(eventId);
        return feedbacks.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }
}
