package com.eventify.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.eventify.user.dto.UserProfileResponseDTO;
import com.eventify.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "isOrganizer", expression = "java(user.isOrganizer())")
    UserProfileResponseDTO toProfileDto(User user);

}
