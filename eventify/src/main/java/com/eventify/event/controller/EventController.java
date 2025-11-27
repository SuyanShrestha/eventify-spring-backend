package com.eventify.event.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/events")
@Slf4j
public class EventController {

    // just to check working of auth
    @GetMapping("/dummy")
    public ResponseEntity<String> getDummyEvents() {
        log.debug("inside dummy events endpoint");
        return ResponseEntity.ok("Dummy events ");
    }

}
