package com.eventify.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.eventify.event.dto.EventCategoryDTO;
import com.eventify.event.model.EventCategory;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventCategoryMapper {
    EventCategoryMapper INSTANCE = Mappers.getMapper(EventCategoryMapper.class);

    EventCategoryDTO toDto(EventCategory category);

    EventCategory toEntity(EventCategoryDTO dto);
}
