package com.eventify.event.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient.ResponseSpec;

import com.eventify.event.model.EventCategory;
import com.eventify.event.service.EventCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@Slf4j
public class EventCategoryController {

    private final EventCategoryService eventCategoryService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody EventCategory category)
    {
        eventCategoryService.save(category);
        return ResponseEntity.ok("Category has been created");
    }

    @GetMapping
    public ResponseEntity<List<EventCategory>> getCategories()
    {
      List<EventCategory> categories = eventCategoryService.getCategories();
      return ResponseEntity.ok(categories);
    }
    
}
