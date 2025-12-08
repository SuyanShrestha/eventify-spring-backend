package com.eventify.event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventify.event.dto.EventCategoryDTO;
import com.eventify.event.dto.EventCategoryRequestDTO;
import com.eventify.event.helper.EventCategoryTestHelper;
import com.eventify.event.mapper.EventCategoryMapper;
import com.eventify.event.model.EventCategory;
import com.eventify.event.repository.EventCategoryRepository;
import com.eventify.event.service.EventCategoryService;

import lombok.RequiredArgsConstructor;

@ExtendWith(MockitoExtension.class)
public class EventCategoryServiceTest {
    

    @Mock
    private EventCategoryRepository eventCategoryRepository;

    @InjectMocks
    private EventCategoryService eventCategoryService;

    @InjectMocks
    private EventCategoryTestHelper testHelper;

    @Spy
    private EventCategoryMapper eventCategoryMapper = Mappers.getMapper(EventCategoryMapper.class);


    @Test
    public void save_whenValidData_shouldSaveToDatabase()
    {
        EventCategoryRequestDTO categoryDto = testHelper.createCategoryRequestDto();
        eventCategoryService.save(categoryDto);
        verify(eventCategoryRepository, times(1)).save(any(EventCategory.class));
    }

    @Test
    public void getCategories_shouldReturnAllCategories()
    {

        List<EventCategory> categories =  testHelper.createCategoryList();
        when(eventCategoryRepository.findAll()).thenReturn(categories);
        List<EventCategoryDTO> dtos = eventCategoryService.getCategories();
        assertEquals(3, dtos.size());
        assertEquals("Conference", dtos.getFirst().getName());
    }
}
