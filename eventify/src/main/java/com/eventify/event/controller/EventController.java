package com.eventify.event.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.event.dto.EventResponseDTO;
import com.eventify.event.model.Event;
import com.eventify.event.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
@Slf4j
public class EventController {


    private final EventService eventService;
   
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents() {
        List<EventResponseDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<Event> save(@RequestBody Event event) {
        Event saved =eventService.save(event);
        return ResponseEntity.ok(saved);
        
    }


}
