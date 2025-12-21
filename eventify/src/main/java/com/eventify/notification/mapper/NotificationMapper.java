package com.eventify.notification.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.eventify.notification.dto.NotificationResponseDTO;
import com.eventify.notification.model.Notification;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "event.banner", target = "eventDetails.banner")
    @Mapping(source = "event.title", target = "eventDetails.title")
    NotificationResponseDTO toDto(Notification notification);

    List<NotificationResponseDTO> toDtoList(List<Notification> notifications);
}
