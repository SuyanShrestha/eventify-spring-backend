package com.eventify.event.service;

import org.springframework.stereotype.Service;

import com.eventify.event.dto.EventDto;
import com.eventify.event.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    

    private final EventRepository eventRepository;
    
}
