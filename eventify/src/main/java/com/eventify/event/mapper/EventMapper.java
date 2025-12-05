package com.eventify.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.eventify.event.dto.EventResponseDTO;
import com.eventify.event.model.Event;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "category.id", target = "categoryDetails.id")
    @Mapping(source = "category.name", target = "categoryDetails.name")
    @Mapping(source = "organizer.id", target = "organizer.id")
    @Mapping(source = "organizer.username", target = "organizer.username")
    @Mapping(source = "organizer.profilePicture", target = "organizer.profilePicture")
    @Mapping(target = "isUpcoming", expression = "java(event.getStartDate().isAfter(java.time.LocalDateTime.now()))")
    @Mapping(target = "isActive", expression = "java(!event.getStartDate().isAfter(java.time.LocalDateTime.now()) && !event.getEndDate().isBefore(java.time.LocalDateTime.now()))")
    @Mapping(target = "isExpired", expression = "java(event.getEndDate().isBefore(java.time.LocalDateTime.now()))")
    EventResponseDTO toDto(Event event);

    @Mapping(source = "categoryDetails.id", target = "category.id")
    @Mapping(source = "categoryDetails.name", target = "category.name")
    @Mapping(source = "organizer.id", target = "organizer.id")
    Event toEntity(EventResponseDTO dto);
}