package com.eventify.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.event.dto.EventCategoryDTO;
import com.eventify.event.dto.EventCategoryRequestDTO;
import com.eventify.event.service.EventCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminEventCategoryController {

    private final EventCategoryService eventCategoryService;
    
    @PostMapping
    public ResponseEntity<EventCategoryDTO> save(@RequestBody EventCategoryRequestDTO category)
    {
        EventCategoryDTO eventCategory = eventCategoryService.save(category);
        return ResponseEntity.ok(eventCategory);
    }
}
