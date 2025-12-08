package com.eventify.event.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eventify.event.dto.EventCategoryRequestDTO;
import com.eventify.event.model.EventCategory;

@Component
public class EventCategoryTestHelper {
    

    public EventCategoryRequestDTO createCategoryRequestDto()
    {
        return EventCategoryRequestDTO.builder().name("AI Event").build();
    }

    public List<EventCategory> createCategoryList()
    {
       return List.of(
        EventCategory.builder().name("Conference").build(),
        EventCategory.builder().name("Workshop").build(),
        EventCategory.builder().name("Webinar").build()
    );
    }

}
