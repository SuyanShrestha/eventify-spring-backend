package com.eventify.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String profilePicture;
    private String phoneNumber;
    private String address;

    @JsonProperty("is_organizer")
    private boolean isOrganizer;
}
