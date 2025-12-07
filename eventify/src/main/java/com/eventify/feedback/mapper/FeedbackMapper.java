package com.eventify.feedback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.eventify.feedback.dto.FeedbackResponseDTO;
import com.eventify.feedback.model.Feedback;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackMapper {
    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "user.id", target = "user.id")
    @Mapping(source = "user.username", target = "user.username")
    @Mapping(source = "user.profilePicture", target = "user.profilePicture")
    FeedbackResponseDTO toDto(Feedback feedback);
}
