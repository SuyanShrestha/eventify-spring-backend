package com.eventify.event.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventify.event.model.EventCategory;
import com.eventify.event.repository.EventCategoryRepository;
import com.eventify.event.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventCategoryService {
    

    private final EventCategoryRepository eventCategoryRepository;

    public void save(EventCategory category)
    {
        eventCategoryRepository.save(category);
    }

    public List<EventCategory> getCategories()
    {
        return eventCategoryRepository.findAll();
    }
}
