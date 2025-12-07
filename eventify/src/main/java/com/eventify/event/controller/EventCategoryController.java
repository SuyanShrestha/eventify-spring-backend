package com.eventify.event.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.event.dto.EventCategoryDTO;
import com.eventify.event.service.EventCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Slf4j
public class EventCategoryController {

    private final EventCategoryService eventCategoryService;

    @GetMapping
    public ResponseEntity<List<EventCategoryDTO>> getCategories()
    {
      List<EventCategoryDTO> categories = eventCategoryService.getCategories();
      return ResponseEntity.ok(categories);
    }
    
}
