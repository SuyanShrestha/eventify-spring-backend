package com.eventify.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eventify.event.dto.EventCategoryDTO;
import com.eventify.event.dto.EventCategoryRequestDTO;
import com.eventify.event.mapper.EventCategoryMapper;
import com.eventify.event.model.EventCategory;
import com.eventify.event.repository.EventCategoryRepository;
import com.eventify.event.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventCategoryService {
    
    private final EventCategoryMapper eventCategoryMapper;
    private final EventCategoryRepository eventCategoryRepository;

    public void save(EventCategoryRequestDTO categoryDto)
    {
        EventCategory category = EventCategory.builder()
                .name(categoryDto.getName())
                .build();
        eventCategoryRepository.save(category);
    }

    public List<EventCategoryDTO> getCategories()
    {
        List<EventCategory> categories =  eventCategoryRepository.findAll();
        return categories.stream()
                .map(eventCategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
